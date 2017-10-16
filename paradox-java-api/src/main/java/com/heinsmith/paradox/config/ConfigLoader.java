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

package com.heinsmith.paradox.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Hein Smith on 2017/10/16.
 */
public class ConfigLoader {

    private static final Logger logger = LogManager.getLogger(ConfigLoader.class.getName());

    private ConfigLoader() {
    }

    public static Properties getConfig() {

        Properties properties = new Properties();
        ClassLoader classLoader = ConfigLoader.class.getClassLoader();
        URL fileUrl = classLoader.getResource("paradox.properties");

        if (fileUrl != null) {
            String fileString = fileUrl.getFile();

            if (fileString != null) {
                File configFile = new File(fileString);

                try (FileInputStream fileInputStream = new FileInputStream(configFile)) {
                    properties.load(fileInputStream);
                } catch (IOException ioException) {
                    logger.error(ioException);
                }
            }
        }
        return properties;
    }
}
