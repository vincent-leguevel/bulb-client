package fr.bulb.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 03/07/2017
 */
public class InscritpionController {

    @FXML
    javafx.scene.layout.BorderPane borderPane;

    @FXML
    public void annuler() {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
    }
}
