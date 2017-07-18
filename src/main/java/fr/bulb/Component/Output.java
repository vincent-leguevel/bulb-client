package fr.bulb.Component;

public class Output{
    private Input destination;
    public Coordinate coordinate;
    private Component originComponent;
    private Electricity value = null;

    public Output(Input destination, Component originComponent, Coordinate coordinate, Electricity electricity){
        this(originComponent, coordinate);
        this.destination = destination;
        this.value = electricity;
    }

    public Output(Component originComponent, Coordinate coordinate){
        this.originComponent = originComponent;
        this.coordinate = coordinate;
    }

    public Electricity getValue() {
        return value;
    }

    public void setValue(Electricity value){
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
