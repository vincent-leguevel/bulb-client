package fr.bulb.Component;

public abstract class Electricity {
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

    @Override
    public String toString() {
        return "{volt: "+ this.volt+"}";
    }
}
