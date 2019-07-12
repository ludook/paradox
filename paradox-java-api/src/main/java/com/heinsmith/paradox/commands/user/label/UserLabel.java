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

package com.heinsmith.paradox.commands.user.label;

import com.heinsmith.paradox.CommonValidationUtils;
import com.heinsmith.paradox.commands.CommandId;
import com.heinsmith.paradox.commands.CommandValidationException;
import com.heinsmith.paradox.commands.TxCommand;

/**
 * Created by Hein Smith on 2017/03/24.
 */
public class UserLabel extends TxCommand {

    private int userNumber;

    public UserLabel(int userNumber) throws CommandValidationException {
        super(CommandId.REQUEST_USER_LABEL);

        if (CommonValidationUtils.invalidUserNumber(userNumber)) {
            throw new CommandValidationException();
        }
        this.userNumber = userNumber;
    }

    @Override
    protected String buildCommand(boolean obfuscate) {
        return String.format("%03d", userNumber);
    }

    @Override
    public void parseResponse(String repsonse) {
        //TODO
    }

    public int getUserNumber() {
        return userNumber;
    }
}
