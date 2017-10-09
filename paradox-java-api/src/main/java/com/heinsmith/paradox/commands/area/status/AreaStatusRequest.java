package com.heinsmith.paradox.commands.area.status;

import com.heinsmith.paradox.ProtocolConstants;

/**
 * Created by Hein Smith on 2017/03/24.
 */
public class AreaStatusRequest {

    private int area;

    public AreaStatusRequest(int area) {
        this.area = area;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("RA");
        String areaPadded = String.format("%003d", area);
        builder.append(areaPadded);
        builder.append(ProtocolConstants.COMMAND_END);
        return builder.toString();
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
