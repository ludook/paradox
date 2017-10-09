package com.heinsmith.paradox.commands.area.arm;

/**
 * Created by Hein Smith on 2017/05/31.
 */
public enum ArmType {

    REGULAR_ARM("A"),
    FORCE_ARM("F"),
    STAY_ARM("S"),
    INSTANT_ARM("I");

    private String key;

    ArmType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
