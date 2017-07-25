package fr.bulb.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Client extends Application {

    public void createView(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/client.fxml"));

        Scene scene = new Scene(root, 1400, 900);

        primaryStage.setTitle("Bulb");
        primaryStage.getIcons().add(new Image("/image/Logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
