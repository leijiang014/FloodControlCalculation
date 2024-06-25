package org.example.pojo;

public class FloodControl {
    public HD hd = new HD();
    public DHQ dhq = new DHQ();
    public void setInflow(double[] inflow){
        hd.setInflow(inflow);

    }
    public void floodControlCalculate(){
        hd.floodControlCalculate();
        dhq.setInflow(hd.outflow);
        dhq.floodControlCalculate();
    }
}
