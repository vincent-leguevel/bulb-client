package fr.bulb;

import fr.bulb.Component.*;
import fr.bulb.Component.EntryCurrent;
import fr.bulb.defaultPack.Cable;
import fr.bulb.defaultPack.ExitCurrent;
import javafx.scene.canvas.GraphicsContext;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Project {

    public enum State{
        ANIMATION,
        EDITION
    }

    private String name = "Undefined";
    public ArrayList<Component> availableComponent = new ArrayList<Component>();
    public HashMap<String, ArrayList<Component>> posedComponent = new HashMap<String, ArrayList<Component>>();
    public ArrayList<Component> circuitEntries = new ArrayList<Component>();
    public ArrayList<Cable> cables = new ArrayList<Cable>();
    public Output activeOutput = null;
    public Component toPreview = null;
    private ArrayList<InterractiveComponent> posedInteractiveComponent = new ArrayList<InterractiveComponent>();
    private Class selectComponent;
    private String path = null;
    private GraphicsContext ctx;
    private State state = State.EDITION;
    private Coordinate.Orientation orientation = Coordinate.Orientation.RIGHT;
    private Timer timerAnimation = new Timer();

    public Project(GraphicsContext ctx) {
        this.ctx = ctx;
        this.posedComponent.put(Cable.class.getName(), new ArrayList<Component>());
    }

    public Project(String name, String path, GraphicsContext ctx) {
        this(ctx);
        this.name = name;
        this.path = path;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Coordinate.Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Coordinate.Orientation orientation) {
        this.orientation = orientation;
    }

    public void setSelectComponent(Class selectComponent) {
        this.selectComponent = selectComponent;
    }

    public void launchAnimation(){
        if(this.state == State.EDITION) {
            if (this.circuitIsValid()) {
                this.setState(Project.State.ANIMATION);
                this.timerAnimation.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        drawCircuit();
                        //tickCircuit(); //TODO next step
                    }
                }, 0, 50);
            }// else INVALID CIRCUIT
        }
    }

    private void drawCircuit(){
        for (ArrayList<Component> components :
                posedComponent.values()) {
            if(components.getClass().getName() == Cable.class.getName()){
                break;
            }
            for (Component component :
                    components) {
                component.clearGUI(ctx);
                component.draw(ctx);
            }
        }
        for (Component cable :
                posedComponent.get(Cable.class.getName())) {
            cable.draw(ctx);
        }
    }

    public void stopAnimation(){
        if(this.state == State.ANIMATION) {
            this.setState(State.EDITION);
            this.timerAnimation.cancel();
            this.timerAnimation = new Timer();
            this.resetComponents();
        }
    }

    public Component isInComponent(Coordinate coordinate){
        for(Map.Entry<String, ArrayList<Component>> entry :
                this.posedComponent.entrySet()) {
            String key = entry.getKey();
            ArrayList<Component> value = entry.getValue();

            for(Component component : value){
                if(component.isInHitbox(coordinate)){
                    return component;
                }
            }
        }
        return null;
    }

    public void clickOnComponent(Coordinate coord){
        for(InterractiveComponent component :
                posedInteractiveComponent) {
            if (component.isClicked(coord)) {
                component.onClick(ctx);
            }
        }
    }

    public void resetComponents(){
        for (ArrayList<Component> components:
                this.posedComponent.values()){
            for (Component component:
                    components){
                component.resetState(this.ctx);
            }
        }
    }

    public void addComponent(Coordinate coord){
        if(this.selectComponent != null){
            Component classe = null;
            try {
                Class[] args = {Coordinate.class, GraphicsContext.class};
                Component component = (Component)this.selectComponent.getDeclaredConstructor(args).newInstance(coord, this.ctx);
                this.pushComponent(component);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }else{
            throw new Error("NO COMPONENT SELECT");
        }
    }

    public void addComponent(Coordinate coord, String componentClass){
        try {
            Class classe = Class.forName(componentClass);
            Class[] args = {Coordinate.class, GraphicsContext.class};
            Component component = (Component)classe.getDeclaredConstructor(args).newInstance(coord, this.ctx);
            this.pushComponent(component);
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    private void pushComponent(Component component){
        if(this.isInteractif(component.getClass())){
            this.posedInteractiveComponent.add((InterractiveComponent)component);
        }
        if(this.isBeginning(component.getClass())){
            this.circuitEntries.add(component);
        }
        if(this.posedComponent.get(component.getClass().getName()) == null){
            this.posedComponent.put(component.getClass().getName(), new ArrayList<Component>());
        }
        this.posedComponent.get(component.getClass().getName()).add(component);
    }

    public void addCable(Output source, Input dest){
        Component cable = new Cable(source.coordinate, dest.coordinate, this.ctx);
        Component componentSource = source.originComponent;
        Component componentDest = dest.getComponent();
        source.nextComponent = cable;
        cable.getOutput("01").nextComponent = componentDest;
        cable.getInput("01").setSource(componentSource.isInOutput(source.coordinate));
        componentDest.isInInput(dest.coordinate).setSource(cable.getOutput("01"));
        this.posedComponent.get(Cable.class.getName()).add(cable);
    }

    private boolean isInteractif(Class classe){
        return InterractiveComponentInterface.class.isAssignableFrom(classe);
    }

    private boolean isBeginning(Class classe){
        return EntryCurrent.class.isAssignableFrom(classe);
    }

    public boolean circuitIsValid(Component source){
        for(Output output:
                source.getOutputs().values()){
            if (output.nextComponent == null){
                return false;
            }
            if(ExitCurrent.class.isAssignableFrom(output.nextComponent.getClass())){
                return true;
            }
            return circuitIsValid(output.nextComponent);
        }

        return false;
    }

    public boolean circuitIsValid(){
        if(this.circuitEntries.size() == 0){
            return false;
        }
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        for(Component entry:
                this.circuitEntries){
            res.add(this.circuitIsValid(entry));
        }

        for (boolean result:
                res){
            if(!result){
                return false;
            }
        }

        return true;
    }

    public void previewSelectComponent(Coordinate coordinate){
        if(this.toPreview != null){
            this.toPreview.clearGUI(this.ctx);
        }
        Class[] args = {Coordinate.class, GraphicsContext.class};
        try {
            this.toPreview = (Component)this.selectComponent.getDeclaredConstructor(args).newInstance(new Coordinate(coordinate.getX(), coordinate.getY(), this.orientation), this.ctx);
        } catch (Exception e) {
            System.out.println(e);
        }

        this.drawCircuit();
    }

    public void erasePreviewComponent(){
        this.toPreview.clearGUI(this.ctx);
        this.drawCircuit();
    }
}
