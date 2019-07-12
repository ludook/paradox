package com.heinsmith.paradox.commands.system.event;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.ZONE_ALARM)
public class AlarmZoneEvent extends SystemEvent {

    public AlarmZoneEvent(Integer number, Integer area) {
        super(number, area);
    }
}
