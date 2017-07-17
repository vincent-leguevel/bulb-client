package fr.bulb.Component;

import javafx.scene.canvas.GraphicsContext;

public interface InterractiveComponent extends ComponentInterface {
    public void onClick(GraphicsContext ctx);
}
