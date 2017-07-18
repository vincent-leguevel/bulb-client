package fr.bulb.Component;

public class AlternatingCurrent extends Electricity{
    private int frequency = 0;

    public AlternatingCurrent(int volt, int frequency) {
        super(volt);
        this.frequency = frequency;
    }
}
