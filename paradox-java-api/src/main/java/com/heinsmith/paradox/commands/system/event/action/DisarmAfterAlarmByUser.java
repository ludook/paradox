package com.heinsmith.paradox.commands.system.event.action;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.DISARM_AFTER_ALARM_BY_USER)
public class DisarmAfterAlarmByUser extends SystemEvent {

    public DisarmAfterAlarmByUser(Integer number, Integer area) {
        super(number, area);
    }
}
