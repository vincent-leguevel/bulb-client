package fr.bulb;

import fr.bulb.Component.AlternatingCurrent;
import fr.bulb.Component.Coordinate;
import fr.bulb.Component.InterractiveComponent;
import fr.bulb.defaultPack.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(300, 250);

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        /*canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                for (InterractiveComponent btn :
                        btnArray) {
                    if(btn.isClicked(new Coordinate((int) event.getX(), (int)event.getY()))){
                        btn.onClick(gc);
                        System.out.println("HAS BEEN CLICKED");
                    }else{
                        System.out.println("HAS NOT BEEN CLICKED");
                    }
                }
            }
        });*/

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        //final Lamp btn = new Lamp(new Coordinate(50, 200, Coordinate.Orientation.UP), gc);
        final Lamp btn2 = new Lamp(new Coordinate(70, 100, Coordinate.Orientation.RIGHT), gc);

        Timer timer = new java.util.Timer();

        /*timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        btn.tick(gc);
                        System.out.println(btn.getOutput("01").getValue());
                    }
                });
            }
        }, 0, 50);*/

        root.getChildren().add(canvas);


        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
