package fr.bulb.Component;

public class Output{
    private Input destination;
    private Component originComponent;
    private Current value = null;

    public Output(Input destination, Component originComponent, Current current){
        this.destination = destination;
        this.originComponent = originComponent;
        this.value = current;
    }

    public Current getValue() {
        return value;
    }

    public void setValue(Current value){
        this.value = value;
    }
}
