package fr.bulb.controller;

import fr.bulb.constants.Tools;
import fr.bulb.view.Connection;
import fr.bulb.view.Propos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Collection;

public class ClientController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private ComboBox tools;

    private Color color = Color.WHITE;



    @FXML
    private void initialize() {


        tools.getItems().setAll(FXCollections.observableArrayList(Tools.values()));
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
    public void quitter() throws Exception {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
    }

    @FXML
    public void click(MouseEvent e) {
    }


    @FXML
    public void pickColor(ActionEvent e){
        System.out.println("Color");
        color = colorPicker.getValue();
    }
}

