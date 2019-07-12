package com.heinsmith.paradox.commands.system.event.action;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.ARM_SPECIAL)
public class ArmSpecial extends SystemEvent {

    public ArmSpecial(Integer number, Integer area) {
        super(number, area);
    }
}
