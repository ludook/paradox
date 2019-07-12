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

import com.heinsmith.paradox.commands.area.arm.AreaArm;
import com.heinsmith.paradox.commands.area.arm.ArmType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by Hein Smith on 2017/10/12.
 */
class AreaArmTest implements TxCommandTest {

    private static final char[] testCode = new char[]{'1', '2', '3', '4'};

    @Test
    @Override
    public void positiveConstructionTest() throws CommandValidationException {
        AreaArm areaArm = new AreaArm(4, ArmType.REGULAR_ARM, testCode);
        assertEquals("AA004A1234\r", areaArm.getAscii());
        assertEquals(ArmType.REGULAR_ARM, areaArm.getArmType());
        assertEquals(4, areaArm.getArea());
        assertSame(testCode, areaArm.getPassword(false));
    }

    @Test
    @Override
    public void responseCodeTest() throws CommandValidationException {
        AreaArm areaArm = new AreaArm(1, ArmType.REGULAR_ARM, testCode);
        assertEquals("AA001", areaArm.getResponseCode());
    }


    @Test
    void armTypesTest() throws CommandValidationException {

        AreaArm areaArm = new AreaArm(1, ArmType.REGULAR_ARM, testCode);
        assertEquals("AA001A1234\r", areaArm.getAscii());

        areaArm = new AreaArm(2, ArmType.FORCE_ARM, testCode);
        assertEquals("AA002F1234\r", areaArm.getAscii());

        areaArm = new AreaArm(3, ArmType.INSTANT_ARM, testCode);
        assertEquals("AA003I1234\r", areaArm.getAscii());

        areaArm = new AreaArm(4, ArmType.STAY_ARM, testCode);
        assertEquals("AA004S1234\r", areaArm.getAscii());

        assertThrows(CommandValidationException.class, () -> new AreaArm(4, null, testCode));
    }

    @Test
    void areaTest() {
        assertThrows(CommandValidationException.class, () -> new AreaArm(0, ArmType.REGULAR_ARM, testCode));
        assertThrows(CommandValidationException.class, () -> new AreaArm(9, ArmType.REGULAR_ARM, testCode));
    }

    @Test
    void codeTest() {
        assertThrows(CommandValidationException.class, () -> {
            char[] longCode = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
            new AreaArm(1, ArmType.REGULAR_ARM, longCode);
        });

        assertThrows(CommandValidationException.class, () -> {
            char[] shortCode = new char[]{};
            new AreaArm(4, ArmType.REGULAR_ARM, shortCode);
        });

        assertThrows(CommandValidationException.class, () -> new AreaArm(4, ArmType.REGULAR_ARM, null));
    }
}
