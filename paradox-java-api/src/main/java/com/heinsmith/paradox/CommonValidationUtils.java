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

import com.heinsmith.paradox.config.ConfigLoader;

import java.util.Properties;

/**
 * Created by Hein Smith on 2017/10/12.
 */
public class CommonValidationUtils {

    private static final int inputMax;
    private static final int areaMax;
    private static final int panelCodeMax;
    private static final int panelCodeMin;

    static {
        Properties configProperties = ConfigLoader.getConfig();
        inputMax = parseString(configProperties.getProperty("inputMax"), 16);
        areaMax = parseString(configProperties.getProperty("areaMax"), 8);
        panelCodeMin = parseString(configProperties.getProperty("panelCodeMin"), 4);
        panelCodeMax = parseString(configProperties.getProperty("panelCodeMax"), 6);
    }

    private CommonValidationUtils() {
    }

    public static boolean invalidInputNumber(int number) {
        return (number < 1 || number > inputMax);
    }

    public static boolean invalidPanelCode(char[] code) {
        boolean result = (code == null || code.length < panelCodeMin || code.length > panelCodeMax);
        if (!result) {
            for (char codeEntry : code) {
                if (!Character.isDigit(codeEntry)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public static boolean invalidAreaNumber(int area) {
        return (area < 1 || area > areaMax);
    }

    private static int parseString(String prop, int defaultValue) {
        int result = defaultValue;
        if (prop != null && !prop.isEmpty()) {
            try {
                result = Integer.parseInt(prop);
            } catch (NumberFormatException exception) {
                result = defaultValue;
            }
        }
        return result;
    }
}
