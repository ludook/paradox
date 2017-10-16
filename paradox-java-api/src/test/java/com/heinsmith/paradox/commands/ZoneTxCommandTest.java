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

import com.heinsmith.paradox.commands.zone.ZoneTxCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Hein Smith on 2017/10/16.
 */
public class ZoneTxCommandTest implements TxCommandTest {

    @Test
    @Override
    public void positiveConstructionTest() throws CommandValidationException {
        ZoneTxCommand txCommand = new ZoneTxCommand(CommandId.REQUEST_ZONE_LABEL, 5) {

        };
        assertEquals(5, txCommand.getZone());
    }

    @Test
    @Override
    public void responseCodeTest() throws CommandValidationException {
        ZoneTxCommand txCommand = new ZoneTxCommand(CommandId.REQUEST_ZONE_STATUS, 7) {

        };
        assertEquals("RZ007", txCommand.getResponseCode());
    }


    @Test
    public void invalidConstructionTest() throws CommandValidationException {
        assertThrows(CommandValidationException.class, () -> new ZoneTxCommand(null, 50) {

        });

        assertThrows(CommandValidationException.class, () -> new ZoneTxCommand(CommandId.REQUEST_ZONE_STATUS, 500) {

        });
    }
}
