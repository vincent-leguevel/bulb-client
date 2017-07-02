package fr.bulb;

import java.util.List;

public interface Component {
    public String name = null;
    public String category = null;
    public int resistance = 0;
    public List<Input> input = null;
    public List<Output> output = null;

    public Input setInput();
    public Input getInputs();

    public Output setOutput();
    public Output getOutput();

    public void tick();

}
