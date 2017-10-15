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

import com.heinsmith.paradox.commands.area.arm.AreaQuickArm;
import com.heinsmith.paradox.commands.area.arm.ArmType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Created by Hein Smith on 2017/10/12.
 */
class AreaQuickArmTest implements TxCommandTest {

    private static final char[] testCode = new char[]{'1', '2', '3', '4'};

    @Override
    public void positiveConstructionTest() throws CommandValidationException {
        AreaQuickArm areaQuickArm = new AreaQuickArm(2, ArmType.REGULAR_ARM, testCode);
        assertEquals("AQ002A1234\r", areaQuickArm.getAscii());
        assertEquals(2, areaQuickArm.getArea());
        assertEquals(ArmType.REGULAR_ARM, areaQuickArm.getArmType());
        assertEquals(testCode, areaQuickArm.getPassword());
    }

    @Override
    public void responseCodeTest() throws CommandValidationException {
        AreaQuickArm areaQuickArm = new AreaQuickArm(1, ArmType.REGULAR_ARM, testCode);
        assertEquals("AQ001", areaQuickArm.getResponseCode());
    }


    @Test
    void armTypesTest() throws CommandValidationException {

        AreaQuickArm areaQuickArm = new AreaQuickArm(1, ArmType.REGULAR_ARM, testCode);
        assertEquals("AQ001A1234\r", areaQuickArm.getAscii());

        areaQuickArm = new AreaQuickArm(2, ArmType.FORCE_ARM, testCode);
        assertEquals("AQ002F1234\r", areaQuickArm.getAscii());

        areaQuickArm = new AreaQuickArm(3, ArmType.INSTANT_ARM, testCode);
        assertEquals("AQ003I1234\r", areaQuickArm.getAscii());

        areaQuickArm = new AreaQuickArm(4, ArmType.STAY_ARM, testCode);
        assertEquals("AQ004S1234\r", areaQuickArm.getAscii());

        assertThrows(CommandValidationException.class, () -> new AreaQuickArm(2, null, testCode));
    }

    @Test
    void areaTest() throws CommandValidationException {
        assertThrows(CommandValidationException.class, () -> new AreaQuickArm(0, ArmType.REGULAR_ARM, testCode));
        assertThrows(CommandValidationException.class, () -> new AreaQuickArm(9, ArmType.REGULAR_ARM, testCode));
    }


    @Test
    void codeTest() throws CommandValidationException {
        assertThrows(CommandValidationException.class, () -> {
            char[] longCode = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
            new AreaQuickArm(1, ArmType.REGULAR_ARM, longCode);
        });

        assertThrows(CommandValidationException.class, () -> {
            char[] shortCode = new char[]{};
            new AreaQuickArm(4, ArmType.REGULAR_ARM, shortCode);
        });
    }
}
