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

package com.heinsmith.paradox.commands.panic;

import com.heinsmith.paradox.commands.CommandId;
import com.heinsmith.paradox.commands.CommandValidationException;
import com.heinsmith.paradox.commands.area.AreaTxCommand;

/**
 * Created by Hein Smith on 2017/03/24.
 */
public class SmokeReset extends AreaTxCommand {

    public SmokeReset(int area) throws CommandValidationException {
        super(CommandId.SMOKE_RESET, area);
    }
}
