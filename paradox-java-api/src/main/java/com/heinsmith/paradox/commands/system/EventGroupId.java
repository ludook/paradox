package com.heinsmith.paradox.commands.system;

import java.util.Arrays;

public enum EventGroupId {

    ZONE_CLOSE(0), ZONE_OPEN(1),
    NON_RAPPORTABLE_EVENT(4),
    USER_CODE_KEYBOARD(5),
    ARM_BY_MASTER(9), ARM_BY_USER(10), ARM_BY_KEY(11), ARM_SPECIAL(12),
    DISARM_BY_MASTER(13), DISARM_BY_USER(14), DISARM_BY_KEY(15),
    DISARM_AFTER_ALARM_BY_MASTER(16), DISARM_AFTER_ALARM_BY_USER(17), DISARM_AFTER_ALARM_BY_KEY(18),
    CANCEL_ALARM_BY_MASTER(19), CANCEL_ALARM_BY_USER(20), CANCEL_ALARM_BY_KEY(21),
    ZONE_ALARM(24),
    STATE_ONE(64), STATE_TWO(65);

    public int group;

    EventGroupId(int group) {
        this.group = group;
    }

    public static EventGroupId get(int group) {
        for (EventGroupId e : Arrays.asList(EventGroupId.values())) {
            if (e.group == group) {
                return e;
            }
        }
        throw new IllegalArgumentException("groupe inconnu: " + group);
    }

}
