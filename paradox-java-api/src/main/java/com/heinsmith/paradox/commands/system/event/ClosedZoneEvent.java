package com.heinsmith.paradox.commands.system.event;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.ZONE_CLOSE)
public class ClosedZoneEvent extends SystemEvent {

    public ClosedZoneEvent(Integer number, Integer area) {
        super(number, area);
    }
}
