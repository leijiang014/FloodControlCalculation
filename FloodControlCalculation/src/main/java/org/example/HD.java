package org.example;

public class HD extends Reservoir {


    public double usableStorage = 8.28;
    public double deadStorage = 8.42;


    public HD(double []inflow) {
        setPeriodNumber(inflow.length);
        setInflow(inflow);

        reservoirVolume[0] = 8.42;
        reservoirWaterLevel[0] = getLevelByVolume(reservoirVolume[0]);
    }
    @Override
    public double getLevelByVolume(double volume) {
        double[] xArray = {8.75, 8.96, 9.17, 9.38, 10.00, 10.83, 11.67, 12.50, 13.33, 14.48, 15.24};
        double[] yArray = {1598,1599,1600,1601,1604,1607,1610,1613,1616,1619,1622.78};
        if (volume <= xArray[0]) {
            return yArray[0];
        }
        if (volume >= xArray[xArray.length - 1]) {
            return yArray[yArray.length - 1];
        }

        for (int i = 0; i < xArray.length - 1; i++) {
            if (volume >= xArray[i] && volume <= xArray[i + 1]) {
                // 线性插值公式
                double t = (volume - xArray[i]) / (xArray[i + 1] - xArray[i]);
                return yArray[i] + t * (yArray[i + 1] - yArray[i]);
            }
        }

        return 0;
    }



    public double getMaxInstantOutflow(double volume) {
        double[] xArray = {8.75, 8.96, 9.17, 9.38, 10.00, 10.83, 11.67, 12.50, 13.33, 14.48, 15.24};
        double[] yArray = {0, 357.4970174, 632.4938402, 930.0841762, 1974.816225, 3226.339038, 4658.098959, 6250.640132, 7989.483585, 9863.4, 13851.5};

        if (volume <= xArray[0]) {
            return yArray[0];
        }
        if (volume >= xArray[xArray.length - 1]) {
            return yArray[yArray.length - 1];
        }

        // 查找输入值所在的区间
        for (int i = 0; i < xArray.length - 1; i++) {
            if (volume >= xArray[i] && volume <= xArray[i + 1]) {
                // 线性插值公式
                double t = (volume - xArray[i]) / (xArray[i + 1] - xArray[i]);
                return yArray[i] + t * (yArray[i + 1] - yArray[i]);
            }
        }

        // 如果没有找到合适的区间，返回0（这种情况一般不会发生）
        return 0;
    }

    public double getMaxPeriodAvgOutflow(double currentStorage, double inflow) {
        double Avgoutflow = getMaxInstantOutflow(currentStorage);
        double nextStorage = currentStorage + (inflow - getMaxInstantOutflow(currentStorage)) * T / 100000000;

        Avgoutflow = 0.5 * (getMaxInstantOutflow(currentStorage) + getMaxInstantOutflow(nextStorage));
        nextStorage = currentStorage + (inflow - Avgoutflow) * T / 100000000;

        Avgoutflow = 0.5 * (getMaxInstantOutflow(currentStorage) + getMaxInstantOutflow(nextStorage));
        nextStorage = currentStorage + (inflow - Avgoutflow) * T / 100000000;

        return Avgoutflow;
    }

    public void floodControlCalculate() {
        for (int i = 0; i < 24; i++) {

            outflow[i] = getMaxPeriodAvgOutflow(reservoirVolume[i], inflow[i]);
            reservoirVolume[i + 1] = reservoirVolume[i] + (inflow[i] - outflow[i]) * T / 100000000;
            reservoirWaterLevel[i + 1] = getLevelByVolume(reservoirVolume[i + 1]);
        }
    }





}
