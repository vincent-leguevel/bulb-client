package fr.bulb;

import fr.bulb.Component.AlternatingCurrent;
import fr.bulb.Component.Coordinate;
import fr.bulb.Component.InterractiveComponent;
import fr.bulb.Component.Output;
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

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        final Button btn = new Button(new Coordinate(50, 200, Coordinate.Orientation.UP), gc);
        final Lamp lp = new Lamp(new Coordinate(70, 100, Coordinate.Orientation.LEFT), gc);

        Output generator = new Output(null, null, null, new AlternatingCurrent(230, 50));

        btn.getInput("01").setSource(generator);
        lp.getInput("01").setSource(btn.getOutput("01"));

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(btn.isClicked(new Coordinate((int) event.getX(), (int)event.getY()))){
                    btn.onClick(gc);
                    System.out.println("HAS BEEN CLICKED");
                }else{
                    System.out.println("HAS NOT BEEN CLICKED");
                }
            }
        });

        Timer timer = new java.util.Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        btn.tick(gc);
                        lp.tick(gc);
                    }
                });
            }
        }, 0, 50);

        root.getChildren().add(canvas);


        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
