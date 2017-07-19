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

    @Override
    public void setInput() {
        // THERE IS NO INPUT
    }

    public Component tick(GraphicsContext ctx) {
        return this;
    }

    public Component draw(GraphicsContext ctx) {
        switch (this.coord.getOrientation()){
            case UP:
                break;
            case RIGHT:
                break;
            case DOWN:
                break;
            case LEFT:
                break;
        }
        return null;
    }
}
