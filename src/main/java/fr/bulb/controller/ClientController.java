package fr.bulb.controller;

import fr.bulb.view.Connection;
import fr.bulb.view.Propos;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ClientController {

    @FXML
    javafx.scene.layout.BorderPane borderPane;

    @FXML
    public void connection() throws Exception {
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
}
