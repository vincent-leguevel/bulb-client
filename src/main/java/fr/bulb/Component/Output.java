package fr.bulb.Component;

public class Output{
    private Input destination;
    public Coordinate coordinate;
    private Component originComponent;
    private Current value = null;

    public Output(Input destination, Component originComponent, Coordinate coordinate, Current current){
        this(originComponent, coordinate);
        this.destination = destination;
        this.value = current;
    }

    public Output(Component originComponent, Coordinate coordinate){
        this.originComponent = originComponent;
        this.coordinate = coordinate;
    }

    public Current getValue() {
        return value;
    }

    public void setValue(Current value){
        this.value = value;
    }

    public Coordinate setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        return coordinate;
    }

    @Override
    public String toString() {
        return "Coordinate: "+this.coordinate+", OrigineComponent: "+this.originComponent.name;
    }
}
