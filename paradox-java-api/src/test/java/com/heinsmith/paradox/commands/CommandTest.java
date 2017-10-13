package com.heinsmith.paradox.commands;

import com.heinsmith.paradox.ProtocolConstants;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


/**
 * Created by Hein Smith on 2017/03/18.
 */
public class CommandTest {

    public static final String FAILED = "COMM&fail\r";
    public static final String OK = "COMM&ok\r";

    @Test
    public void isSuccess() {
        int length = OK.getBytes().length;
        RxCommand rxCommand = new RxCommand(OK);
        assertEquals(8, length);
        assertEquals(true, rxCommand.isSuccess());
        assertEquals(8, rxCommand.getBytes().length);
    }

    @Test
    public void isFailed() {

        int length = FAILED.getBytes().length;
        RxCommand rxCommand = new RxCommand(FAILED);

        assertEquals(10, length);
        assertEquals(true, rxCommand.isFailed());
        assertEquals(10, rxCommand.getBytes().length);
    }

    @Test
    public void getBytes() {
        byte[] okBytes = OK.getBytes();
        RxCommand rxCommand = new RxCommand(OK);
        assertArrayEquals(okBytes, rxCommand.getBytes());
    }

    @Test
    public void getRawCommand() {
        RxCommand rxCommand = new RxCommand(OK);
        assertEquals(OK, rxCommand.getRawCommand());
    }

    @Test
    public void get() {
        String rawCommand;
        RxCommand rxCommand;
        // Loop through ASCII charset and ensure we receive the same character back as passed in the original rxCommand.
        for (int c = 32; c < 128; c++) {
            char asciiCharacter = (char) c;
            rawCommand = "COMM" + asciiCharacter + "&ok\r";
            rxCommand = new RxCommand(rawCommand);
            assertEquals(9, rxCommand.getBytes().length);
            assertEquals(asciiCharacter, rxCommand.get(ProtocolConstants.BYTE_05));
        }
    }

    @Test
    public void getIndexFromTo() {
        String rawCommand;
        RxCommand rxCommand;
        // Loop through ASCII charset and ensure we receive the same character back as passed in the original rxCommand.
        for (int c = 32; c < 128; c++) {
            char asciiCharacter = (char) c;
            String chars = asciiCharacter + "" + asciiCharacter + "";
            rawCommand = "A" + chars + "&ok\r";
            rxCommand = new RxCommand(rawCommand);
            assertEquals(7, rxCommand.getBytes().length);
            assertEquals(chars, rxCommand.get(ProtocolConstants.BYTE_02, ProtocolConstants.BYTE_03));
        }

        rxCommand = new RxCommand("A");
        assertEquals("A", rxCommand.get(ProtocolConstants.BYTE_01, ProtocolConstants.BYTE_01));
    }

    @Test(expected = CommandValidationException.class)
    public void invalidConstructionTest() throws CommandValidationException {
        TxCommand txCommand = new TxCommand(null) {

            @Override
            protected String buildCommand() {
                return null;
            }

            @Override
            public String getResponseCode() {
                return null;
            }
        };
    }
}