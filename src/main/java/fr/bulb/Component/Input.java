package fr.bulb.Component;

public class Input {
    private Output source = null;
    private Component component;
    public Coordinate coordinate;

    public Input(Coordinate coordinate, Component componentSSSS){
        this.coordinate = coordinate;
        this.component = component;
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
