package fr.bulb.defaultPack;

import fr.bulb.Component.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Button extends InterractiveComponent {

    public enum State{
        OPEN("OPEN"),
        CLOSE("CLOSE");

        String value;

        State(String value){
            this.value = value;
        }
    }

    public Button(Coordinate coordinate){
        super("Button", "But-01", "Interactif", "Interactive button", State.OPEN.value, 0, 0, coordinate, 100, 20);
        this.setInput();
        this.setOutput();
    }

    public Button(Coordinate coordinate, GraphicsContext ctx){
        this(coordinate);
        initGui(ctx);
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
        switch (this.coord.getOrientation()){
            case UP:
                inputCoords = new Coordinate(this.coord.getX() + this.height, this.coord.getY() + this.width, this.coord.getOrientation());
                break;
            case DOWN:
                inputCoords = new Coordinate(this.coord.getX(), this.coord.getY(), this.coord.getOrientation());
                break;
            case LEFT:
                inputCoords = new Coordinate(this.coord.getX() + this.width, this.coord.getY() + this.height, this.coord.getOrientation());
                break;
            case RIGHT:
                inputCoords = new Coordinate(this.coord.getX(), this.coord.getY() + this.height, this.coord.getOrientation());
                break;
            default:
                throw new RuntimeException("INVALID ORIENTATION");
        }

        this.inputs.put("01", new Input(inputCoords, this));
    }

    @Override
    public void setOutput() {
        Coordinate outputCoords = null;
        switch (this.coord.getOrientation()){
            case UP:
                outputCoords = new Coordinate(this.coord.getX() + this.height, this.coord.getY(), this.coord.getOrientation());
                break;
            case DOWN:
                outputCoords = new Coordinate(this.coord.getX(), this.coord.getY() + this.width, this.coord.getOrientation());
                break;
            case LEFT:
                outputCoords = new Coordinate(this.coord.getX(), this.coord.getY() + this.height, this.coord.getOrientation());
                break;
            case RIGHT:
                outputCoords = new Coordinate(this.coord.getX() + this.width, this.coord.getY() + this.height, this.coord.getOrientation());
                break;
            default:
                throw new RuntimeException("INVALID ORIENTATION");
        }

        this.outputs.put("01", new Output(this, outputCoords));
    }

    public Component tick() {
        if (this.state.equals(State.OPEN.value)){
            //when open transmit null current
            this.getOutput("01").setValue(null);
        }else{
            //when close transmit input current
            this.getOutput("01").setValue(this.getInput("01").getSource().getValue());
        }
        //draw(ctx);
        return this;
    }

    public Component draw(GraphicsContext ctx) {
//        ctx.strokeOval(this.coord.getX()-5, this.coord.getY()-5, 10, 10);
        this.clearGUI(ctx);
        switch (this.coord.getOrientation()){
            case RIGHT:
            case LEFT:
//                ctx.clearRect(this.coord.getX(), this.coord.getY()-1, this.width, this.height+2);
                ctx.strokeLine(this.coord.getX(),this.coord.getY() + this.height,this.coord.getX() + 30, this.coord.getY() + this.height);
                ctx.strokeLine(this.coord.getX() + 60,this.coord.getY() + this.height, this.coord.getX() + this.width, this.coord.getY() + this.height);
                if(this.coord.getOrientation() == Coordinate.Orientation.RIGHT){
                    ctx.strokeLine(this.coord.getX() + 30,this.coord.getY() + this.height, this.coord.getX() + 60, this.state.equals(State.OPEN.value)? this.coord.getY(): this.coord.getY()+this.height-2);
                }else{
                    ctx.strokeLine(this.coord.getX() + 60, this.coord.getY()+ this.height, this.coord.getX() + 30, this.state.equals(State.OPEN.value)? this.coord.getY(): this.coord.getY()+this.height-2);
                }
                break;
            case UP:
            case DOWN:
//                ctx.clearRect(this.coord.getX()-1, this.coord.getY(), this.height+2, this.width);
                boolean isUp = this.coord.getOrientation() == Coordinate.Orientation.UP;
                int x = isUp ? this.coord.getX() + this.height : this.coord.getX();
                int openValue = 0;
                if(this.state.equals(State.OPEN.value)){
                    openValue = isUp? x - this.height : x + this.height;
                }else{
                    openValue = isUp? x - 1 : x + 1;
                }
                ctx.strokeLine(x, this.coord.getY(), x, this.coord.getY() + 30);
                ctx.strokeLine(x, this.coord.getY() + 60, x, this.coord.getY() + this.width);
                ctx.strokeLine(x, this.coord.getY() + 30, openValue , this.coord.getY() + 60);
            break;
        }

        ctx.setStroke(Color.RED);
        ctx.strokeOval(this.getInput("01").coordinate.getX()-5, this.getInput("01").coordinate.getY()-5, 10, 10);
        ctx.setStroke(Color.BLACK);
        ctx.strokeOval(this.getOutput("01").coordinate.getX()-5, this.getOutput("01").coordinate.getY()-5, 10, 10);
        return this;
    }

    public void onClick(GraphicsContext ctx) {
        if (this.state.equals(State.OPEN.value)){
            //when closed
            this.state = State.CLOSE.value;
        }else{
            //when open
            this.state = State.OPEN.value;
        }

        draw(ctx);
    }
}
