package fr.bulb;

import fr.bulb.Component.AlternatingCurrent;
import fr.bulb.Component.Coordinate;
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

import java.util.Timer;
import java.util.TimerTask;

public class App extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(500, 250);

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        final EntryCurrent generator = new EntryCurrent(new Coordinate(10, 100, Coordinate.Orientation.RIGHT), new AlternatingCurrent(230, 50), gc);
        final Cable cable1 = new Cable(new Coordinate(generator.getOutput("01").coordinate.getX(),generator.getOutput("01").coordinate.getY(), Coordinate.Orientation.RIGHT),90, gc);
        final Button btn = new Button(new Coordinate(cable1.getOutput("01").coordinate.getX(), cable1.getOutput("01").coordinate.getY() - 20, Coordinate.Orientation.RIGHT), gc);
        final Lamp lp = new Lamp(new Coordinate(btn.getOutput("01").coordinate.getX(), btn.getOutput("01").coordinate.getY() - 25, Coordinate.Orientation.RIGHT), gc);
        final ExitCurrent ground = new ExitCurrent(new Coordinate(lp.getOutput("01").coordinate.getX(), lp.getOutput("01").coordinate.getY()-15, Coordinate.Orientation.RIGHT), gc);

        cable1.getInput("01").setSource(generator.getOutput("01"));
        btn.getInput("01").setSource(cable1.getOutput("01"));
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
                        generator.tick(gc);
                        cable1.tick(gc);
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
