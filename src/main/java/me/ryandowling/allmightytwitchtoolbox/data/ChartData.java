package me.ryandowling.allmightytwitchtoolbox.data;

import java.util.Date;
import java.util.List;

public class ChartData {
    private List<Date> xAxis;
    private List<Integer> yAxis;

    public ChartData(List<Date> xAxis, List<Integer> yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public List<Date> getxAxis() {
        return xAxis;
    }

    public List<Integer> getyAxis() {
        return this.yAxis;
    }
}
