package org.example.pojo;

public class MW extends Reservoir {
    public double usableStorage = 8.28;
    public double deadStorage = 8.42;

    public MW() {
        name = "MW";
    }

    public void setInflow(double[] inflow) {
        setPeriodNumber(inflow.length);
        System.arraycopy(inflow, 0, this.inflow, 0, inflow.length);
        reservoirVolume[0] = deadStorage;
        reservoirWaterLevel[0] = getLevelByVolume(reservoirVolume[0]);
    }
    @Override
    public double getLevelByVolume(double volume) {
        double[] xArray = {
                0,0.08,0.25,0.66,1.3,2.1,3.08,5.01,6.6,7.2


        };
        double[] yArray = {
                1275,1287,1300,1320,1340,1360,1380,1398,1408,1414.8


        };
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
        double[] xArray = {
                5.01,5.17,5.33,5.49,5.65,5.81,5.96,6.12,6.28,6.44,6.6,6.78,6.95,7.22
        };
        double[] yArray = {
                0,257.8125,729.2038681,1339.633046,2062.5,2882.431377,3789.054446,4774.754319,5833.630945,6960.9375,8152.747093,10717.06437,13505.04463,16500

        };

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
        for (int i = 0; i < inflow.length; i++) {

            outflow[i] = getMaxPeriodAvgOutflow(reservoirVolume[i], inflow[i]);
            reservoirVolume[i + 1] = reservoirVolume[i] + (inflow[i] - outflow[i]) * T / 100000000;
            reservoirWaterLevel[i + 1] = getLevelByVolume(reservoirVolume[i + 1]);
        }
    }
}
