package com.heinsmith.paradox.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.heinsmith.paradox.ParadoxCommandReceiver;

/**
 * Created by somejavadev on 2017/03/30.
 */
public class ParadoxSerialPortDataListener implements SerialPortDataListener {

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if ((serialPortEvent == null) || (serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)){
            return;
        }
        SerialPort serialPort = serialPortEvent.getSerialPort();
        byte[] newData = new byte[serialPort.bytesAvailable()];
        int numRead = serialPort.readBytes(newData, newData.length);
        ParadoxCommandReceiver.receive(newData);
    }
}
