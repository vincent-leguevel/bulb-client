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
    protected Integer resistance;
    protected String state;
    protected HashMap<String,Input> inputs = new HashMap<String, Input>();
    protected HashMap<String,Output> outputs = new HashMap<String, Output>();

    // Component graphic management
    protected HashMap<String, Integer> hitbox = new HashMap<String, Integer>();
    protected Coordinate coord;
    protected int width;
    protected int height;
    protected ImageIO icon; //need to be define in the constructor

    public Component(String name, String ref, String category, String description, Integer resistance, Coordinate coordinate, int width, int height){
        this.name = name;
        this.ref = ref;
        this.category = category;
        this.description = description;

        this.resistance = resistance;

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

    public String getState() {
        return state;
    }

    public Component setState(String state) {
        this.state = state;
        return this;
    }

    private void setHitBox() throws RuntimeException{
        int x = 0;
        int y = 0;
        int yMax = 0;
        int xMax = 0;
        switch (this.coord.getOrientation()){
            case UP:
            case DOWN:
                x = this.coord.getX();
                y = this.coord.getY() - this.width;
                xMax = this.coord.getX() + this.height;
                yMax = this.coord.getY();
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

    public boolean isClicked(Coordinate coordinate){
        return this.isInHitbox(coordinate);
    }

    public Component initGui(GraphicsContext ctx) {
        this.draw(ctx);
        return this;
    };
}
