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
 * Created by Hein Smith on 2017/10/12.
 */
public class CommonValidationUtils {

    private CommonValidationUtils() {
    }

    public static boolean invalidPanelCode(char[] code) {
        boolean result = (code == null || code.length < 4 || code.length > 6);
        if(!result) {
            checkDigit:
            for (int i = 0; i < code.length; i++) {
                char codeEntry = code[i];
                if(!Character.isDigit(codeEntry)) {
                    result = true;
                    break checkDigit;
                }
            }
        }
        return result;
    }

    public static boolean invalidAreaNumber(int area) {
        return (area < 1 || area > 8);
    }
}
