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

package com.heinsmith.paradox.commands.area.status;

import com.heinsmith.paradox.ProtocolConstants;

/**
 * Created by Hein Smith on 2017/03/24.
 */
public class AreaStatusRequest {

    private int area;

    public AreaStatusRequest(int area) {
        this.area = area;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("RA");
        String areaPadded = String.format("%003d", area);
        builder.append(areaPadded);
        builder.append(ProtocolConstants.COMMAND_END);
        return builder.toString();
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
