package fr.bulb.Component;

public class Input {
    private Output source = null;
    private Coordinate coordinate;

    public Input(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public Output getSource() {
        return source;
    }

    public Input setSource(Output source){
        this.source = source;
        return this;
    }

    public Coordinate setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        return coordinate;
    }
}
