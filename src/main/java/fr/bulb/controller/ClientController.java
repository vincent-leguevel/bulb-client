package fr.bulb.controller;

import fr.bulb.Component.Coordinate.Orientation;
import fr.bulb.Project;
import fr.bulb.constants.Tools;
import fr.bulb.Component.*;
import fr.bulb.defaultPack.*;
import fr.bulb.defaultPack.Button;
import fr.bulb.view.Connection;
import fr.bulb.view.Propos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientController {

    private Project project;

    private Double lastXMouse = 0D;
    private Double lastYMouse = 0D;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Canvas canvas;

    @FXML
    private Label zoomState;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ComboBox tools;

    @FXML
    private Pane canvasWrapper;

    private Color color = Color.WHITE;



    @FXML
    private void initialize(){


        tools.getItems().setAll(FXCollections.observableArrayList(Tools.values()));
        tools.getSelectionModel().selectFirst();

        //Delimitation visuelle du canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeLine(0,0,0,canvas.getHeight());
        gc.strokeLine(0,0,canvas.getWidth(),0);
        gc.strokeLine(canvas.getWidth(),0,canvas.getWidth(),canvas.getHeight());
        gc.strokeLine(0,0,0,canvas.getHeight());

        project = new Project(gc);

        project.setOrientation(Orientation.DOWN);

        project.setSelectComponent(Button.class);

        project.addComponent(new Coordinate(85, 10, Orientation.DOWN), AlternatingEntryCurrent.class.getName());

        project.addComponent(new Coordinate(100,120, Orientation.DOWN), "fr.bulb.defaultPack.Button");

        project.addComponent(new Coordinate(75, 250, Orientation.DOWN), Lamp.class.getName());

        project.addComponent(new Coordinate(85, 380, Orientation.DOWN), ExitCurrent.class.getName());

    }

    @FXML
    public void connection() {
        Stage root = (Stage) borderPane.getScene().getWindow();
        new Connection().createView();
    }

    @FXML
    public void propos() throws Exception {
        new Propos().createView();
    }

    @FXML
    public void quitter() throws Exception {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
    }

    @FXML
    public void playAnimation(){
        this.project.erasePreviewComponent();
        this.project.toPreview = null;
        this.project.launchAnimation();
    }

    @FXML
    public void stopAnimation(){
        this.project.stopAnimation();
    }

    @FXML
    public void click(MouseEvent e) {
        Coordinate coord = new Coordinate((int)e.getX(), (int)e.getY(), this.project.getOrientation());
        switch (this.project.getState()){
            case ANIMATION:
                this.project.clickOnComponent(coord);
                break;
            case EDITION:
                Component clickedComponent = this.project.isInComponent(coord);
                if(clickedComponent != null){
                    Input isInput = clickedComponent.isInInput(coord);
                    Output isOutput = clickedComponent.isInOutput(coord);

                    if(isOutput != null){
                        this.project.activeOutput = isOutput;
                    }else if(isInput != null && this.project.activeOutput != null){
                        this.project.addCable(this.project.activeOutput, isInput);
                        this.project.activeOutput = null;
                    }
                }else {
                    this.project.addComponent(coord);
                }
                break;
        }
    }

    @FXML
    public void zoom() {
        if(canvas.getScaleX() < 2.6D){
            canvas.setScaleX(canvas.getScaleX()+0.2D);
            canvas.setScaleY(canvas.getScaleY()+0.2D);
            canvas.setTranslateX(canvas.getTranslateX()+200D);
            canvas.setTranslateY(canvas.getTranslateY()+200D);
            Integer z = Integer.parseInt(zoomState.getText())+20;
            zoomState.setText(z+"");

        }
    }

    @FXML
    public void zoomOut() {
        if(canvas.getScaleX() > 0.6D){
            canvas.setScaleX(canvas.getScaleX()-0.2D);
            canvas.setScaleY(canvas.getScaleY()-0.2D);
            canvas.setTranslateX(canvas.getTranslateX()-200D);
            canvas.setTranslateY(canvas.getTranslateY()-200D);
            Integer z = Integer.parseInt(zoomState.getText())-20;
            zoomState.setText(z+"");

        }
    }

    @FXML
    public void pickColor(ActionEvent e){
        System.out.println("Color");
        color = colorPicker.getValue();
    }

    public void rotateComponent(KeyEvent e){
        if(e.getCode() == KeyCode.R) {
            System.out.println("rotate");
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
        }
    }

    public void previewComponent(MouseEvent e){
        Coordinate coordinate = new Coordinate((int)e.getX(), (int)e.getY());
        if (this.project.isInComponent(coordinate) == null){
            this.project.previewSelectComponent(coordinate);
        }else{
            this.project.erasePreviewComponent();
        }
    }
}

