package com.heinsmith.paradox.commands.system.event.action;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.DISARM_BY_KEY)
public class DisarmByKey extends SystemEvent {

    public DisarmByKey(Integer number, Integer area) {
        super(number, area);
    }
}
