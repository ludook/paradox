package com.heinsmith.paradox;

import com.fazecast.jSerialComm.SerialPort;
import com.heinsmith.paradox.serial.ParadoxSerialPortDataListener;

/**
 * This version of the TwoWaySerialComm example makes use of the
 * SerialPortEventListener to avoid polling.
 *
 */
public class TwoWaySerialComm {
    public static void main(String[] args) {
        SerialPort comPort = SerialPort.getCommPort("/tmp/virtualcom0");
        ParadoxCommandReceiver.addCommandListener(new CommandListener() {
            @Override
            public void receiveCommand(String command) {
                System.out.println(command);
            }
        });
        comPort.openPort();
        comPort.addDataListener(new ParadoxSerialPortDataListener());
    }
}