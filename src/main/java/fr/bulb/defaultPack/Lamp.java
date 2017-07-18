package fr.bulb.defaultPack;

import fr.bulb.Component.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Lamp extends Component {

    public enum State{
        POWERED("POWERED"),
        UNPOWERED("UNPOWERED");

        String value;

        State(String value){
            this.value = value;
        }
    }

    public Lamp(Coordinate coordinate){
        super("Lamp", "Lam-01", "Passive", "Standard lamp", 0.04, 0, coordinate, 100, 50);

        this.state = Button.State.OPEN.value;

        this.setInput();
        this.setOutput();
    }

    public Lamp(Coordinate coordinate, GraphicsContext ctx){
        this(coordinate);
        initGui(ctx);
    }

    public double getActivePower() {
        return 0;
    }

    public double getReactivePower() {
        return 0;
    }

    public void setInput() {
        Coordinate inputCoords = null;
        switch (this.coord.getOrientation()){
            case UP:
                inputCoords = new Coordinate(this.coord.getX() + this.height / 2, this.coord.getY(), this.coord.getOrientation());
                break;
            case DOWN:
                inputCoords = new Coordinate(this.coord.getX() + this.height / 2, this.coord.getY() - this.width, this.coord.getOrientation());
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
                outputCoords = new Coordinate(this.coord.getX() + this.height / 2, this.coord.getY() - this.width, this.coord.getOrientation());
                break;
            case DOWN:
                outputCoords = new Coordinate(this.coord.getX() + this.height / 2, this.coord.getY(), this.coord.getOrientation());
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

    public Component tick(GraphicsContext ctx) {
        /*System.out.println("tick");
        if (this.state.equals(Button.State.OPEN.value)){
            //when open transmit null current
            this.getOutput("01").setValue(new Current(0,0));
        }else{
            //when close transmit input current
            this.getOutput("01").setValue(this.getInput("01").getSource().getValue());
        }
        draw(ctx);*/
        return this;
    }

    public Component draw(GraphicsContext ctx) {

        System.out.println(this.coord+" width: "+this.width+"; height: "+this.height);
        switch (this.coord.getOrientation()){
            case RIGHT:
            case LEFT:
                int y = this.coord.getY() + this.height / 2 ;
                ctx.clearRect(this.coord.getX(), this.coord.getY()-1, this.width, this.height+2);
                ctx.strokeRect(this.coord.getX(), this.coord.getY(), this.coord.getX() + this.width, this.coord.getY() + this.height);
                ctx.strokeLine(this.coord.getX(),y,this.coord.getX() + 30, y);
                ctx.strokeLine(this.coord.getX() + 70,y, this.coord.getX() + this.width, y);
                if(this.state.equals(State.POWERED.value)){
                    ctx.setFill(Color.YELLOW);
                    ctx.fillOval(this.coord.getX() + 30, this.coord.getY() + this.height, 40, 40 );
                    ctx.setFill(Color.BLACK);
                }
                ctx.strokeOval(this.coord.getX() + 30, this.coord.getY() - this.height , 40, 40 );
                break;
            case UP:
            case DOWN:
                int x = this.coord.getX() + this.height / 2;
                ctx.clearRect(this.coord.getX()-1, this.coord.getY()- this.width, this.height+2, this.width);
                ctx.strokeLine(this.coord.getX() + this.height / 2, this.coord.getY(), this.coord.getX() + this.height / 2, this.coord.getY() - 30);
                ctx.strokeLine(x, this.coord.getY() - 70, x, this.coord.getY() - this.width);
                break;
        }
        return this;
    }
}
