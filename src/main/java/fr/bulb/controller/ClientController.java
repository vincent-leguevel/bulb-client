package fr.bulb.controller;

import com.jfoenix.controls.JFXListView;
import fr.bulb.Component.Component;
import fr.bulb.Component.Coordinate;
import fr.bulb.Component.Coordinate.Orientation;
import fr.bulb.Component.Input;
import fr.bulb.Component.Output;
import fr.bulb.Project;
import fr.bulb.constants.ToolConstant;
import fr.bulb.plugins.Plugin;
import fr.bulb.view.Connection;
import fr.bulb.view.PluginManager;
import fr.bulb.view.Propos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClientController {

    private Project project;

    private List<Plugin> plugins = new ArrayList<>();

    @FXML
    private BorderPane borderPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ComboBox<ToolConstant> tools;

    @FXML
    private Pane canvasWrapper;

    private Color color = Color.WHITE;

    @FXML
    public Pane topPane;

    @FXML
    private Text errorLog;

    @FXML
    private JFXListView<String> passifComponentList;

    @FXML
    private JFXListView<String> actifComponentList;

    @FXML
    private MenuBar menuBar;

    private Timer eraseLog = new Timer();

    @FXML
    private void initialize(){

        tools.getItems().setAll(FXCollections.observableArrayList(ToolConstant.values()));
        tools.getSelectionModel().selectFirst();

        //Delimitation visuelle du canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeLine(0,0,0,canvas.getHeight());
        gc.strokeLine(0,0,canvas.getWidth(),0);
        gc.strokeLine(canvas.getWidth(),0,canvas.getWidth(),canvas.getHeight());
        gc.strokeLine(0,0,0,canvas.getHeight());

        this.project = new Project(gc);

        ObservableList<String> data = FXCollections.observableArrayList("AlternatingEntryCurrent", "ExitCurrent", "Lamp");
        this.passifComponentList.setItems(data);

        ObservableList<String> data2 = FXCollections.observableArrayList("Button");
        this.actifComponentList.setItems(data2);

        ChangeListener<String> setSelectedComponent = new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov,
                                String old_val, String new_val) {
                project.setSelectComponent(new_val);
            }
        };
        this.actifComponentList.getSelectionModel().selectedItemProperty().addListener(setSelectedComponent);
        this.passifComponentList.getSelectionModel().selectedItemProperty().addListener(setSelectedComponent);
    }

    @FXML
    public void connection() {
        Stage root = (Stage) borderPane.getScene().getWindow();
        new Connection().createView();
    }

    @FXML
    public void propos() throws Exception {
        Stage root = (Stage) borderPane.getScene().getWindow();
        new Propos().createView();
    }

    @FXML
    public void managerPlugins() {
        new PluginManager(this,plugins).createView();
    }

    @FXML
    public void quit() throws Exception {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
    }

    @FXML
    public void playAnimation(){
        project.erasePreviewComponent();
        try {
            this.project.launchAnimation();
        }catch (Error error){
            errorLog.setText(error.getMessage());

            eraseLog.schedule(new TimerTask() {
                @Override
                public void run() {
                    errorLog.setText(null);
                    this.cancel();
                }
            }, 3000);
        }
    }

    @FXML
    public void stopAnimation(){
        this.project.stopAnimation();
    }

    @FXML
    public void click(MouseEvent e) {
        Coordinate coord = new Coordinate((int)e.getX(), (int)e.getY(), this.project.getOrientation());
        if(e.getButton() == MouseButton.PRIMARY) {
            switch (this.project.getState()) {
                case ANIMATION:
                    this.project.clickOnComponent(coord);
                    break;
                case EDITION:
                    Component clickedComponent = this.project.isInComponent(coord);
                    if (clickedComponent != null) {
                        Input isInput = clickedComponent.isInInput(coord);
                        Output isOutput = clickedComponent.isInOutput(coord);
                        if (isOutput != null) {
                            this.project.activeOutput = isOutput;
                        } else if (isInput != null && this.project.activeOutput != null) {
                            this.project.addCable(this.project.activeOutput, isInput);
                            this.project.activeOutput = null;
                        }
                    } else {
                        this.project.addComponent(coord);
                    }
                    break;
            }
        }else if (e.getButton() == MouseButton.SECONDARY){
            switch (this.project.getState()){
                case ANIMATION:
                    break;
                case EDITION:
                    this.project.removeComponent(coord);
                    break;
            }
        }
    }

    @FXML
    public void pickColor(ActionEvent e){
        color = colorPicker.getValue();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public ComboBox<ToolConstant> getTools() {
        return tools;
    }

    public void setTools(ComboBox<ToolConstant> tools) {
        this.tools = tools;
    }

    public Pane getTopPane() {
        return topPane;
    }

    public void setTopPane(Pane topPane) {
        this.topPane = topPane;
    }

    public Text getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(Text errorLog) {
        this.errorLog = errorLog;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public Timer getEraseLog() {
        return eraseLog;
    }

    public void setEraseLog(Timer eraseLog) {
        this.eraseLog = eraseLog;
    }

    public void keyEventHandler(KeyEvent e){
        switch (e.getCode()){
            case R:
                Orientation next = null;
                switch (this.project.getOrientation()) {
                    case UP:
                        next = Orientation.RIGHT;
                        break;
                    case RIGHT:
                        next = Orientation.DOWN;
                        break;
                    case DOWN:
                        next = Orientation.LEFT;
                        break;
                    case LEFT:
                        next = Orientation.UP;
                        break;
                }
                this.project.setOrientation(next);
                break;
            case S:
                if(e.isControlDown()){
                    if(this.project.getProjectFile() == null){
                        FileChooser fc = new FileChooser();
                        fc.setTitle("Enregistrer votre projet");
                        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BULB","*.bulb"));
                        File file = fc.showSaveDialog((Stage) borderPane.getScene().getWindow());
                        this.project.setProjectFile(file.getPath());
                        this.project.setName(file.getName());
                    }
                    try {
                        this.project.save();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                break;
            case O:
                if(e.isControlDown()){
                    FileChooser fc = new FileChooser();
                    fc.setTitle("Selectionner votre projet");
                    fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BULB","*.bulb"));
                    File file = fc.showOpenDialog((Stage) borderPane.getScene().getWindow());
                    this.project.setProjectFile(file.getPath());
                    this.project.setName(file.getName());
                    try {
                        this.project.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
                break;
        }
    }

    public void previewComponent(MouseEvent e){
        Coordinate coordinate = new Coordinate((int)e.getX(), (int)e.getY());
        if (this.project.isInComponent(coordinate) == null && this.project.getState() == Project.State.EDITION){
            this.project.previewSelectComponent(coordinate);
        }else{
            this.project.erasePreviewComponent();
        }
    }
}

