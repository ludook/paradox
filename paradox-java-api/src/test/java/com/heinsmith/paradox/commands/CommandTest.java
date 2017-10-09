package com.heinsmith.paradox.commands;

import com.heinsmith.paradox.ProtocolConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Hein Smith on 2017/03/18.
 */
class CommandTest {

    public static final String FAILED = "COMM&fail\r";
    public static final String OK = "COMM&ok\r";

    @org.junit.jupiter.api.Test
    void isSuccess() {
        int length = OK.getBytes().length;
        RxCommand rxCommand = new RxCommand(OK);

        assertEquals(8, length, "RxCommand length is invalid.");
        assertEquals(true, rxCommand.isSuccess(), "RxCommand should be ok");
        assertEquals(8, rxCommand.getBytes().length);

    }

    @org.junit.jupiter.api.Test
    void isFailed() {

        int length = FAILED.getBytes().length;
        RxCommand rxCommand = new RxCommand(FAILED);

        assertEquals(10, length, "RxCommand length is invalid.");
        assertEquals(true, rxCommand.isFailed(), "RxCommand should be a failed rxCommand");
        assertEquals(10, rxCommand.getBytes().length);
    }

    @org.junit.jupiter.api.Test
    void getBytes() {
        byte[] okBytes = OK.getBytes();
        RxCommand rxCommand = new RxCommand(OK);
        assertArrayEquals(okBytes, rxCommand.getBytes());
    }

    @org.junit.jupiter.api.Test
    void getRawCommand() {
        RxCommand rxCommand = new RxCommand(OK);
        assertEquals(OK, rxCommand.getRawCommand());
    }

    @Test
    void get() {
        String rawCommand;
        RxCommand rxCommand;
        // Loop through ASCII charset and ensure we receive the same character back as passed in the original rxCommand.
        for (int c=32; c<128; c++) {
            char asciiCharacter = (char)c;
            rawCommand = "COMM" + asciiCharacter + "&ok\r";
            rxCommand = new RxCommand(rawCommand);
            assertEquals(9, rxCommand.getBytes().length);
            assertEquals(asciiCharacter, rxCommand.get(ProtocolConstants.BYTE_05),"Invalid character return for: " + asciiCharacter);
        }
    }

    @Test
    void getIndexFromTo() {
        String rawCommand;
        RxCommand rxCommand;
        // Loop through ASCII charset and ensure we receive the same character back as passed in the original rxCommand.
        for (int c=32; c<128; c++) {
            char asciiCharacter = (char)c;
            String chars = asciiCharacter + "" + asciiCharacter + "";
            rawCommand = "A" + chars + "&ok\r";
            rxCommand = new RxCommand(rawCommand);
            assertEquals(7, rxCommand.getBytes().length);
            assertEquals(chars, rxCommand.get(ProtocolConstants.BYTE_02, ProtocolConstants.BYTE_03),"Invalid character return for: " + asciiCharacter);
        }

        rxCommand = new RxCommand("A");
        assertEquals("A", rxCommand.get(ProtocolConstants.BYTE_01, ProtocolConstants.BYTE_01));
    }

    @Test
    void invalidRawCommands() {
        Throwable nullCommand = assertThrows(InvalidCommandException.class, () -> {
            RxCommand rxCommand = new RxCommand(null);

        });
        Throwable emptyCommand = assertThrows(InvalidCommandException.class, () -> {
            RxCommand rxCommand = new RxCommand("\t\n\r  ");
        });
        assertEquals("Empty or Null command provided.", nullCommand.getMessage());
        assertEquals("Empty or Null command provided.", emptyCommand.getMessage());
    }
}