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

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventFactory {

    public final static String PATTERN = "^G([0-9]{3})N([0-9]{3})A([0-9]{3})$";
    private static final Logger log = LogManager.getLogger(EventFactory.class.getName());
    private static Map<EventGroupId, Class<? extends SystemEvent>> eventGroupMap = new HashMap<>();

    public static void init() {
        log.info("scan for declared paradox events ...");
        log.info("package scan: " + EventFactory.class.getPackage().getName());
        Reflections reflections = new Reflections(EventFactory.class.getPackage().getName());
        reflections.getTypesAnnotatedWith(ParadoxEvent.class).forEach(clazz -> {
            log.info("found paradox event: " + clazz.getSimpleName() + ", groupKey: " + clazz.getAnnotation(ParadoxEvent.class).value());
            eventGroupMap.put(clazz.getAnnotation(ParadoxEvent.class).value(), (Class<? extends SystemEvent>) clazz);
        });
    }

    public static boolean isWellFormed(String event) {
        return event.matches(PATTERN);
    }

    public static SystemEvent build(String event) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(event);
        matcher.find();
        try {
            EventGroupId group = EventGroupId.get(Integer.parseInt(matcher.group(1)));
            if (eventGroupMap.containsKey(group)) {
                log.info("group found: " + group);
                try {
                    return eventGroupMap.get(group).getConstructor(new Class[]{Integer.class, Integer.class})
                            .newInstance(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ex) {
                    log.error("event not instanciable: " + group, ex);
                    return new SystemEvent(group.group, Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                }
            } else {
                log.warn("event not registered: " + group);
                return new SystemEvent(group.group, Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
            }
        } catch (IllegalArgumentException ex) {
            log.warn("event unkown : ", ex);
            return new SystemEvent(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        }
    }

}
