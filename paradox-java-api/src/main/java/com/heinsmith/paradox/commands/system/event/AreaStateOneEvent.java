package com.heinsmith.paradox.commands.system.event;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.STATE_ONE)
public class AreaStateOneEvent extends SystemEvent {

    public AreaStateOneEvent(Integer number, Integer area) {
        super(number, area);
    }
}
