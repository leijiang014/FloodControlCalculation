package org.example;

public class DHQTest {
    public static void main(String[] args) {
        DHQ dhq = new DHQ(Statistcs.flood1);
        double T = 3600;
        double currentStorage = 2.52;
        double inflow = 2000;
        double outflow = dhq.getMaxInstantOutflow(currentStorage);
        System.out.println(outflow);
        double nextStorage = currentStorage + (inflow - dhq.getMaxInstantOutflow(currentStorage)) * T / 100000000;
        System.out.println(nextStorage);


        outflow = 0.5 * (dhq.getMaxInstantOutflow(currentStorage) + dhq.getMaxInstantOutflow(nextStorage));
        System.out.println(outflow);
        nextStorage = currentStorage + (inflow - outflow) * T / 100000000;


        outflow = 0.5 * (dhq.getMaxInstantOutflow(currentStorage) + dhq.getMaxInstantOutflow(nextStorage));
        System.out.println(outflow);
        nextStorage = currentStorage + (inflow - outflow) * T / 100000000;

        outflow = 0.5 * (dhq.getMaxInstantOutflow(currentStorage) + dhq.getMaxInstantOutflow(nextStorage));
        System.out.println(outflow);
        nextStorage = currentStorage + (inflow - outflow) * T / 100000000;

        System.out.println();
        dhq.getMaxPeriodAvgOutflow(13.33,9000);
        System.out.println(dhq.getLevelByVolume(13.33));

        System.out.println();
    }
}
