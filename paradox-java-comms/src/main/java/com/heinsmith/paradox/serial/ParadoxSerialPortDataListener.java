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

package com.heinsmith.paradox.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.heinsmith.paradox.ParadoxCommandReceiver;

/**
 * Created by Hein Smith on 2017/03/30.
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
        serialPort.readBytes(newData, newData.length);
        ParadoxCommandReceiver.receive(newData);
    }
}
