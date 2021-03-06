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

/**
 * Created by Hein Smith on 2017/05/31.
 */
public enum CommandId {

    AREA_ARM("AA"),
    AREA_QUICK_ARM("AQ"),
    VIRTUAL_INPUT_OPEN("VO"),
    VIRTUAL_INPUT_CLOSE("VC"),
    REQUEST_AREA_STATUS("RA"),
    REQUEST_ZONE_STATUS("RZ"),
    REQUEST_ZONE_LABEL("RL"),
    REQUEST_AREA_LABEL("AL"),
    REQUEST_USER_LABEL("UL"),
    EMERGENCY_PANIC("PE"),
    MEDICAL_PANIC("PM"),
    FIRE_PANIC("PF"),
    SMOKE_RESET("SR"),
    UTILITY_KEY("UK"),
    AREA_DISARM("AD");

    private String key;

    CommandId(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
