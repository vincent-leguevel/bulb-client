package fr.bulb;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.bulb.Component.*;
import fr.bulb.Component.EntryCurrent;
import fr.bulb.defaultPack.*;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Project {

    public enum State{
        ANIMATION,
        EDITION
    }

    private String name = "Undefined";
    private String projectFile = null;
    public HashMap<String, String> availableComponent = new HashMap<String, String>();
    public HashMap<String, ArrayList<Component>> posedComponent = new HashMap<String, ArrayList<Component>>();
    public ArrayList<Component> circuitEntries = new ArrayList<Component>();
    public ArrayList<Cable> cables = new ArrayList<Cable>();
    public Output activeOutput = null;
    public Component toPreview = null;
    private ArrayList<InterractiveComponent> posedInteractiveComponent = new ArrayList<InterractiveComponent>();
    private Class selectComponent;
    private GraphicsContext ctx;
    private State state = State.EDITION;
    private Coordinate.Orientation orientation = Coordinate.Orientation.RIGHT;
    private Timer timerAnimation = new Timer();

    public Project(GraphicsContext ctx) {
        this.ctx = ctx;
        this.availableComponent.put("Button", Button.class.getName());
        this.availableComponent.put("Lamp", Lamp.class.getName());
        this.availableComponent.put("AlternatingEntryCurrent", AlternatingEntryCurrent.class.getName());
        this.availableComponent.put("ExitCurrent", ExitCurrent.class.getName());
        this.posedComponent.put(Cable.class.getName(), new ArrayList<Component>());
    }

    public Project(String name, String projectFile, GraphicsContext ctx) {
        this(ctx);
        this.name = name;
        this.projectFile = projectFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectFile() {
        return projectFile;
    }

    public void setProjectFile(String projectFile) {
        this.projectFile = projectFile;
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

    public void setSelectComponent(String selectComponent) {
        try {
            this.selectComponent = Class.forName(this.availableComponent.get(selectComponent));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void launchAnimation(){
        if(this.state == State.EDITION) {
            if (this.circuitIsValid()) {
                this.setState(Project.State.ANIMATION);
                this.timerAnimation.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        drawCircuit();
                        tickCircuit();
                    }
                }, 0, 50);
            } else {
                throw new Error("INVALID CIRCUIT: CIRCUIT NOT CLOSE");
            }
        }
    }
    
    private void tickCircuit(){
        for (Component entry :
                this.circuitEntries) {
            this.tickCircuit(entry);
        }
    }

    private void tickCircuit(Component source){
        source.tick();
        for (Output output :
                source.getOutputs().values()) {
            if (output.nextComponent != null){
                tickCircuit(output.nextComponent);
            }
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

    public void purgeAnimation(){
        this.timerAnimation.cancel();
        this.timerAnimation.purge();
    }

    //Assertion
    public Component isInComponent(Coordinate coordinate){
        for(Map.Entry<String, ArrayList<Component>> entry :
                this.posedComponent.entrySet()) {
            String key = entry.getKey();
            ArrayList<Component> value = entry.getValue();

            if(key.equals(Cable.class.getName())){
                continue;
            }
            for(Component component : value){
                if(component.isInHitbox(coordinate)){
                    return component;
                }
            }
        }
        return null;
    }

    private boolean isInteractif(Class classe){
        return InterractiveComponentInterface.class.isAssignableFrom(classe);
    }

    private boolean isBeginning(Class classe){
        return EntryCurrent.class.isAssignableFrom(classe);
    }

    private boolean circuitIsValid(Component source){
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

    private boolean circuitIsValid(){
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

    public void clickOnComponent(Coordinate coord){
        for(InterractiveComponent component :
                posedInteractiveComponent) {
            if (component.isClicked(coord)) {
                component.onClick(ctx);
            }
        }
    }

    //Component Gestion
    private void resetComponents(){
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
        Component componentDest = dest.getComponent();
        source.nextComponent = cable;
        cable.getOutput("01").nextComponent = componentDest;
        cable.getInput("01").setSource(source);
        componentDest.isInInput(dest.coordinate).setSource(cable.getOutput("01"));
        this.posedComponent.get(Cable.class.getName()).add(cable);
    }

    public void addCable(Coordinate source, Coordinate dest){
        Component sourceComponent = this.isInComponent(source);
        Output sourceOutput = sourceComponent.isInOutput(source);
        Component destComponent = this.isInComponent(dest);
        Input destInput = destComponent.isInInput(dest);
        Component cable = new Cable(source, dest, this.ctx);
        sourceOutput.nextComponent = cable;
        cable.getOutput("01").nextComponent = destComponent;
        cable.getInput("01").setSource(sourceOutput);
        destInput.setSource(cable.getOutput("01"));
        this.posedComponent.get(Cable.class.getName()).add(cable);
    }

    public void removeComponent(Coordinate coord){
        Component hasBeenClicked = this.isInComponent(coord);
        if(hasBeenClicked != null){
            for (Input input :
                    hasBeenClicked.getInputs().values()) {
                Output currentComponent = input.getSource();
                if(input.getSource() != null) {
                    if (currentComponent.originComponent.getClass().getName().equals(Cable.class.getName())) { //if my current Component is a cable then
                        Component cable = currentComponent.originComponent; //I store the reference
                        currentComponent = currentComponent.originComponent.getInput("01").getSource(); //set currentComponent to the cable's source Component
                        this.posedComponent.get(cable.getClass().getName()).remove(cable); //now i can remove my cable
                    }
                    currentComponent.nextComponent = null; //and finally set the nextComponent of my currentComponent to null
                }else{
                    break;
                }
            }

            for (Output output :
                    hasBeenClicked.getOutputs().values()) { //same with the outputs
                if (output.nextComponent != null) {
                    Component currentComponent = output.nextComponent;

                    Coordinate sourceCoordinate = output.coordinate;
                    if (currentComponent.getClass().getName().equals(Cable.class.getName())) { //if my current Component is a cable then
                        Component cable = currentComponent; //I store the reference
                        currentComponent = currentComponent.getOutput("01").nextComponent; //set currentComponent to the cable's destination Input
                        sourceCoordinate = cable.getOutput("01").coordinate;
                        this.posedComponent.get(cable.getClass().getName()).remove(cable); //now i can remove my cable
                    }
                    currentComponent.isInInput(sourceCoordinate).setSource(null);
                }else{
                    break;
                }
            }
            hasBeenClicked.clearGUI(this.ctx);
            this.posedComponent.get(hasBeenClicked.getClass().getName()).remove(hasBeenClicked);
            ctx.clearRect(0,0, ctx.getCanvas().getWidth(), ctx.getCanvas().getHeight()); //delete cable artifact
            this.drawCircuit();
        }
    }

    //GUI
    private void drawCircuit(){
        for (ArrayList<Component> components :
                posedComponent.values()) {
            if(components.getClass().getName().equals(Cable.class.getName())){
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

    public void previewSelectComponent(Coordinate coordinate){
        if(this.toPreview != null) {
            this.erasePreviewComponent();
        }
        if(this.selectComponent != null) {
            Class[] args = {Coordinate.class, GraphicsContext.class};
            try {
                this.toPreview = (Component) this.selectComponent.getDeclaredConstructor(args).newInstance(new Coordinate(coordinate.getX(), coordinate.getY(), this.orientation), this.ctx);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        this.drawCircuit();
    }

    public void erasePreviewComponent(){
        if(this.toPreview != null) {
            this.toPreview.clearGUI(this.ctx);
        }
        this.drawCircuit();
    }


    //LOAD & SAVE project
    public void save() throws ClassNotFoundException, IOException {
        System.out.println("save");
        HashMap<String, ArrayList> toSave = new HashMap<>();

        for (Map.Entry<String, ArrayList<Component>> components :
                this.posedComponent.entrySet()) {
            if(Cable.class.isAssignableFrom(Class.forName(components.getKey()))){
                toSave.put(components.getKey(), new ArrayList<HashMap<String, Coordinate>>());
                for (Component cable :
                        components.getValue()) {
                    HashMap<String, Coordinate> current = new HashMap<>();
                    current.put("in", cable.getInput("01").coordinate);
                    current.put("out", cable.getOutput("01").coordinate);
                    toSave.get(components.getKey()).add(current);
                }
                continue;
            }
            toSave.put(components.getKey(), new ArrayList<Coordinate>());
            for (Component component:
                    components.getValue()){
                toSave.get(components.getKey()).add(component.getCoord());
            }
        }

        System.out.println(toSave);

        Gson gson = new Gson();


        FileWriter out = new FileWriter(new File(this.projectFile));
        out.append(gson.toJson(toSave));
        out.close();
    }

    public void load() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();

        BufferedReader in = new BufferedReader(new FileReader(this.projectFile));
        String line;

        StringBuffer data = new StringBuffer();

        while ((line = in.readLine()) != null){
            data.append(line);
        }

        Type type = new TypeToken<HashMap<String, ArrayList>>(){}.getType();
        HashMap<String, ArrayList> componentsSource = gson.fromJson(data.toString(),type);

        Type typeCoord = new TypeToken<Coordinate>(){}.getType();
        for (Map.Entry<String, ArrayList> components:
                componentsSource.entrySet()){
            if(Cable.class.isAssignableFrom(Class.forName(components.getKey()))){
                continue;
            }

            for (Object coord :
                    components.getValue()) {
                String stringCoord = gson.toJson(coord);

                Coordinate parseCoord = gson.fromJson(stringCoord, typeCoord);

                this.addComponent(parseCoord, components.getKey());
            }

        }

        Type typeCableCoord = new TypeToken<HashMap<String, Coordinate>>(){}.getType();
        for (Object coord:
                componentsSource.get(Cable.class.getName())) {
            String stringCoord = gson.toJson(coord);

            HashMap<String, Coordinate> parseCoord = gson.fromJson(stringCoord, typeCableCoord);

            this.addCable(parseCoord.get("in"), parseCoord.get("out"));
        }

        this.drawCircuit();
    }
}
