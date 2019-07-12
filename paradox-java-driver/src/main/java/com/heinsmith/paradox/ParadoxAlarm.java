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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ParadoxAlarm implements CommandListener {

    public final static int NB_FAIL_FOR_COM_RESTART = 10;
    protected static final Logger log = LogManager.getLogger(ParadoxAlarm.class.getName());
    SerialPort comPort;
    int nbCmdTimeout;

    HashMap<String, ArrayDeque<TxCommand>> txQueue = new HashMap<>();

    HashMap<Class<? extends SystemEvent>, Set<EventListener>> eventListeners = new HashMap<>();

    public ParadoxAlarm(String commPort, int baudRate) {
        EventFactory.init();
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

    private void timeout(TxCommand<?> txCommand) {
        synchronized (comPort) {
            try {
                if (nbCmdTimeout < NB_FAIL_FOR_COM_RESTART) {
                    log.warn("timeout for tx: " + txCommand);
                    log.info("clear waiting for tx: " + txCommand);
                    txQueue.computeIfPresent(txCommand.getResponseCode(), (s, txCommands) -> {
                        txCommands.forEach(txCommand1 -> txCommand1.ended());
                        txCommands.clear();
                        return txCommands;
                    });
                } else {
                    nbCmdTimeout = 0;

                    log.error("paradox alarm not responding");
                    log.warn("clear Tx Queues...");
                    txQueue.keySet().forEach(k -> txQueue.get(k).forEach(tx -> tx.ended()));
                    txQueue.clear();
                    log.warn("close com port...");
                    comPort.closePort();
                    waitFor(1000);

                    log.warn("open com port...");
                    comPort.openPort();
                    waitFor(1000);
                }
                nbCmdTimeout++;
                log.warn("nb reinit: " + nbCmdTimeout);
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    public synchronized void runCommand(final TxCommand<?> txCommand) throws IOException {
        OutputStream outputStream = comPort.getOutputStream();
        String tx = txCommand.getAscii();
        byte[] panelBytes = tx.getBytes();
        String responseCode = txCommand.getResponseCode();

        txQueue.computeIfAbsent(responseCode, key -> new ArrayDeque<>());
        txQueue.get(responseCode).addLast(txCommand);

        txCommand.addResponseHandler((txCommand12, success) -> {
            log.debug(txCommand.getResponseCode() + ": one serial comm ok --> reinit set to 0");
            nbCmdTimeout = 0;
        });

        txCommand.setTimeoutHandler(txCommand1 -> {
            log.warn("timeout on cmd: " + txCommand1);
            timeout(txCommand1);
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
                TxCommand<Void> txCommand = txCommands.pop();
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

    private void waitFor(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

}