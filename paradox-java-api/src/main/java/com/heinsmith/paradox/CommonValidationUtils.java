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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * Created by Hein Smith on 2017/10/12.
 */
public class CommonValidationUtils {

    private static final int INPUT_MAX;
    private static final int AREA_MAX;
    private static final int PANEL_CODE_MAX;
    private static final int PANEL_CODE_MIN;
    private static final int ZONE_MAX;
    private static final int USER_MAX;
    private static final Logger LOG = LogManager.getLogger(CommonValidationUtils.class.getName());

    static {
        Properties configProperties = ConfigLoader.getConfig();
        INPUT_MAX = parseString(configProperties.getProperty("inputMax"), 16);
        AREA_MAX = parseString(configProperties.getProperty("areaMax"), 8);
        PANEL_CODE_MIN = parseString(configProperties.getProperty("panelCodeMin"), 4);
        PANEL_CODE_MAX = parseString(configProperties.getProperty("panelCodeMax"), 6);
        ZONE_MAX = parseString(configProperties.getProperty("zoneMax"), 96);
        USER_MAX = parseString(configProperties.getProperty("userMax"), 999);
    }

    private CommonValidationUtils() {
    }

    public static boolean invalidInputNumber(int number) {
        return (number < 1 || number > INPUT_MAX);
    }

    public static boolean invalidPanelCode(char[] code) {
        boolean result = (code == null || code.length < PANEL_CODE_MIN || code.length > PANEL_CODE_MAX);
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
        return (area < 1 || area > AREA_MAX);
    }


    public static boolean invalidUserNumber(int number) {
        return (number < 1 || number > USER_MAX);
    }

    public static boolean invalidZoneNumber(int zone) {
        return (zone < 1 || zone > ZONE_MAX);
    }

    private static int parseString(String prop, int defaultValue) {
        int result = defaultValue;
        if (prop != null && !prop.isEmpty()) {
            try {
                result = Integer.parseInt(prop);
            } catch (NumberFormatException exception) {
                LOG.info("Invalid property", exception);
            }
        }
        return result;
    }
}
