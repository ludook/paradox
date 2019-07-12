package com.heinsmith.paradox.commands.system.event.action;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.DISARM_BY_USER)
public class DisarmByUser extends SystemEvent {

    public DisarmByUser(Integer number, Integer area) {
        super(number, area);
    }
}
