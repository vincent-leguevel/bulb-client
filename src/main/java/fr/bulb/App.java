package fr.bulb;

import fr.bulb.Component.Coordinate;
import fr.bulb.Component.Current;
import fr.bulb.Component.Output;
import fr.bulb.defaultPack.Button;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Map;
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
                for (Button btn :
                        btnArray) {
                    System.out.println(btn.isInHitbox(new Coordinate((int)event.getX(), (int)event.getY())));
                    if(btn.isInHitbox(new Coordinate((int) event.getX(), (int)event.getY()))){
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
        final Button btn2 = new Button(new Coordinate(70, 200, Coordinate.Orientation.LEFT), gc);

        btn2.getOutput("01").setValue(new Current(10,10));

        btn.getInput("01").setSource(btn2.getOutput("01"));

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                btn.tick(gc);
                System.out.println(btn.getOutput("01").getValue());
            }
        },0,50);

        btnArray.add(btn);

        root.getChildren().add(canvas);


        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
