package com.heinsmith.paradox.commands;

/**
 * Created by Hein Smith on 2017/05/31.
 */
public enum CommandId {

    AREA_ARM("AA");

    private String key;

    CommandId(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
