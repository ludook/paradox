package com.heinsmith.paradox;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParadoxCommandReceiver implements SerialPortDataListener {

    private static final Logger log = LogManager.getLogger(ParadoxAlarm.class.getName());

    private StringBuilder commandBuffer = new StringBuilder();
    private CommandListener listener;

    public ParadoxCommandReceiver(CommandListener listener) {
        this.listener = listener;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if ((serialPortEvent == null) || (serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)) {
            return;
        }
        SerialPort serialPort = serialPortEvent.getSerialPort();
        byte[] newData = new byte[serialPort.bytesAvailable()];
        serialPort.readBytes(newData, newData.length);
        receive(newData);
    }

    public synchronized void receive(byte[] dataInput) {
        for (byte newAsciiData : dataInput) {
            log.debug("some data received on serial port: " + newAsciiData);
            char asciiInput = (char) (newAsciiData & 0xFF);
            if (asciiInput == ProtocolConstants.COMMAND_END) {
                commandBuffer.append(asciiInput);
                try {
                    String received = commandBuffer.toString().trim();
                    log.debug("cmd on serial port: " + received);
                    listener.handleResponse(received);
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
                commandBuffer.delete(0, commandBuffer.length());
            } else {
                commandBuffer.append(asciiInput);
            }
        }
    }
}