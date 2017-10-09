package com.heinsmith.paradox.commands;

import com.heinsmith.paradox.ProtocolConstants;

/**
 * Created by Hein Smith on 2017/05/31.
 */
public abstract class TxCommand {

    private CommandId key;
    private String command;

    public TxCommand(CommandId key) {
        this.key = key;
    }

    protected abstract String buildCommand() throws CommandValidationException;

    public String getCommand() {

        if(command == null) {
            try {
                String childCommand = buildCommand();
                StringBuilder builder = new StringBuilder();
                builder.append(key);
                builder.append(childCommand);
                builder.append(ProtocolConstants.COMMAND_END);
                command = builder.toString();
            } catch (CommandValidationException e) {
                e.printStackTrace();
                command = null;
            }
        }
        return command;
    }
}
