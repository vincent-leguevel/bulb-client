package fr.bulb;

public class Current {
    private int ampere = 0;
    private int volt = 0;


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

    public int getWatt(){
        return this.ampere * this.volt;
    }
}
