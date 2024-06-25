package org.example.test;

import org.example.pojo.FloodControl;
import org.example.pojo.Reservoir;
import org.example.pojo.Statistcs;
import org.example.pojo.writeExcel;

import java.util.ArrayList;
import java.util.List;

public class FloodControlTest {
    public static void main(String[] args) {
        FloodControl floodControl = new FloodControl();
        floodControl.setInflow(Statistcs.flood1);
        floodControl.floodControlCalculate();
        List<Reservoir> reservoirs = new ArrayList<>();
        reservoirs.add(floodControl.hd);
        reservoirs.add(floodControl.dhq);
        writeExcel.writeExcel(reservoirs);

    }

}
