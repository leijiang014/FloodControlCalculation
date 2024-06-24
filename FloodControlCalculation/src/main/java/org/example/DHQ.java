package org.example;

public class DHQ extends Reservoir {

    public double usableStorage = 0.42;
    public double deadStorage =2.52;


    public DHQ(double []inflow) {
        setPeriodNumber(inflow.length);
        setInflow(inflow);

        reservoirVolume[0] = 2.52;
        reservoirWaterLevel[0] = getLevelByVolume(reservoirVolume[0]);
    }
    @Override
    public double getLevelByVolume(double volume) {
        //库容
        double[] xArray = {
                1.50,1.57,1.63,1.69,1.77,1.93,1.91,1.98,2.05,2.13,2.21,2.29,2.37,2.45,2.62,2.71,2.79,2.88
        };
        //水位
        double[] yArray = {
                1462,1463,1464,1465,1466,1467,1468,1469,1470,1471,1472,1473,1474,1475,1476,1477,1478,1479
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
        //下泄流量
        double[] xArray = {
                1.50,1.57,1.63,1.69,1.77,1.93,1.91,1.98,2.05,2.13,2.21,2.29,2.37,2.45,2.62,2.71,2.79,2.88
        };
        //水位
        double[] yArray = {
                142.63,403.41,741.12,1141.03,1594.63,2096.2,2641.51,3227.31,3850.96,4510.3,5203.49,5928.94,6685.29,7471.32,8285.95,9128.2,9997.21,10892.16
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
        for (int i = 0; i < 24; i++) {

            outflow[i] = getMaxPeriodAvgOutflow(reservoirVolume[i], inflow[i]);
            reservoirVolume[i + 1] = reservoirVolume[i] + (inflow[i] - outflow[i]) * T / 100000000;
            reservoirWaterLevel[i + 1] = getLevelByVolume(reservoirVolume[i + 1]);
        }
    }






}
