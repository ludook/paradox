package com.heinsmith.paradox.commands.system.event.action;

import com.heinsmith.paradox.commands.system.EventGroupId;
import com.heinsmith.paradox.commands.system.ParadoxEvent;
import com.heinsmith.paradox.commands.system.SystemEvent;

@ParadoxEvent(EventGroupId.USER_CODE_KEYBOARD)
public class UserCodeKeyboardEvent extends SystemEvent {

    public UserCodeKeyboardEvent(Integer number, Integer area) {
        super(number, area);
    }

}
