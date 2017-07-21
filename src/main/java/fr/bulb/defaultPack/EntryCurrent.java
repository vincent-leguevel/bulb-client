package fr.bulb.defaultPack;

import fr.bulb.Component.*;
import javafx.scene.canvas.GraphicsContext;

public class EntryCurrent extends Component {

    private Electricity electricity;

    public EntryCurrent(Coordinate coordinate, Electricity electricity ) {
        super("EntryCurrent", "Gen-01", "Passive", "Standard EntryCurrent", 0, 0, coordinate, 40, 30);
        this.electricity = electricity;

        this.setOutput();
        this.getOutput("01").setValue(electricity);
    }

    public EntryCurrent(Coordinate coordinate, Electricity electricity, GraphicsContext ctx){
        this(coordinate, electricity);
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
        // THERE IS NO INPUT
    }

    public Component tick(GraphicsContext ctx) {
        draw(ctx);
        if(this.getOutput("01").getValue() == null){
            this.getOutput("01").setValue(this.electricity);
        }
        return this;
    }

    public Component draw(GraphicsContext ctx) {
//        ctx.strokeOval(this.coord.getX()-5, this.coord.getY()-5, 10, 10);
        switch (this.coord.getOrientation()){
            case UP:
            case DOWN:
                int isUp = this.coord.getOrientation() == Coordinate.Orientation.UP ? 1 : 0;
                ctx.strokeLine(this.coord.getX(), this.coord.getY() + this.width * isUp, this.coord.getX() + this.height, this.coord.getY() + this.width * isUp);
                ctx.strokeLine(this.coord.getX(), this.coord.getY() + this.width * isUp, this.coord.getX() + this.height / 2, this.coord.getY() + this.width / 2 );
                ctx.strokeLine(this.coord.getX() + this.height, this.coord.getY() + this.width * isUp , this.coord.getX() + this.height / 2, this.coord.getY() + this.width / 2);
                ctx.strokeLine(this.coord.getX() + this.height / 2, this.coord.getY() + this.width / 2 , this.coord.getX() + this.height / 2, this.coord.getY() + (this.width * (1 - isUp)));
//                ctx.strokeRect(this.hitbox.get("x"),this.hitbox.get("y"),this.height,this.width);
                break;
            case RIGHT:
            case LEFT:
                int isLeft = this.coord.getOrientation() == Coordinate.Orientation.LEFT ? 1 : 0;
                ctx.strokeLine(this.coord.getX() + this.width * isLeft, this.coord.getY(), this.coord.getX()  + this.width * isLeft, this.coord.getY() + this.height);
                ctx.strokeLine(this.coord.getX() + this.width * isLeft, this.coord.getY(), this.coord.getX() + this.width / 2, this.coord.getY() + this.height / 2);
                ctx.strokeLine(this.coord.getX() + this.width * isLeft, this.coord.getY() + this.height, this.coord.getX() + this.width / 2, this.coord.getY() + this.height / 2);
                ctx.strokeLine(this.coord.getX() - this.width * (isLeft - 1), this.coord.getY() + this.height / 2, this.coord.getX() + this.width / 2, this.coord.getY() + this.height / 2);
//                ctx.strokeRect(this.hitbox.get("x"),this.hitbox.get("y"),this.width,this.height);
                break;
        }
//        ctx.strokeOval(this.getOutput("01").coordinate.getX()-5, this.getOutput("01").coordinate.getY()-5, 10, 10);
        return this;
    }
}
