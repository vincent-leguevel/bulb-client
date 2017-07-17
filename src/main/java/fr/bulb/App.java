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
        final ArrayList<Button> btnArray = new ArrayList<Button>();

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(300, 250);

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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
        });

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        final Button btn = new Button(new Coordinate(50, 200, Coordinate.Orientation.UP), gc);
        final Button btn2 = new Button(new Coordinate(70, 200, Coordinate.Orientation.RIGHT), gc);

        btn2.getOutput("01").setValue(new AlternatingCurrent(10,10));

        btn.getInput("01").setSource(btn2.getOutput("01"));

        Timer timer = new java.util.Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        btn.tick(gc);
                        System.out.println(btn.getOutput("01").getValue());
                    }
                });
            }
        }, 0, 50);

        btnArray.add(btn);
        btnArray.add(btn2);

        root.getChildren().add(canvas);


        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
