package fr.bulb.constants;

/**
 * Created by vince on 12/07/2017.
 */
public enum Tools {

    MOVE ("Mouvement"),
    SELECT ("Selection");

    private String name = "";

    Tools(String name){
        this.name = name;
    }
    public String toString(){
        return name;
    }
}
