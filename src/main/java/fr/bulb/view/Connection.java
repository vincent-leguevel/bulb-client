package fr.bulb.view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Connection {

    private double xOffset = 0;
    private double yOffset = 0;

    public void createView()  {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/connection.fxml"));
            final Stage stage = new Stage();
            stage.setTitle("Connection");
            stage.getIcons().add(new Image("/image/Logo.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();


            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    xOffset = me.getScreenX() - stage.getX();// - me.getSceneX();
                    yOffset = me.getScreenY() - stage.getY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {

                public void handle(MouseEvent me) {
                    stage.setX(me.getScreenX() - xOffset);
                    stage.setY(me.getScreenY() - yOffset);
                }
            });



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
