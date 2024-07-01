package org.example.pojo;

public class DHQ extends Reservoir {
    public double usableStorage = 0.42;
    public double deadStorage =2.52;

    public DHQ() {
        name = "DHQ";
    }

    public void setInflow(double[] inflow) {
        setPeriodNumber(inflow.length);
        System.arraycopy(inflow, 0, this.inflow, 0, inflow.length);
        reservoirVolume[0] = deadStorage;
        reservoirWaterLevel[0] = getLevelByVolume(reservoirVolume[0]);
    }
    @Override
    public double getLevelByVolume(double volume) {
        //库容
        double[] xArray = {
                0.0,48.8,280.9,634.4,1137.7,1908.4,2929.9,4319.9,6151.3,8285.1,10743.7,13677.2,16940.0,20457.6,22100.0,22867.1,24418.1,25363.8,27934.6,28854.2



        };
        //水位
        double[] yArray = {
                1406,1410,1415,1420,1425,1430,1435,1440,1445,1450,1455,1460,1465,1470,1472,1473,1475,1476,1479,1480



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
                2.21,2.29,2.36,2.44,2.54,2.62,2.67,2.71
        };
        //水位
        double[] yArray = {
                0,886,2507,4606,7091,9910,11433,13500

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
