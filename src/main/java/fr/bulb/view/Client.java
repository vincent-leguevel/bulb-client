package fr.bulb.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 03/07/2017
 */
public class Client extends Application {


    public void createView(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/client.fxml"));

        Scene scene = new Scene(root, 1400, 900);

        primaryStage.setTitle("Bulb");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
