package fr.bulb.Component;

public class Coordinate {
    private int x = 0;
    private int y = 0;
    public enum Orientation{
        UP,
        RIGHT,
        DOWN,
        LEFT
    }
    private Orientation orientation = null;

    public Coordinate(int x, int y, Orientation orientation){
        this(x, y);
        this.orientation = orientation;
    }

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "{ x: "+ this.x+", y: "+this.y+", orientation :"+ this.orientation+"}";
    }
}
