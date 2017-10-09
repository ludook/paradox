package com.heinsmith.paradox.commands.area.arm;

import com.heinsmith.paradox.commands.CommandId;
import com.heinsmith.paradox.commands.CommandValidationException;
import com.heinsmith.paradox.commands.TxCommand;

/**
 * Created by Hein Smith on 2017/03/24.
 */
public class AreaArm extends TxCommand {

    private int area;
    private char[] password;
    private ArmType armType;

    public AreaArm(int area, ArmType armType, char[] password) {
        super(CommandId.AREA_ARM);
        this.armType = armType;
        this.area = area;
        this.password = password;
    }

    @Override
    protected String buildCommand() throws CommandValidationException {
        if(area < 0 || area > 8) {
            throw new CommandValidationException();
        }

        if(armType == null) {
            throw new CommandValidationException();
        }

        if(password == null || password.length < 1) {
            throw new CommandValidationException();
        }

        StringBuilder builder =  new StringBuilder();
        builder.append(String.format("%03d", String.valueOf(area)));
        builder.append(armType.getKey());
        builder.append(password);

        return builder.toString();
    }

    public int getArea() {
        return area;
    }

    public char[] getPassword() {
        return password;
    }

    public ArmType getArmType() {
        return armType;
    }
}
