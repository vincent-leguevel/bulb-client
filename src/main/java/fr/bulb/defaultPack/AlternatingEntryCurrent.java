package fr.bulb.defaultPack;

import fr.bulb.Component.AlternatingCurrent;
import fr.bulb.Component.Coordinate;
import fr.bulb.Component.Electricity;
import fr.bulb.Component.EntryCurrent;
import javafx.scene.canvas.GraphicsContext;

public class AlternatingEntryCurrent extends EntryCurrent {

    public AlternatingEntryCurrent(Coordinate coordinate) {
        super(coordinate, new AlternatingCurrent(230, 50));
    }

    public AlternatingEntryCurrent(Coordinate coordinate, GraphicsContext ctx) {
        super(coordinate, new AlternatingCurrent(230, 50), ctx);
    }
}
