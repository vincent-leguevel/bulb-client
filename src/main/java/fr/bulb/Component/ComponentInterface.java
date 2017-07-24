package fr.bulb.Component;

import javafx.scene.canvas.GraphicsContext;

public interface ComponentInterface {
    public double getActivePower();
    public double getReactivePower();
    public double getCurrent();

    public void setInput();
    public void setOutput();

    public Component tick();
    public Component initGui(GraphicsContext ctx);
    public Component draw(GraphicsContext ctx);
}
