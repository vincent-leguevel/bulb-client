package fr.bulb.Component;

public class Coordinate {
    private float x = 0;
    private float y = 0;

    public Coordinate(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{ x: "+ this.x+", y: "+this.y+"}";
    }
}
