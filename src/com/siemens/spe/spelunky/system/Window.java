package com.siemens.spe.spelunky.system;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Created by 152863eh on 21.03.2017.
 */
public class Window extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static Game game;
    public static KeyEvent keyInput;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene mainScene = new Scene(root, Settings.screenDimensionX, Settings.screenDimensionY);
        Controller c =  new Controller();
        mainScene.setOnKeyPressed(new KeyEventEventHandler());
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("SPElunky");
        primaryStage.show();
        Canvas canvas = new Canvas(Settings.screenDimensionX, Settings.screenDimensionY);
        root.getChildren().add( canvas );
        GraphicsContext gc = canvas.getGraphicsContext2D();
        keyInput = null;
        game = new Game(gc);
        c.start();
    }

    private static class KeyEventEventHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            keyInput = event;
            System.out.println(event.getCode().toString());
        }
    }

    private static class Controller extends AnimationTimer {
        @Override
        public void handle(long now) {
            game.update(now);
        }
    }
}
