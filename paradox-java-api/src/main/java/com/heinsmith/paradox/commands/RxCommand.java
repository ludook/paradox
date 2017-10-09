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

package com.heinsmith.paradox.commands;

import com.heinsmith.paradox.ProtocolConstants;

import java.nio.charset.StandardCharsets;

/**
 * Created by Hein Smith on 2017/03/18.
 */
public class RxCommand {

    private final String rawCommand;
    private byte[] bytes;

    public RxCommand(String rawCommand) {

        this.rawCommand = rawCommand;

        if(rawCommand == null || rawCommand.trim().isEmpty()) {
            throw new InvalidCommandException("Empty or Null command provided.");
        }
        bytes = rawCommand.getBytes(StandardCharsets.US_ASCII);
    }

    public boolean isSuccess() {
        return rawCommand.endsWith(ProtocolConstants.COMMAND_OK);
    }

    public boolean isFailed() {
        return rawCommand.endsWith(ProtocolConstants.COMMAND_FAIL);
    }


    public char get(int index) {
        if(index >= bytes.length || index < 0) {
            throw new InvalidCommandIndexException();
        }
        return (char) (bytes[index] & 0xFF);
    }

    public String get(int beginIndex, int endIndex) {


        if(beginIndex < 0 || endIndex >= rawCommand.length()) {
            throw new InvalidCommandIndexException();
        }
        if(beginIndex > endIndex) {
            throw new InvalidCommandIndexException();
        }
        return rawCommand.substring(beginIndex, endIndex+1);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getRawCommand() {
        return rawCommand;
    }

}
