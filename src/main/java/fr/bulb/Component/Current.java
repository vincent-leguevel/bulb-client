package fr.bulb.Component;

public class Current {
    private Integer ampere = null;
    private Integer volt = null;


    public Current(int ampere, int volt){
        this.ampere = ampere;
        this.volt = volt;
    }

    public int getAmpere(){
        return this.ampere;
    }

    public Current setAmpere(int value){
        this.ampere = value;
        return this;
    }

    public int getVolt(){
        return this.volt;
    }

    public Current setVolt(int value){
        this.volt = value;
        return this;
    }

    public int getPower(){
        return this.ampere * this.volt;
    }
}
