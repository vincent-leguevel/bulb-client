package fr.bulb.Component;



import javax.imageio.ImageIO;
import java.util.Map;

abstract class Component implements ComponentInterface{
    // Component informations
    String name; //need to be define in the constructor
    String ref; //need to be define in the constructor
    String category; //need to be define in the constructor
    String description; //need to be define in the constructor

    // Component physic information
    Integer resistance;
    String state;
    private Map<String,Input> inputs;
    private Map<String,Output> outputs;

    // Component graphic management
    Coordinate coord;
    float width;
    float height;
    ImageIO icon; //need to be define in the constructor

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

    public Component setWidth(float width) {
        this.width = width;
        return this;
    }

    public String getState() {
        return state;
    }

    public Component setState(String state) {
        this.state = state;
        return this;
    }

    public float getHeight() {
        return height;
    }

    public Component setHeight(float height) {
        this.height = height;
        return this;
    }
}
