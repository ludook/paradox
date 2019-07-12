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

import com.heinsmith.paradox.commands.CommandId;
import com.heinsmith.paradox.commands.CommandValidationException;
import com.heinsmith.paradox.commands.area.AreaTxCommand;

/**
 * Created by Hein Smith on 2017/03/24.
 */
public class AreaStatus extends AreaTxCommand<AreaStatus.AreaStatusResponse> {

    public AreaStatus(int area) throws CommandValidationException {
        super(CommandId.REQUEST_AREA_STATUS, area);
    }

    @Override
    public void parseResponse(String response) {
        this.response = new AreaStatusResponse(response);
    }

    public enum State {
        DISARM("D"), ARM("A"), FORCED_ARM("F"), PARTIAL_ARM("P"), INSTANT_ARM("I");

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

    public enum ReadyState {
        READY("O"), NOT_READY("N");

        private String state;

        ReadyState(String state) {
            this.state = state;
        }

        public static ReadyState parse(String state) {
            for (ReadyState s : ReadyState.values()) {
                if (s.state.equals(state)) {
                    return s;
                }
            }
            throw new IllegalArgumentException("unkown ready state: " + state);
        }
    }

    public enum MemoryState {
        OK("O"), MEMORY_ALARM("M");

        private String state;

        MemoryState(String state) {
            this.state = state;
        }

        public static MemoryState parse(String state) {
            for (MemoryState s : MemoryState.values()) {
                if (s.state.equals(state)) {
                    return s;
                }
            }
            throw new IllegalArgumentException("unkown memory state: " + state);
        }
    }

    public class AreaStatusResponse {

        public State state;
        public ReadyState ready;
        public MemoryState memory;

        public AreaStatusResponse(String response) {
            state = State.parse(String.valueOf(response.charAt(0)));
            memory = MemoryState.parse(String.valueOf(response.charAt(1)));
            ready = ReadyState.parse(String.valueOf(response.charAt(3)));
        }

    }
}
