package fr.bulb.controller;

import fr.bulb.view.Connection;
import fr.bulb.view.Propos;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Stage;

public class ClientController {

    private Double lastXMouse = 0D;
    private Double lastYMouse = 0D;

    @FXML
    javafx.scene.layout.BorderPane borderPane;

    @FXML
    javafx.scene.control.Tab tabPassif;

    @FXML
    javafx.scene.control.ScrollBar sbPassif;

    @FXML
    javafx.scene.canvas.Canvas canevas;

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

        GraphicsContext gc = canevas.getGraphicsContext2D();
        gc.strokeLine(lastXMouse,lastYMouse,e.getX(),e.getY());
        lastXMouse = e.getX();
        lastYMouse = e.getY();
    }
}
