package fr.bulb.Component;

public interface ComponentInterface {
    public double getActivePower();
    public double getReactivePower();
    
    public Component tick();
    public Component initGui();
}
