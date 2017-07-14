package fr.bulb.Component;

import javafx.scene.canvas.Canvas;

public interface ComponentInterface {
    public Component tick();
    public Component initGui(Canvas context);
}
