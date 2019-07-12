package com.heinsmith.paradox.commands.system;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ParadoxEvent {
    EventGroupId value();
}
