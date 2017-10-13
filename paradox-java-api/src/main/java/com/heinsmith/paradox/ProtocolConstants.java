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
public class ProtocolConstants {

    private ProtocolConstants() {
    }

    public static  final Character COMMAND_END = '\r';
    public static  final String COMMAND_OK = "&ok" + COMMAND_END;
    public static  final String COMMAND_FAIL = "&fail" + COMMAND_END;
    public static  final String BUFFER_FULL = "!" + COMMAND_END;

    // BYTES from specification are 0 indexed.
    public static  final int BYTE_01 = 0;
    public static  final int BYTE_02 = 1;
    public static  final int BYTE_03 = 2;
    public static  final int BYTE_04 = 3;
    public static  final int BYTE_05 = 4;
    public static  final int BYTE_06 = 5;
    public static  final int BYTE_07 = 6;
    public static  final int BYTE_08 = 7;
    public static  final int BYTE_09 = 8;
    public static  final int BYTE_10 = 9;
    public static  final int BYTE_11 = 10;
    public static  final int BYTE_12 = 11;

}
