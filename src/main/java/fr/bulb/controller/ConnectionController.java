package fr.bulb.controller;

import fr.bulb.view.Inscription;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 03/07/2017
 */
public class ConnectionController {

    @FXML
    javafx.scene.layout.BorderPane borderPane;


    @FXML
    public void inscription() {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
        new Inscription().createView();
    }
    @FXML
    public void annuler() {
        Stage root = (Stage) borderPane.getScene().getWindow();
        root.close();
    }


}
