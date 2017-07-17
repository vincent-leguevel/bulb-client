package fr.bulb.Component;

import javafx.scene.canvas.GraphicsContext;

public interface ComponentInterface {
    public double getActivePower();
    public double getReactivePower();

    public void setInput();
    public void setOutput();

    public Component tick(GraphicsContext ctx);
    public Component initGui(GraphicsContext ctx);
    public Component draw(GraphicsContext ctx);
}
