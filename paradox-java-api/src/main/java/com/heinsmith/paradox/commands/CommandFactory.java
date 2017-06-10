package com.heinsmith.paradox.commands;

import com.heinsmith.paradox.commands.area.status.AreaStatusResponse;

/**
 * Created by somejavadev on 2017/03/18.
 */
public class CommandFactory {


    private static final String REQUEST_AREA_STATUS = "RA";

    public RxCommand getCommand(final String rawCommand) {

        final String type = rawCommand.substring(0,2);

        RxCommand rxCommand;

        switch (type) {
            case REQUEST_AREA_STATUS: {
                rxCommand = new AreaStatusResponse(rawCommand);
                break;
            }
            default:{
                rxCommand = null;
            }
        }
        return rxCommand;
    }
}
