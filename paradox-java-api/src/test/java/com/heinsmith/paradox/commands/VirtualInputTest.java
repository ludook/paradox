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

import com.heinsmith.paradox.commands.virtual.input.VirtualInput;
import com.heinsmith.paradox.commands.virtual.input.VirtualInputClose;
import com.heinsmith.paradox.commands.virtual.input.VirtualInputOpen;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hein Smith on 2017/10/13.
 */
public class VirtualInputTest {

    @Test
    public void testVirtualInputAscii() throws CommandValidationException {

        VirtualInput virtualInput = new VirtualInputOpen(16);
        assertEquals("VO016\r", virtualInput.getAscii());

        virtualInput = new VirtualInputClose(4);
        assertEquals("VC004\r", virtualInput.getAscii());
    }


    @Test
    public void testVirtualInputResponse() throws CommandValidationException {

        VirtualInput virtualInput = new VirtualInputOpen(1);
        assertEquals("VO001", virtualInput.getResponseCode());

        virtualInput = new VirtualInputClose(2);
        assertEquals("VC002", virtualInput.getResponseCode());
    }
}
