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

package com.heinsmith.paradox.commands.zone.status;

import com.heinsmith.paradox.commands.CommandId;
import com.heinsmith.paradox.commands.CommandValidationException;
import com.heinsmith.paradox.commands.zone.ZoneTxCommand;

/**
 * Created by Hein Smith on 2017/03/24.
 */
public class ZoneStatus extends ZoneTxCommand<ZoneStatus.ZoneStatusResponse> {

    public ZoneStatus(int zone) throws CommandValidationException {
        super(CommandId.REQUEST_ZONE_STATUS, zone);
    }

    @Override
    public void parseResponse(String response) {
        this.response = new ZoneStatus.ZoneStatusResponse(response);
    }

    public enum State {
        CLOSED("C"), OPENED("O"), TILT("T"), FIRE_LOOP_DEFECT("F");

        private String state;

        State(String state) {
            this.state = state;
        }

        public static State parse(String state) {
            for (State s : State.values()) {
                if (s.state.equals(state)) {
                    return s;
                }
            }
            throw new IllegalArgumentException("unkown status: " + state);
        }
    }

    public class ZoneStatusResponse {

        public State state;

        public ZoneStatusResponse(String response) {
            state = State.parse(String.valueOf(response.charAt(0)));
        }

    }
}
