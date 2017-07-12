package fr.bulb.Component;

public class Input {
    private Output source;
    private Coordinate coordinate;

    public Input(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public Output getSource() {
        return source;
    }
}
