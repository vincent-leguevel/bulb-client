package fr.bulb.Component;

import java.util.Map;

abstract class Component implements ComponentInterface{
    // Component informations
    String name;
    String ref;
    String category;
    String description;

    // Component physic information
    Integer resistance;
    String state;
    private Map<String,Input> inputs;
    private Map<String,Output> outputs;

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

    // Component graphic management
    Coordinate coord = null;
}
