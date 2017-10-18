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
import com.heinsmith.paradox.commands.user.label.UserLabel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Hein Smith on 2017/10/18.
 */
public class UserLabelTest implements TxCommandTest {

    @Test
    @Override
    public void positiveConstructionTest() throws CommandValidationException {
        UserLabel userLabel = new UserLabel(500);
        assertEquals(500, userLabel.getUserNumber());
        assertEquals("UL500\r", userLabel.getAscii());
    }

    @Test
    @Override
    public void responseCodeTest() throws CommandValidationException {
        UserLabel userLabel = new UserLabel(330);
        assertEquals("UL330", userLabel.getResponseCode());
    }
}
