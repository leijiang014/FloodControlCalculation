package org.example;

import java.util.Arrays;

public abstract class Reservoir {
    public double usableStorage;//调节库容
    public double deadStorage;//死库容

    double T = 3600;//计算的时段

    public double installedCapacity;//装机容量
    public double avgInflow ;
    public int periodNumber ;

    public void setPeriodNumber(int periodNumber) {
        inflow = new double[periodNumber];
        reservoirVolume = new double[periodNumber+1];
        reservoirWaterLevel = new double[periodNumber + 1];
        outflow = new double[periodNumber];
        this.periodNumber = periodNumber;
    }

    public double[] inflow ;
    public double[] reservoirVolume;
    public double[] reservoirWaterLevel ;
    public double[] outflow ;


    public void setInflow(double[] inflow) {

        for (int i = 0; i < inflow.length; i++) {
            this.inflow[i] = inflow[i];
        }

    }

    public double getLevelByVolume(double volume) {

        return 0;
    }




}
