package fr.bulb.controller;

import fr.bulb.view.Connection;
import fr.bulb.view.Propos;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientController {

    private Double lastXMouse = 0D;
    private Double lastYMouse = 0D;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Canvas canvas;

    @FXML
    private StackPane stackPane;



    @FXML
    private void initialize() {

        stackPane.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeLine(0,0,0,canvas.getHeight());
        gc.strokeLine(0,0,canvas.getWidth(),0);
        gc.strokeLine(canvas.getWidth(),0,canvas.getWidth(),canvas.getHeight());
        gc.strokeLine(0,0,0,canvas.getHeight());

    }

    @FXML
    public void connection() {
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
        System.out.println("click");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeLine(lastXMouse,lastYMouse,e.getX(),e.getY());
        lastXMouse = e.getX();
        lastYMouse = e.getY();
    }

    @FXML
    public void zoom() {
        if(canvas.getScaleX() < 2.6D){
            canvas.setScaleX(canvas.getScaleX()+0.2D);
            canvas.setScaleY(canvas.getScaleY()+0.2D);
            canvas.setTranslateX(canvas.getTranslateX()+200D);
            canvas.setTranslateY(canvas.getTranslateY()+200D);

        }
    }

    @FXML
    public void zoomOut() {
        if(canvas.getScaleX() > 0.6D){
            canvas.setScaleX(canvas.getScaleX()-0.2D);
            canvas.setScaleY(canvas.getScaleY()-0.2D);
            canvas.setTranslateX(canvas.getTranslateX()-200D);
            canvas.setTranslateY(canvas.getTranslateY()-200D);

        }
    }
}

