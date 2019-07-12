package com.heinsmith.paradox.commands.system.event;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.ZONE_OPEN)
public class OpenZoneEvent extends SystemEvent {

    public OpenZoneEvent(Integer number, Integer area) {
        super(number, area);
    }
}
