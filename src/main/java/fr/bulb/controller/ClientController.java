package fr.bulb.controller;

import fr.bulb.view.Connection;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 03/07/2017
 */
public class ClientController {


    @FXML
    javafx.scene.layout.BorderPane borderPane;

    @FXML
    public void connection() throws Exception {
        new Connection().createView();
    }
}
