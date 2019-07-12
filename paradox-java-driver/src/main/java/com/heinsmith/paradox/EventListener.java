package com.heinsmith.paradox;

import com.heinsmith.paradox.commands.system.SystemEvent;

public interface EventListener {

    void fireResponse(SystemEvent event);
}
