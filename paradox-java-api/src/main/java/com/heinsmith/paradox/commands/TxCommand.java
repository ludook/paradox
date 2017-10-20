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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hein Smith on 2017/05/31.
 */
public abstract class TxCommand {

    protected CommandId commandId;
    private String command;
    private final List<ResponseHandler> responseHandlers = new ArrayList<>();

    public TxCommand(CommandId commandId) throws CommandValidationException {
        if(commandId == null) {
            throw new CommandValidationException();
        }
        this.commandId = commandId;
    }

    protected abstract String buildCommand();

    public String getAscii() {

        if (command == null) {
            String childCommand = buildCommand();
            StringBuilder builder = new StringBuilder();
            builder.append(commandId.getKey());
            builder.append(childCommand);
            builder.append(ProtocolConstants.COMMAND_END);
            command = builder.toString();
        }
        return command;
    }

    public String getResponseCode() {
        String ascii = getAscii();
        String result = null;
        if(ascii != null && ascii.length() >= 5) {
            result = ascii.substring(0, 5);
        }
        return result;
    }

    public void addResponseHandler(final ResponseHandler responseHandler) {
        responseHandlers.add(responseHandler);
    }

    public List<ResponseHandler> getResponseHandlers() {
        return responseHandlers;
    }
}
