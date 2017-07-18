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
        // THERE IS NO INPUT
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
        return this;
    }

    public Component draw(GraphicsContext ctx) {
        //TODO
        return null;
    }
}
