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

import com.heinsmith.paradox.commands.zone.status.ZoneStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Hein Smith on 2017/10/16.
 */
public class ZoneStatusTest implements TxCommandTest {

    @Test
    @Override
    public void positiveConstructionTest() throws CommandValidationException {
        ZoneStatus zoneStatus = new ZoneStatus(50);
        assertEquals(50, zoneStatus.getZone());
        assertEquals("RZ050\r", zoneStatus.getAscii());

    }

    @Test
    @Override
    public void responseCodeTest() throws CommandValidationException {
        ZoneStatus zoneStatus = new ZoneStatus(56);
        assertEquals("RZ056", zoneStatus.getResponseCode());
    }
}
