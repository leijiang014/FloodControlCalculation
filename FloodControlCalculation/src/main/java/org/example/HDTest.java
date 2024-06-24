package org.example;

public class HDTest {
    public static void main(String[] args) {
        HD hd = new HD(Statistcs.flood1);
        double T = 3600;
        double currentStorage = 13.33;
        double inflow = 9000;
        double outflow = hd.getMaxInstantOutflow(currentStorage);
        System.out.println(outflow);
        double nextStorage = currentStorage + (inflow - hd.getMaxInstantOutflow(currentStorage)) * T / 100000000;
        System.out.println(nextStorage);


        outflow = 0.5 * (hd.getMaxInstantOutflow(currentStorage) + hd.getMaxInstantOutflow(nextStorage));
        System.out.println(outflow);
        nextStorage = currentStorage + (inflow - outflow) * T / 100000000;


        outflow = 0.5 * (hd.getMaxInstantOutflow(currentStorage) + hd.getMaxInstantOutflow(nextStorage));
        System.out.println(outflow);
        nextStorage = currentStorage + (inflow - outflow) * T / 100000000;

        outflow = 0.5 * (hd.getMaxInstantOutflow(currentStorage) + hd.getMaxInstantOutflow(nextStorage));
        System.out.println(outflow);
        nextStorage = currentStorage + (inflow - outflow) * T / 100000000;

        System.out.println();
        hd.getMaxPeriodAvgOutflow(13.33,9000);
        System.out.println(hd.getLevelByVolume(13.33));

        System.out.println();
    }
}
