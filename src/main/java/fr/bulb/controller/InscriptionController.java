package fr.bulb.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class InscriptionController {

    @FXML
    javafx.scene.layout.BorderPane borderPane;

    @FXML
    public void annuler() {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
    }
}
