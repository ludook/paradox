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

package com.heinsmith.paradox;

/**
 * Created by Hein Smith on 2017/03/18.
 *
 * Constants file which includes constants used by the
 * APR3-PRT3 Printer module from paradox alarm systems.
 *
 */
public interface ProtocolConstants {

    Character COMMAND_END = '\r';
    String COMMAND_OK = "&ok" + COMMAND_END;
    String COMMAND_FAIL = "&fail" + COMMAND_END;
    String BUFFER_FULL = "!" + COMMAND_END;


    // BYTES from specification are 0 indexed.
    int BYTE_01 = 0;
    int BYTE_02 = 1;
    int BYTE_03 = 2;
    int BYTE_04 = 3;
    int BYTE_05 = 4;
    int BYTE_06 = 5;
    int BYTE_07 = 6;
    int BYTE_08 = 7;
    int BYTE_09 = 8;
    int BYTE_10 = 9;
    int BYTE_11 = 10;
    int BYTE_12 = 11;

}
