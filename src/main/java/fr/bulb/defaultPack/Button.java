package fr.bulb.defaultPack;

import fr.bulb.Component.*;
import javafx.scene.canvas.Canvas;

public class Button extends Component implements InterractiveComponent {
    public Component tick() {
        /*
        WHAT TO DO WHEN THE ANIMATION GOES
         */
        return this;
    }

    public Component initGui(Canvas context) {
        /*
        WHAT TO DO TO RENDER THIS COMPONENT
         */
        return this;
    }

    public void onClick() {
        /*
        WHAT TO DO WHEN IT'S CLICKED ON
         */
    }
}
