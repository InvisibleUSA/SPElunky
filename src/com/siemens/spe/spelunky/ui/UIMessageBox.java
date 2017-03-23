package com.siemens.spe.spelunky.ui;

import com.siemens.spe.spelunky.map.Map;
import com.siemens.spe.spelunky.system.GameObject;
import com.siemens.spe.spelunky.system.Position;
import com.siemens.spe.spelunky.system.Settings;
import com.siemens.spe.spelunky.system.TextureManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

/**
 * Created by 152544be on 23.03.2017.
 */
public class UIMessageBox implements GameObject{
    private Position rootMessageBox;
    private Position sizeMessageBox;
    private Image backgroundImage;
    private String message;
    private Font messageFont;
    private final int MESSAGE_SIZE = 120;
    private final String FONT_PATH = "file:" + System.getProperty("user.dir") + "\\fonts\\Avara.ttf";

    public UIMessageBox(String message) {
        this.rootMessageBox = new Position(Settings.screenDimensionX/8, Settings.screenDimensionY/4);
        this.sizeMessageBox = new Position((int)(Settings.screenDimensionX/1.25), Settings.screenDimensionY/3);
        this.message = message;
        this.backgroundImage = TextureManager.getTexture("Banner2");
    }

    public void setBackgroundImage(Image img) {
        this.backgroundImage = img;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void draw(GraphicsContext gc) {
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, rootMessageBox.x, rootMessageBox.y, sizeMessageBox.x, sizeMessageBox.y);
        }
        messageFont = Font.loadFont(FONT_PATH, MESSAGE_SIZE);
        gc.setFont(messageFont);
        gc.fillText(message, rootMessageBox.x + 60, rootMessageBox.y+ sizeMessageBox.y/2 + 40,sizeMessageBox.x - 60);

    }

    @Override
    public void update(Map map) {

    }

    @Override
    public Position getPosition() {
        return new Position(-1,-1);
    }

}
