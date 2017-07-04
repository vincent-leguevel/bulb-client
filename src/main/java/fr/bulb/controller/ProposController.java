package fr.bulb.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ProposController {

    @FXML
    javafx.scene.layout.BorderPane borderPane;

    @FXML
    public void fermer() {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
    }
}
