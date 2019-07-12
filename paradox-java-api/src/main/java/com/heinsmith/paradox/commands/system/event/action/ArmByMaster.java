package com.heinsmith.paradox.commands.system.event.action;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.ARM_BY_MASTER)
public class ArmByMaster extends SystemEvent {

    public ArmByMaster(Integer number, Integer area) {
        super(number, area);
    }
}
