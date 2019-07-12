package com.heinsmith.paradox.commands.system.event;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.NON_RAPPORTABLE_EVENT)
public class NonRapportableEvent extends SystemEvent {

    public NonRapportableEvent(Integer number, Integer area) {
        super(number, area);
    }
}
