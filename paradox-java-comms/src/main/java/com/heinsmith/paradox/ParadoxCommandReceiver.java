package com.heinsmith.paradox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by somejavadev on 2017/03/30.
 */
public class ParadoxCommandReceiver {

    private static final List<CommandListener> COMMAND_LISTENERS = new ArrayList<>();
    private static final StringBuilder COMMAND_CONTAINER = new StringBuilder();
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    public static void receive(byte[] dataInput) {
        try {
            LOCK.writeLock().lock();
            for (int i = 0; i < dataInput.length; i++) {
                byte newAsciiData = dataInput[i];
                char asciiInput = (char) (newAsciiData & 0xFF);
                if(asciiInput == ProtocolConstants.COMMAND_END) {
                    COMMAND_CONTAINER.append(asciiInput);
                    String command = COMMAND_CONTAINER.toString() ;
                    for (CommandListener commandListener: COMMAND_LISTENERS) {
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