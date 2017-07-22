package fr.bulb.controller;

import fr.bulb.constants.ToolConstant;
import fr.bulb.plugins.Plugin;
import fr.bulb.view.Connection;
import fr.bulb.view.PluginManager;
import fr.bulb.view.Propos;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ClientController {

    private Double lastXMouse = 0D;
    private Double lastYMouse = 0D;


    private List<Plugin> plugins = new ArrayList<>();

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
    private void initialize() {


        tools.getItems().setAll(FXCollections.observableArrayList(ToolConstant.values()));
        tools.getSelectionModel().selectFirst();

        //Delimitation visuelle du canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeLine(0,0,0,canvas.getHeight());
        gc.strokeLine(0,0,canvas.getWidth(),0);
        gc.strokeLine(canvas.getWidth(),0,canvas.getWidth(),canvas.getHeight());
        gc.strokeLine(0,0,0,canvas.getHeight());

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
    public void managerPlugins() {
        new PluginManager(this,plugins).createView();
    }

    @FXML
    public void quitter() throws Exception {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
    }

    @FXML
    public void click(MouseEvent e) {

        System.out.println("click");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.strokeLine(lastXMouse,lastYMouse,e.getX(),e.getY());
        lastXMouse = e.getX();
        lastYMouse = e.getY();
        System.out.println(tools.getSelectionModel().getSelectedItem());
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

    public void lol(){
        System.out.println("mdr");
    }
}

