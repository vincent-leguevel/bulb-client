package fr.bulb.defaultPack;

import fr.bulb.Component.Component;
import fr.bulb.Component.Coordinate;
import fr.bulb.Component.Input;
import fr.bulb.Component.Output;
import javafx.scene.canvas.GraphicsContext;

public class Cable{
    private Coordinate start;
    private Coordinate end;
    private Input input;
    private Output output;

    public Cable(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public Cable(Coordinate start, Coordinate end, Input input, Output output) {
        this(start, end);
        this.input = input;
        this.output = output;
    }

    public Cable tick(GraphicsContext ctx) {
        return null;
    }

    public Cable draw(GraphicsContext ctx) {
        return null;
    }
}
