package org.example.test;

import org.example.pojo.HD;
import org.example.pojo.Statistcs;

public class HDFloodControlTest {
    public static void main(String[] args) {
        HD hd = new HD();
        hd.setInflow(Statistcs.flood2);

        hd.floodControlCalculate();
        for (int i = 0; i < hd.periodNumber; i++) {
            System.out.print(hd.reservoirVolume[i]+"  ");
            System.out.print(hd.reservoirWaterLevel[i]+"  ");
            System.out.print(hd.outflow[i]+"  ");
            System.out.println(hd.inflow[i]+"  ");
        }
    }
}
