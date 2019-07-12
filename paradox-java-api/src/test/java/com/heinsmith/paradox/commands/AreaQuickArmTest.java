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

    @Override
    public void positiveConstructionTest() throws CommandValidationException {
        AreaQuickArm areaQuickArm = new AreaQuickArm(2, ArmType.REGULAR_ARM);
        assertEquals("AQ002A1234\r", areaQuickArm.getAscii());
        assertEquals(2, areaQuickArm.getArea());
        assertEquals(ArmType.REGULAR_ARM, areaQuickArm.getArmType());
        assertEquals("", areaQuickArm.getPassword(false));
    }

    @Override
    public void responseCodeTest() throws CommandValidationException {
        AreaQuickArm areaQuickArm = new AreaQuickArm(1, ArmType.REGULAR_ARM);
        assertEquals("AQ001", areaQuickArm.getResponseCode());
    }


    @Test
    void armTypesTest() throws CommandValidationException {

        AreaQuickArm areaQuickArm = new AreaQuickArm(1, ArmType.REGULAR_ARM);
        assertEquals("AQ001A\r", areaQuickArm.getAscii());

        areaQuickArm = new AreaQuickArm(2, ArmType.FORCE_ARM);
        assertEquals("AQ002F\r", areaQuickArm.getAscii());

        areaQuickArm = new AreaQuickArm(3, ArmType.INSTANT_ARM);
        assertEquals("AQ003I\r", areaQuickArm.getAscii());

        areaQuickArm = new AreaQuickArm(4, ArmType.STAY_ARM);
        assertEquals("AQ004S\r", areaQuickArm.getAscii());

        assertThrows(CommandValidationException.class, () -> new AreaQuickArm(2, null));
    }

    @Test
    void areaTest() throws CommandValidationException {
        assertThrows(CommandValidationException.class, () -> new AreaQuickArm(0, ArmType.REGULAR_ARM));
        assertThrows(CommandValidationException.class, () -> new AreaQuickArm(9, ArmType.REGULAR_ARM));
    }

}
