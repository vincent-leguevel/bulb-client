package fr.bulb.defaultPack;

import fr.bulb.Component.Component;
import fr.bulb.Component.Coordinate;
import fr.bulb.Component.Input;
import fr.bulb.Component.Output;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cable extends Component {
    private Coordinate start;
    private Coordinate end;

    public Cable(Coordinate start, Coordinate end) {
        super("Cable", "Cab-01", null, null, null,0, 0, start,0,0);

        this.start = start;
        this.end = end;
        this.setInput();
        this.setOutput();
    }

    public Cable(Coordinate start, Coordinate end, GraphicsContext ctx) {
        this(start, end);
        this.initGui(ctx);
    }

    public double getActivePower() {
        return 0;
    }

    public double getReactivePower() {
        return 0;
    }

    public double getCurrent() {
        return 0;
    }

    @Override
    public void setInput() {
        this.inputs.put("01", new Input(this.start, this));
    }

    @Override
    public void setOutput() {
        this.outputs.put("01", new Output(this, this.end));
    }

    public Cable tick() {
        this.getOutput("01").setValue(this.getInput("01").getSource().getValue());
        return this;
    }

    public Cable draw(GraphicsContext ctx) {
        ctx.strokeLine(this.start.getX(), this.start.getY(), this.end.getX(), this.end.getY());

//        ctx.setStroke(Color.RED);
//        ctx.strokeOval(this.getInput("01").coordinate.getX()-5, this.getInput("01").coordinate.getY()-5, 10, 10);
//        ctx.setStroke(Color.BLACK);
//        ctx.strokeOval(this.getOutput("01").coordinate.getX()-5, this.getOutput("01").coordinate.getY()-5, 10, 10);

        return this;
    }

    //NO HITBOX
    @Override
    protected void setHitBox() throws RuntimeException {
        int x = 0;
        int y = 0;
        int yMax = 0;
        int xMax = 0;
        this.hitbox.put("x", x);
        this.hitbox.put("y", y);
        this.hitbox.put("xMax", xMax);
        this.hitbox.put("yMax", yMax);
    }

    @Override
    public void clearGUI(GraphicsContext ctx){
        //DON'T WANT TO ERASE PART OF THE CIRCUIT
    }
}
