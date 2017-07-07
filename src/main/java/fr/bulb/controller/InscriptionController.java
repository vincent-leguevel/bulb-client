package fr.bulb.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class InscriptionController {

    @FXML
    private BorderPane borderPane;

    @FXML
    public void annuler() {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
    }
}
