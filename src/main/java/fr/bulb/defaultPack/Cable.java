package fr.bulb.defaultPack;

import fr.bulb.Component.Component;
import fr.bulb.Component.Coordinate;
import fr.bulb.Component.Input;
import fr.bulb.Component.Output;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cable extends Component {

    public Cable(Coordinate coord, int length) {
        super("Cable", "Cab-01", null, null, 0, 0, coord,
                (coord.getOrientation() == Coordinate.Orientation.UP || coord.getOrientation() == Coordinate.Orientation.DOWN)? 0 : length,
                (coord.getOrientation() == Coordinate.Orientation.RIGHT || coord.getOrientation() == Coordinate.Orientation.LEFT)? 0 : length);

        /*if(this.coord.getX() > this.end.getX()){ //Input plus a gauche que output
            this.coord.setOrientation(Coordinate.Orientation.LEFT);
        }else if(this.coord.getX() < this.end.getX()){
            this.coord.setOrientation(Coordinate.Orientation.RIGHT);
        }else if(this.coord.getY() > this.end.getY()){
            this.coord.setOrientation(Coordinate.Orientation.DOWN);
        }else{
            this.coord.setOrientation(Coordinate.Orientation.UP);
        }*/ //TODO a mettre dans la generation des cables

        this.setInput();
        this.setOutput();
    }

    public Cable(Coordinate start, int length, GraphicsContext ctx) {
        this(start, length);
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
        Coordinate inputCoords = null;
        System.out.println("{Orientation: "+this.coord.getOrientation()+"; width: "+this.width+"; height: "+this.height+"}");
        switch (this.coord.getOrientation()){
            case UP:
                inputCoords = new Coordinate(this.coord.getX(), this.hitbox.get("yMax"), this.coord.getOrientation());
                break;
            case DOWN:
                inputCoords = new Coordinate(this.coord.getX(), this.coord.getY(), this.coord.getOrientation());
                break;
            case LEFT:
                inputCoords = new Coordinate(this.coord.getX() + this.width, this.coord.getY(), this.coord.getOrientation());
                break;
            case RIGHT:
                inputCoords = new Coordinate(this.coord.getX(), this.coord.getY(), this.coord.getOrientation());
                break;
            default:
                throw new RuntimeException("INVALID ORIENTATION");
        }

        this.inputs.put("01", new Input(inputCoords));
    }

    @Override
    public void setOutput() {
        Coordinate outputCoords = null;
        switch (this.coord.getOrientation()){
            case UP:
                outputCoords = new Coordinate(this.coord.getX(), this.coord.getY(), this.coord.getOrientation());
                break;
            case DOWN:
                outputCoords = new Coordinate(this.coord.getX(), this.hitbox.get("yMax"), this.coord.getOrientation());
                break;
            case LEFT:
                outputCoords = new Coordinate(this.coord.getX(), this.coord.getY(), this.coord.getOrientation());
                break;
            case RIGHT:
                outputCoords = new Coordinate(this.coord.getX() + this.width, this.coord.getY(), this.coord.getOrientation());
                break;
            default:
                throw new RuntimeException("INVALID ORIENTATION");
        }

        this.outputs.put("01", new Output(this, outputCoords));
    }

    public Cable tick(GraphicsContext ctx) {
        this.getOutput("01").setValue(this.getInput("01").getSource().getValue());
        return this;
    }

    public Cable draw(GraphicsContext ctx) {
        switch (this.coord.getOrientation()){
            case UP:
            case DOWN:
                ctx.strokeLine(this.coord.getX(), this.hitbox.get("y") , this.coord.getX(), this.hitbox.get("yMax"));
                break;
            case RIGHT:
            case LEFT:
                ctx.strokeLine(this.hitbox.get("x"), this.coord.getY(), this.hitbox.get("xMax"), this.coord.getY());
                break;
        }

        ctx.setStroke(Color.RED);
        ctx.strokeOval(this.getInput("01").coordinate.getX()-5, this.getInput("01").coordinate.getY()-5, 10, 10);
        ctx.setStroke(Color.BLACK);
        ctx.strokeOval(this.getOutput("01").coordinate.getX()-5, this.getOutput("01").coordinate.getY()-5, 10, 10);

        return this;
    }

    @Override
    protected void setHitBox() throws RuntimeException {
        int x = 0;
        int y = 0;
        int yMax = 0;
        int xMax = 0;
        switch (this.coord.getOrientation()){
            case UP:
            case DOWN:
                x = this.coord.getX();
                y = this.coord.getY();
                xMax = this.coord.getX();
                yMax = this.coord.getY() + this.height;
                break;
            case LEFT:
            case RIGHT:
                x = this.coord.getX();
                y = this.coord.getY();
                xMax = this.coord.getX() + this.width;
                yMax = this.coord.getY();
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
}
