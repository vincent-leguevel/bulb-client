package fr.bulb.Component;

public abstract class InterractiveComponent extends Component implements InterractiveComponentInterface{
    public InterractiveComponent(String name, String ref, String category, String description, double ampMax, int resistance, Coordinate coordinate, int width, int height) {
        super(name, ref, category, description, ampMax, resistance, coordinate, width, height);
    }

    public boolean isClicked(Coordinate coordinate){
        return this.isInHitbox(coordinate);
    }
}
