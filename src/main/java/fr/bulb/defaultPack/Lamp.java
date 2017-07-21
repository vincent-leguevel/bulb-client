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

        //System.out.println(this.coord+" width: "+this.width+"; height: "+this.height+"; state:" + this.state);

        //ctx.strokeOval(this.coord.getX()-5, this.coord.getY()-5, 10, 10);

        int circleX = 0;
        int circleY = 0;

        switch (this.coord.getOrientation()){
            case RIGHT:
            case LEFT:
                int y = this.coord.getY() + this.height / 2 ;
                ctx.clearRect(this.coord.getX(), this.coord.getY(), this.width, this.height);
                //ctx.strokeRect(this.coord.getX(), this.coord.getY(), this.width, this.height);
                ctx.strokeLine(this.coord.getX(),y,this.coord.getX() + 25, y);
                ctx.strokeLine(this.coord.getX() + 75,y, this.coord.getX() + this.width, y);
                circleX = this.coord.getX() + 25;
                circleY = this.coord.getY();
                if(this.state.equals(State.POWERED.value)){
                    ctx.setFill(Color.YELLOW);
                    ctx.fillOval(circleX, circleY, 50, 50 );
                    ctx.setFill(Color.BLACK);
                }
//                ctx.strokeRect(this.hitbox.get("x"),this.hitbox.get("y"),this.width,this.height);
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
                int x  = this.coord.getX() + this.height / 2 ;
                ctx.clearRect(this.coord.getX(), this.coord.getY(), this.height, this.width);
                //ctx.strokeRect(this.coord.getX(), this.coord.getY(), this.height, this.width);
                ctx.strokeLine(x,this.coord.getY(),x, this.coord.getY() + 25);
                ctx.strokeLine(x,this.coord.getY() + 75, x, this.coord.getY() + this.width);
                circleX = this.coord.getX();
                circleY = this.coord.getY() + this.width / 4;
                if(this.state.equals(State.POWERED.value)){
                    ctx.setFill(Color.YELLOW);
                    ctx.fillOval(circleX, circleY, 50, 50 );
                    ctx.setFill(Color.BLACK);
                }
//                ctx.strokeRect(this.hitbox.get("x"),this.hitbox.get("y"),this.height,this.width);
                break;
        }

        ctx.strokeOval(circleX, circleY , 50, 50 );

        ctx.setStroke(Color.RED);
        ctx.strokeOval(this.getInput("01").coordinate.getX()-5, this.getInput("01").coordinate.getY()-5, 10, 10);
        ctx.setStroke(Color.BLACK);
        ctx.strokeOval(this.getOutput("01").coordinate.getX()-5, this.getOutput("01").coordinate.getY()-5, 10, 10);
        return this;
    }
}
