package com.heinsmith.paradox.commands.system.event.action;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.DISARM_AFTER_ALARM_BY_KEY)
public class DisarmAfterAlarmByKey extends SystemEvent {

    public DisarmAfterAlarmByKey(Integer number, Integer area) {
        super(number, area);
    }
}
