/*
 *     Copyright (C) 2017 Hein Smith
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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