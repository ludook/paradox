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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Hein Smith on 2017/03/30.
 */
public class ParadoxCommandReceiver {

    private static final List<CommandListener> COMMAND_LISTENERS = new ArrayList<>();
    private static final StringBuilder COMMAND_CONTAINER = new StringBuilder();
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    public static void receive(byte[] dataInput) {
        try {
            LOCK.writeLock().lock();
            for (byte newAsciiData : dataInput) {
                char asciiInput = (char) (newAsciiData & 0xFF);
                if (asciiInput == ProtocolConstants.COMMAND_END) {
                    COMMAND_CONTAINER.append(asciiInput);
                    String command = COMMAND_CONTAINER.toString();
                    for (CommandListener commandListener : COMMAND_LISTENERS) {
                        commandListener.receiveCommand(command);
                    }
                    COMMAND_CONTAINER.delete(0, COMMAND_CONTAINER.length());
                } else {
                    COMMAND_CONTAINER.append(asciiInput);
                }
            }
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    public static void addCommandListener(CommandListener commandListener) {
        if(commandListener == null) {
            return;
        }
        COMMAND_LISTENERS.add(commandListener);
    }
}