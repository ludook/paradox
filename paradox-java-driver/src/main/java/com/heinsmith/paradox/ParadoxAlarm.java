package com.heinsmith.paradox;

import com.fazecast.jSerialComm.SerialPort;
import com.heinsmith.paradox.commands.CommandValidationException;
import com.heinsmith.paradox.commands.EventFactory;
import com.heinsmith.paradox.commands.TxCommand;
import com.heinsmith.paradox.commands.area.arm.AreaQuickArm;
import com.heinsmith.paradox.commands.area.arm.ArmType;
import com.heinsmith.paradox.commands.area.disarm.AreaDisarm;
import com.heinsmith.paradox.commands.area.status.AreaStatus;
import com.heinsmith.paradox.commands.system.SystemEvent;
import com.heinsmith.paradox.commands.zone.status.ZoneStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ParadoxAlarm implements CommandListener {

    protected static final Logger log = LogManager.getLogger(ParadoxAlarm.class.getName());
    final SerialPort comPort;
    SuccessCommListener successCommListener;
    ErrorCommListener errorCommListener;

    Map<String, ArrayDeque<TxCommand<?>>> txQueue = new ConcurrentHashMap<>();

    Map<Class<? extends SystemEvent>, Set<EventListener>> eventListeners = new ConcurrentHashMap<>();

    public ParadoxAlarm(String commPort, int baudRate, SuccessCommListener successCommListener, ErrorCommListener errorCommListener) {
        EventFactory.init();
        this.successCommListener = successCommListener;
        this.errorCommListener = errorCommListener;
        comPort = SerialPort.getCommPort(commPort);
        comPort.setBaudRate(baudRate);
        comPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        if (comPort.openPort()) {
            comPort.addDataListener(new ParadoxCommandReceiver(this));
        } else {
            throw new IllegalStateException("port com can not be open");
        }
    }

    public void armArea(int area) throws CommandValidationException, IOException {
        AreaQuickArm areaQuickArm = new AreaQuickArm(area, ArmType.REGULAR_ARM);
        areaQuickArm.addResponseHandler((txCommand, success) -> log.debug("success=[" + success + "], command=[" + txCommand.getAscii().replace("\r", "") + "]"));
        runCommand(areaQuickArm);
    }

    public void disarmArea(int area, String password) throws CommandValidationException, IOException {
        AreaDisarm areaDisarm = new AreaDisarm(area, password.toCharArray());
        areaDisarm.addResponseHandler((txCommand, success) -> log.debug("success=[" + success + "], command=[" + txCommand.getAscii().replace("\r", "") + "]"));
        runCommand(areaDisarm);
    }

    public void areaStatus(int area, Consumer<AreaStatus.AreaStatusResponse> consumer) throws CommandValidationException, IOException {
        AreaStatus status = new AreaStatus(area);
        status.addResponseHandler((txCommand, success) -> {
            log.debug("success=[" + success + "], command=[" + txCommand.getAscii().replace("\r", "") + "]");
            consumer.accept((AreaStatus.AreaStatusResponse) txCommand.getResponse());
        });
        runCommand(status);
    }

    public void zoneStatus(int zone, Consumer<ZoneStatus.ZoneStatusResponse> consumer) throws CommandValidationException, IOException {
        ZoneStatus status = new ZoneStatus(zone);
        status.addResponseHandler((txCommand, success) -> {
            log.debug("success=[" + success + "], command=[" + txCommand.getAscii().replace("\r", "") + "]");
            consumer.accept((ZoneStatus.ZoneStatusResponse) txCommand.getResponse());
        });
        runCommand(status);
    }

    public void registerEventListener(Class<? extends SystemEvent> event, EventListener listener) {
        eventListeners.computeIfPresent(event, (key, listeners) -> {
            listeners.add(listener);
            return listeners;
        });
        eventListeners.computeIfAbsent(event, key -> {
            Set<EventListener> listeners = new HashSet<>();
            listeners.add(listener);
            return listeners;
        });
    }

    public synchronized void runCommand(final TxCommand<?> txCommand) throws IOException {
        OutputStream outputStream = comPort.getOutputStream();
        String tx = txCommand.getAscii();
        byte[] panelBytes = tx.getBytes();
        String responseCode = txCommand.getResponseCode();

        txQueue.computeIfAbsent(responseCode, key -> new ArrayDeque<>());
        txQueue.get(responseCode).addLast(txCommand);

        txCommand.addResponseHandler((txCommand12, success) -> {
            log.debug(txCommand12.getResponseCode() + ": one serial comm ok");
            successCommListener.onSuccess(txCommand12);
        });

        txCommand.setTimeoutHandler(txCommand1 -> {
            log.warn("timeout on cmd: " + txCommand1);
            clear(txCommand1);
            errorCommListener.onTimeout(txCommand1);
        });
        log.info("send: " + txCommand);
        outputStream.write(panelBytes);
    }

    public void handleResponse(String rxCommand) {
        log.debug(rxCommand);
        int indexOf = rxCommand.indexOf('&');
        // response without content
        if (indexOf > 0) {
            log.info("rx without content: " + rxCommand);
            String responseCode = rxCommand.substring(0, indexOf);
            txQueue.computeIfPresent(responseCode, (responseKey, txCommands) -> {
                TxCommand<Void> txCommand = (TxCommand<Void>) txCommands.pop();
                txCommand.ended();
                txCommand.getResponseHandlers().forEach(responseHandler -> responseHandler.fireResponse(txCommand, rxCommand.endsWith(ProtocolConstants.COMMAND_OK)));
                return txCommands;
            });
        }
        // response with content
        else if (rxCommand.length() > 5 && txQueue.containsKey(rxCommand.substring(0, 5))) {
            log.info("rx with content: " + rxCommand);
            if (!txQueue.containsKey(rxCommand.substring(0, 5))) {
                log.warn("key not found");
            }
            txQueue.computeIfPresent(rxCommand.substring(0, 5), (responseKey, txCommands) -> {
                TxCommand<?> txCommand = txCommands.pop();
                try {
                    txCommand.parseResponse(rxCommand.substring(5));
                    txCommand.ended();
                    txCommand.getResponseHandlers().forEach(responseHandler -> responseHandler.fireResponse(txCommand, true));
                } catch (IllegalArgumentException ex) {
                    log.warn("can't understand response: " + rxCommand);
                }
                return txCommands;
            });
        }
        // event
        else if (EventFactory.isWellFormed(rxCommand)) {
            log.info("rx with event: " + rxCommand);
            SystemEvent event = EventFactory.build(rxCommand);
            if (eventListeners.containsKey(event.getClass())) {
                eventListeners.get(event.getClass()).forEach(l -> l.fireResponse(event));
            }
        } else {
            log.warn("no matching response handler: " + rxCommand);
        }
    }

    public void clear(TxCommand<?> txCommand) {
        log.info("clear waiting for tx: " + txCommand);
        txQueue.computeIfPresent(txCommand.getResponseCode(), (s, txCommands) -> {
            txCommands.forEach(TxCommand::ended);
            txCommands.clear();
            return txCommands;
        });
    }

    public void close() {
        log.warn("clear Tx Queues...");
        txQueue.keySet().forEach(k -> txQueue.get(k).forEach(TxCommand::ended));
        txQueue.clear();
        log.warn("close com port...");
        comPort.closePort();
    }

    private void waitFor(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

}