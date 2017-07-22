package fr.bulb.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Propos {

    public void createView()  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/propos.fxml"));
            Stage stage = new Stage();
            stage.setTitle("A propos");
            stage.getIcons().add(new Image("/image/Logo.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
