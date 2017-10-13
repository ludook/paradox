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
import com.heinsmith.paradox.commands.CommandValidationException;
import com.heinsmith.paradox.commands.ResponseHandler;
import com.heinsmith.paradox.commands.TxCommand;
import com.heinsmith.paradox.commands.area.arm.AreaArm;
import com.heinsmith.paradox.commands.area.arm.ArmType;
import com.heinsmith.paradox.serial.ParadoxSerialPortDataListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;


public class TwoWaySerialComm {

    private static final Logger logger = LogManager.getLogger(TwoWaySerialComm.class.getName());

    public static void main(String[] args) {

        HashMap<String, ArrayDeque<TxCommand>> txRxQueue = new HashMap<>();
        SerialPort comPort = SerialPort.getCommPort("/tmp/virtualcom0");
        ParadoxCommandReceiver.addCommandListener(response -> {
            logger.info(response);
            int indexOf = response.indexOf("&");
            if (indexOf > 0) {
                String responseCode = response.substring(0, indexOf);
                txRxQueue.computeIfPresent(responseCode, (responseKey, responseEventHandler) -> {
                    TxCommand txCommand = responseEventHandler.pop();
                    boolean success = response.endsWith(ProtocolConstants.COMMAND_OK);
                    List<ResponseHandler> responseHandlers = txCommand.getResponseHandlers();
                    responseHandlers.forEach(responseHandler -> responseHandler.fireResponse(txCommand, success));
                    return responseEventHandler;
                });
            }
        });
        comPort.openPort();
        comPort.addDataListener(new ParadoxSerialPortDataListener());

        try {
            AreaArm areaArm = new AreaArm(1, ArmType.REGULAR_ARM, "1234".toCharArray());
            areaArm.addResponseHandler((txCommand, success) -> logger.debug("success=[" + success + "], command=[" + txCommand.getAscii().replace("\r", "") + "]"));
            runCommand(txRxQueue, comPort, areaArm);
        } catch (CommandValidationException e) {
            logger.error(e);
        }
    }

    private static void runCommand(HashMap<String, ArrayDeque<TxCommand>> txRxQueue, SerialPort comPort, final TxCommand txCommand) {
        OutputStream outputStream = comPort.getOutputStream();
        try {
            byte[] panelBytes = txCommand.getAscii().getBytes();
            String responseCode = txCommand.getResponseCode();

            txRxQueue.computeIfPresent(responseCode, (key, responseEventHandlers) -> {
                responseEventHandlers.addLast(txCommand);
                return responseEventHandlers;
            });

            txRxQueue.computeIfAbsent(responseCode, (key) -> {
                ArrayDeque<TxCommand> arrayDeque = new ArrayDeque<>();
                arrayDeque.addLast(txCommand);
                return arrayDeque;
            });
            outputStream.write(panelBytes);

        } catch (IOException e) {
            logger.error(e);
        }
    }
}