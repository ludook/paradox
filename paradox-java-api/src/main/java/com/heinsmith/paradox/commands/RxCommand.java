package com.heinsmith.paradox.commands;

import com.heinsmith.paradox.ProtocolConstants;

import java.nio.charset.StandardCharsets;

/**
 * Created by somejavadev on 2017/03/18.
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
