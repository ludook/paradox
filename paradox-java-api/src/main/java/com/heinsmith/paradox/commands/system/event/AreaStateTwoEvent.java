package com.heinsmith.paradox.commands.system.event;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.STATE_TWO)
public class AreaStateTwoEvent extends SystemEvent {

    public AreaStateTwoEvent(Integer number, Integer area) {
        super(number, area);
    }
}
