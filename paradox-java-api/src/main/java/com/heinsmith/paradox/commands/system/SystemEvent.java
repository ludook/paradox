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

package com.heinsmith.paradox.commands.system;

import com.heinsmith.paradox.commands.system.event.action.ArmByKey;

public class SystemEvent {

    EventGroupId groupId;

    int group;
    int number;
    int area;

    public SystemEvent(Integer number, Integer area) {
        this.groupId = getClass().getAnnotation(ParadoxEvent.class).value();
        this.group = groupId.group;
        this.number = number;
        this.area = area;
    }

    public SystemEvent(Integer group, Integer number, Integer area) {
        this.group = group;
        this.number = number;
        this.area = area;
    }

    public EventGroupId getGroupId() {
        return groupId;
    }

    public int getGroup() {
        return group;
    }

    public int getNumber() {
        return number;
    }

    public int getArea() {
        return area;
    }

}
