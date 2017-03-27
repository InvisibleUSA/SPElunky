package com.siemens.spe.spelunky.system;

import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.utility.timer.TimerHandle;
import com.siemens.spe.spelunky.system.utility.timer.TimerManager;
import com.siemens.spe.spelunky.system.utility.timer.TimerType;
import com.siemens.spe.spelunky.ui.UIHealthBar;
import com.siemens.spe.spelunky.ui.UIInventoryBox;
import com.siemens.spe.spelunky.ui.UIMessageBox;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;

/**
 * Created by 152863eh on 21.03.2017.
 */

public class Game {
    private Map map;
    private UIHealthBar healthBar;
    private UIInventoryBox inventoryBox;
    private UIMessageBox messageBox;
    private long startingTime;
    private long timeSinceLastUpdate;
    private GraphicsContext graphicsContext, gcBuffer;
    private Canvas buffer;
    private MediaPlayer mediaPlayer;
    private TimerHandle clickTimer = TimerManager.requestTimerHandle(TimerType.AUTO_COUNTDOWN_TIMER, Settings.KeyHitTimer);
    /**
     * defines the current game state. This state is checked before each frame is drawn. *
     */
    public static GameState gameState;

    public Game(GraphicsContext gc) {
        graphicsContext = gc;
        startingTime = System.nanoTime();
        timeSinceLastUpdate = startingTime;
        Media sound = null;
        try {
            sound = new Media(new File(System.getProperty("user.dir") + "\\res\\sounds\\zone1_1.mp3").toURI().toString());
            TextureManager.init(System.getProperty("user.dir") + "\\res");
            map = new Map();
            map.loadMap(System.getProperty("user.dir") + "\\map.txt");
            healthBar = new UIHealthBar(new Position(Settings.healthBarPositionX, Settings.healthBarPositionY), new Position(Settings.healthBarWidth, Settings.healthBarHeight));
            inventoryBox = new UIInventoryBox(new Position(Settings.screenDimensionX, Settings.screenDimensionY), new Position(98, 558), new Position(15, 50), 66, 98);
            inventoryBox.setBackground(TextureManager.getTexture("InventoryBackground"));
            healthBar.setHealthBarLayer(TextureManager.getTexture("HealthBarBack"), TextureManager.getTexture("HealthBarBar"), TextureManager.getTexture("HealthBarFront"));

        } catch (IOException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("IO Exception");
            a.setContentText(e.getMessage());
            a.showAndWait();
        } catch (NullPointerException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Null Pointer Exception");
            a.setContentText(e.getMessage());
            a.showAndWait();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("General Exception");
            a.setContentText(e.getMessage());
            a.showAndWait();
        }
        if (sound != null) {
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setVolume(0.25);
            mediaPlayer.play();
        }
        buffer = new Canvas(Settings.tileDimensionsXY * map.getWidth(), Settings.tileDimensionsXY * map.getHeight());
        gcBuffer = buffer.getGraphicsContext2D();
        gameState = GameState.GAME_RUNNING;
    }

    public void update(long now) {
        long timepassed = now - startingTime;
        long elapsed = (now - timeSinceLastUpdate)/1000000;

        timeSinceLastUpdate = now;
        TimerManager.update(elapsed);

        switch (gameState) {
            case GAME_RUNNING:
                messageBox = null;
                if (Window.keyInput != null && clickTimer.ready()) {  //Switch && for || to move freely without beat binding
                    TimerManager.tick();
                    map.update();
                    inventoryBox.update(map);
                    healthBar.update(map);
                }
                break;
            case GAME_OVER_LOST:
                displayLostScreen();
                break;
            case GAME_OVER_WON:
                displayWonScreen();
                break;
            case GAME_PAUSED:
                displayPausedScreen();
        }
        Window.keyInput = null;
        gcBuffer.clearRect(0, 0, map.getWidth() * Settings.tileDimensionsXY, map.getHeight() * Settings.tileDimensionsXY);
        map.draw(gcBuffer);
        copyCanvas();
        healthBar.draw(graphicsContext);
        inventoryBox.draw(graphicsContext);
        if (messageBox != null)
            messageBox.draw(graphicsContext);
    }

    private void copyCanvas() {

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage image = buffer.snapshot(params, null);

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0, Settings.screenDimensionX, Settings.screenDimensionY);


        int dx1 = 0;
        int dy1 = 0;
        int w = Settings.screenDimensionX;
        int h = Settings.screenDimensionY;
        int x1 = (map.getPlayer().getPosition().x  * Settings.tileDimensionsXY - w / 2);
        int y1 = (map.getPlayer().getPosition().y  * Settings.tileDimensionsXY - h / 2);

        if (x1 + w > map.getWidth() * Settings.tileDimensionsXY)
            w = map.getWidth() * Settings.tileDimensionsXY - x1;
        if (y1 + h > map.getHeight() * Settings.tileDimensionsXY)
            h = map.getHeight() * Settings.tileDimensionsXY - y1;

        if (x1 < 0)
            x1 = 0;
        if (y1 < 0)
            y1 = 0;

        graphicsContext.drawImage(image, x1, y1, w, h, dx1, dy1, w, h);
    }

    private void displayPausedScreen() {
       if (messageBox == null) {
           messageBox = new UIMessageBox("Game is paused.");
       }
    }

    private void displayWonScreen() {
        if (messageBox == null) {
            messageBox = new UIMessageBox("You won.");
        }
    }

    private void displayLostScreen() {
        if (messageBox == null) {
            messageBox = new UIMessageBox("You lost.");
        }
    }
}