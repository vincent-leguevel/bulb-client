package fr.bulb.defaultPack;


import fr.bulb.Component.Component;
import fr.bulb.Component.Coordinate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExitCurrent extends Component{
    public ExitCurrent(Coordinate coordinate) {
        super("ExitCurrent", "Gro-01", "Passive", "Standard ExitCurrent", null,0, 0, coordinate, 40, 30);

        this.setInput();
    }

    public ExitCurrent(Coordinate coordinate, GraphicsContext ctx){
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
    public void setOutput() {
        // THERE IS NO INPUT
    }

    public Component tick() {
        return this;
    }

    public Component draw(GraphicsContext ctx) {
        switch (this.coord.getOrientation()){
            case UP:
            case DOWN:
                int isUp = this.coord.getOrientation() == Coordinate.Orientation.UP ? 0 : 1;
                ctx.strokeLine(this.coord.getX(), this.coord.getY() + this.width * isUp, this.coord.getX() + this.height, this.coord.getY() + this.width * isUp);
                ctx.strokeLine(this.coord.getX(), this.coord.getY() + this.width * isUp, this.coord.getX() + this.height / 2, this.coord.getY() + this.width / 2);
                ctx.strokeLine(this.coord.getX() + this.height, this.coord.getY() + this.width * isUp, this.coord.getX() + this.height / 2, this.coord.getY() + this.width / 2);
                ctx.strokeLine(this.coord.getX() + this.height / 2, this.coord.getY() + this.width / 2, this.coord.getX() + this.height / 2, this.coord.getY() + this.width * (1 - isUp));
                break;
            case RIGHT:
            case LEFT:
                int isLeft = this.coord.getOrientation() == Coordinate.Orientation.LEFT ? 0 : 1;
                ctx.strokeLine(this.coord.getX() + this.width * isLeft, this.coord.getY(), this.coord.getX()  + this.width * isLeft, this.coord.getY() + this.height);
                ctx.strokeLine(this.coord.getX() + this.width * isLeft, this.coord.getY(), this.coord.getX() + this.width / 2, this.coord.getY() + this.height / 2);
                ctx.strokeLine(this.coord.getX() + this.width * isLeft, this.coord.getY() + this.height, this.coord.getX() + this.width / 2, this.coord.getY() + this.height / 2);
                ctx.strokeLine(this.coord.getX() - this.width * (isLeft - 1), this.coord.getY() + this.height / 2, this.coord.getX() + this.width / 2, this.coord.getY() + this.height / 2);
                break;
        }
        ctx.setStroke(Color.RED);
        ctx.strokeOval(this.getInput("01").coordinate.getX()-5, this.getInput("01").coordinate.getY()-5, 10, 10);
        ctx.setStroke(Color.BLACK);
        return null;
    }
}
