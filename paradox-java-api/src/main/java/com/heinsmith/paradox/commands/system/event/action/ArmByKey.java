package com.heinsmith.paradox.commands.system.event.action;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.ARM_BY_KEY)
public class ArmByKey extends SystemEvent {

    public ArmByKey(Integer number, Integer area) {
        super(number, area);
    }
}
