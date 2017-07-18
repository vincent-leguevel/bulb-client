package fr.bulb.defaultPack;

import fr.bulb.Component.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Lamp extends Component {

    public enum State{
        POWERED("POWERED"),
        UNPOWERED("UNPOWERED");

        public String value;

        State(String value) {
            this.value = value;
        }
    }

    public Lamp(Coordinate coordinate){
        super("Lamp", "Lam-01", "Passive", "Standard lamp", 0.04, 0, coordinate, 100, 50);

        this.state = State.UNPOWERED.value;

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

    public double getCurrent() {
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
        System.out.println("tick");
        if(this.getInput("01").getSource().getValue() != null ){
            this.state = State.POWERED.value;
        }else{
            this.state = State.UNPOWERED.value;
        }
        draw(ctx);
        return this;
    }

    public Component draw(GraphicsContext ctx) {

        System.out.println(this.coord+" width: "+this.width+"; height: "+this.height+"; state:" + this.state);

        int circleX = 0;
        int circleY = 0;

        switch (this.coord.getOrientation()){
            case RIGHT:
            case LEFT:
                int y = this.coord.getY() + this.height / 2 ;
                ctx.clearRect(this.coord.getX(), this.coord.getY()-1, this.width, this.height+2);
                ctx.strokeRect(this.coord.getX(), this.coord.getY(), this.width, this.height);
                ctx.strokeLine(this.coord.getX(),y,this.coord.getX() + 25, y);
                ctx.strokeLine(this.coord.getX() + 75,y, this.coord.getX() + this.width, y);
                circleX = this.coord.getX() + 25;
                circleY = this.coord.getY();
                if(this.state.equals(State.POWERED.value)){
                    ctx.setFill(Color.YELLOW);
                    ctx.fillOval(circleX, circleY, 50, 50 );
                    ctx.setFill(Color.BLACK);
                }
                /*//first diagonal TODO if I have the MOJO
                int circleCenterX = circleX + 25;
                int circleCenterY = circleX + 25;
                //origin
                double x10 = circleCenterX + 25 * Math.cos((3*Math.PI)/4);
                double y10 = circleCenterY + 25 * Math.sin((3*Math.PI)/4);

                //destination
                double x11 = circleCenterX + 25 * Math.cos((-Math.PI)/4);
                double y11 = circleCenterY + 25 * Math.sin((-Math.PI)/4);

                ctx.strokeLine(x10, y10, x11, y11);*/
                break;
            case UP:
            case DOWN:
                int x = this.coord.getX() + this.height / 2;
                ctx.clearRect(this.coord.getX()-1, this.coord.getY()- this.width, this.height+2, this.width);
                ctx.strokeLine(this.coord.getX() + this.height / 2, this.coord.getY(), this.coord.getX() + this.height / 2, this.coord.getY() - 30);
                ctx.strokeLine(x, this.coord.getY() - 70, x, this.coord.getY() - this.width);
                break;
        }

        ctx.strokeOval(circleX, circleY , 50, 50 );
        return this;
    }
}
