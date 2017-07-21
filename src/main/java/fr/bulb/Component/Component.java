package fr.bulb.Component;



import javafx.scene.canvas.GraphicsContext;

import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

public abstract class Component implements ComponentInterface{
    // Component informations
    protected String name;
    protected String ref;
    protected String category;
    protected String description;

    // Component physic information
    protected int resistance;
    protected double ampMax;
    protected String state;
    protected HashMap<String,Input> inputs = new HashMap<String, Input>();
    protected HashMap<String,Output> outputs = new HashMap<String, Output>();

    // Component graphic management
    protected HashMap<String, Integer> hitbox = new HashMap<String, Integer>();
    protected Coordinate coord;
    protected int width;
    protected int height;
    protected ImageIO icon; //need to be define in the constructor

    public Component(String name, String ref, String category, String description, double ampMax, int resistance, Coordinate coordinate, int width, int height){
        this.name = name;
        this.ref = ref;
        this.category = category;
        this.description = description;

        this.resistance = resistance;
        this.ampMax = ampMax;

        this.coord = coordinate;

        this.width = width;
        this.height = height;

        this.setHitBox();
    }

    public Map<String, Input> getInputs() {
        return inputs;
    }

    public Input getInput(String index){
        return this.inputs.get(index);
    };

    public Component setInput(String inputId, Input input){
        this.inputs.put(inputId, input);
        return this;
    };

    public Map<String, Output> getOutputs() {
        return outputs;
    }

    public Output getOutput(String index) {
        return this.outputs.get(index);
    }

    public Component setOutput(String outputId, Output output){
        this.outputs.put(outputId, output);
        return this;
    };

    public Coordinate getCoord() {
        return coord;
    }

    public Component setCoord(Coordinate coord) {
        this.coord = coord;
        return this;
    }

    public float getWidth() {
        return width;
    }

    public Component setWidth(int width) {
        this.width = width;
        return this;
    }

    public Component setHeight(int height) {
        this.height = height;
        return this;
    }

    public float getHeight() {
        return height;
    }

    public void setInput() {
        Coordinate inputCoords = null;
        switch (this.coord.getOrientation()){
            case UP:
                inputCoords = new Coordinate(this.coord.getX() + this.height / 2, this.coord.getY() + this.width, this.coord.getOrientation());
                break;
            case DOWN:
                inputCoords = new Coordinate(this.coord.getX() + this.height / 2, this.coord.getY(), this.coord.getOrientation());
                break;
            case LEFT:
                inputCoords = new Coordinate(this.coord.getX() + this.width , this.coord.getY() + this.height / 2, this.coord.getOrientation());
                break;
            case RIGHT:
                inputCoords = new Coordinate(this.coord.getX(), this.coord.getY() + this.height / 2, this.coord.getOrientation());
                break;
            default:
                throw new RuntimeException("INVALID ORIENTATION");
        }

        this.inputs.put("01", new Input(inputCoords));
    }

    public void setOutput() {
        Coordinate outputCoords = null;
        switch (this.coord.getOrientation()){
            case UP:
                outputCoords = new Coordinate(this.coord.getX() + this.height / 2, this.coord.getY(), this.coord.getOrientation());
                break;
            case DOWN:
                outputCoords = new Coordinate(this.coord.getX() + this.height / 2, this.coord.getY() + this.width, this.coord.getOrientation());
                break;
            case LEFT:
                outputCoords = new Coordinate(this.coord.getX(), this.coord.getY() + this.height / 2, this.coord.getOrientation());
                break;
            case RIGHT:
                outputCoords = new Coordinate(this.coord.getX() + this.width, this.coord.getY() + this.height / 2, this.coord.getOrientation());
                break;
            default:
                throw new RuntimeException("INVALID ORIENTATION");
        }

        this.outputs.put("01", new Output(this, outputCoords));
    }

    protected void setHitBox() throws RuntimeException{
        int x = 0;
        int y = 0;
        int yMax = 0;
        int xMax = 0;
        switch (this.coord.getOrientation()){
            case UP:
            case DOWN:
                x = this.coord.getX();
                y = this.coord.getY();
                xMax = this.coord.getX() + this.height;
                yMax = this.coord.getY() + this.width;
                break;
            case LEFT:
            case RIGHT:
                x = this.coord.getX();
                y = this.coord.getY();
                xMax = this.coord.getX() + this.width;
                yMax = this.coord.getY() + this.height;
                break;
            default:
                throw new Error("INVALID ORIENTATION");

        }
        System.out.println("{x: "+x+"; y: "+y+"; xMax: "+xMax+"; yMax: "+yMax+"}");
        this.hitbox.put("x", x);
        this.hitbox.put("y", y);
        this.hitbox.put("xMax", xMax);
        this.hitbox.put("yMax", yMax);
    }

    public boolean isInHitbox(Coordinate coordinate){
        return coordinate.getX() >= this.hitbox.get("x")
                && coordinate.getX() <= this.hitbox.get("xMax")
                && coordinate.getY() >= this.hitbox.get("y")
                && coordinate.getY() <= this.hitbox.get("yMax");
    }

    public Component initGui(GraphicsContext ctx) {
        this.draw(ctx);
        return this;
    };
}
