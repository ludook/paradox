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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hein Smith on 2017/05/31.
 */
public abstract class TxCommand<T> {

    private final List<ResponseHandler<T>> responseHandlers = new ArrayList<>();
    protected CommandId commandId;
    protected T response;
    protected Timer timer;
    private String command;
    private String string;

    private TimeoutHandler<T> timeoutHandler;

    public TxCommand(CommandId commandId) throws CommandValidationException {
        if (commandId == null) {
            throw new CommandValidationException();
        }
        this.commandId = commandId;
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (timeoutHandler != null) {
                            timeoutHandler.timeout(TxCommand.this);
                        }
                    }
                },
                3000
        );
    }

    protected abstract String buildCommand(boolean obfuscate);

    public abstract void parseResponse(String repsonse);

    public T getResponse() {
        return response;
    }

    public String getAscii() {
        if (command == null) {
            command = commandId.getKey() + buildCommand(false) + ProtocolConstants.COMMAND_END;
        }
        return command;
    }

    public String toString() {
        if (string == null) {
            string = commandId.getKey() + buildCommand(true) + ProtocolConstants.COMMAND_END;
        }
        return string;
    }

    public String getResponseCode() {
        String ascii = getAscii();
        String result = null;
        if (ascii != null && ascii.length() >= 5) {
            result = ascii.substring(0, 5);
        }
        return result;
    }

    public void addResponseHandler(final ResponseHandler<T> responseHandler) {
        responseHandlers.add(responseHandler);
    }

    public void setTimeoutHandler(final TimeoutHandler<T> timeoutHandler) {
        this.timeoutHandler = timeoutHandler;
    }

    public List<ResponseHandler<T>> getResponseHandlers() {
        return responseHandlers;
    }

    public void ended() {
        timer.cancel();
        timer.purge();
    }
}
