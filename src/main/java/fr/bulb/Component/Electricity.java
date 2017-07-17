package fr.bulb.Component;

abstract class Electricity {
    protected Integer volt = null;


    public Electricity(int volt){
        this.volt = volt;
    }

    public int getVolt(){
        return this.volt;
    }

    public Electricity setVolt(int value){
        this.volt = value;
        return this;
    }
}
