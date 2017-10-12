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

import com.heinsmith.paradox.commands.area.disarm.AreaDisarm;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hein Smith on 2017/10/12.
 */
public class AreaDisarmTest {

    private static char[] testCode = new char[]{'4', '2', '3', '4'};

    @Test
    public void testResponseCode() throws CommandValidationException {
        AreaDisarm areaDisarm = new AreaDisarm(1, testCode);
        assertEquals("AD001", areaDisarm.getResponseCode());

        areaDisarm = new AreaDisarm(8, testCode);
        assertEquals("AD008", areaDisarm.getResponseCode());
    }

    @Test
    public void testDisarmAreas() throws CommandValidationException {
        AreaDisarm areaDisarm = new AreaDisarm(1, testCode);
        assertEquals("AD0014234\r", areaDisarm.getAscii());

        areaDisarm = new AreaDisarm(4, testCode);
        assertEquals("AD0044234\r", areaDisarm.getAscii());
    }

    @Test(expected = CommandValidationException.class)
    public void testLongCodeDisarm() throws CommandValidationException {
        char[] longCode = new char[]{'1', '2', '3', '4', '5', '6', '7'};
        new AreaDisarm(1, longCode);
    }

    @Test(expected = CommandValidationException.class)
    public void testShortCodeDisarm() throws CommandValidationException {
        char[] shortCode = new char[]{'1', '2'};
        new AreaDisarm(1, shortCode);
    }
}
