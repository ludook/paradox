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

package com.heinsmith.paradox.commands.area.arm;

import com.heinsmith.paradox.CommonValidationUtils;
import com.heinsmith.paradox.commands.CommandId;
import com.heinsmith.paradox.commands.CommandValidationException;
import com.heinsmith.paradox.commands.TxCommand;
import com.heinsmith.paradox.commands.area.AreaTxCommand;

/**
 * Created by Hein Smith on 2017/03/24.
 */
public abstract class AreaArmBase extends AreaTxCommand<Void> {

    private char[] password;
    private ArmType armType;

    public AreaArmBase(CommandId commandId, int area, ArmType armType) throws CommandValidationException {
        super(commandId, area);

        if (armType == null) {
            throw new CommandValidationException();
        }

        this.password = "".toCharArray();
        this.armType = armType;
    }

    public AreaArmBase(CommandId commandId, int area, ArmType armType, char[] password) throws CommandValidationException {
        this(commandId, area, armType);

        if (CommonValidationUtils.invalidPanelCode(password)) {
            throw new CommandValidationException();
        }

        this.password = password;
    }

    @Override
    protected String buildCommand(boolean obfuscate) {
        StringBuilder builder = new StringBuilder();
        builder.append(super.buildCommand(obfuscate));
        builder.append(armType.getKey());
        builder.append(getPassword(obfuscate));
        return builder.toString();
    }

    public char[] getPassword(boolean obfuscate) {
        return obfuscate ? "****".toCharArray() : password;
    }

    public ArmType getArmType() {
        return armType;
    }
}
