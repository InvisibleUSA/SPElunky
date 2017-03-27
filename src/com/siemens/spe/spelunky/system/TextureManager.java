package com.siemens.spe.spelunky.system;

import javafx.scene.image.Image;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class TextureManager{
    private static HashMap<String, Image> textures;

    public static void init(String filepath) throws Exception {
        textures = new HashMap<String, Image>();

        File folder =  new File(filepath);
        String[] listOfFiles = folder.list();

        for (String filename : listOfFiles)
            loadTexture(folder + "\\" + filename);
    }

    private static void loadTexture(String filename) throws Exception{
        Path file = Paths.get(filename);
        //InputStream is = Files.newInputStream(file);
        Image img = new Image("file:" + filename);
        textures.put(file.getFileName().toString().split("\\.")[0], img);
    }
    public static Image getTexture(String key)
    {
        return textures.get(key);
    }

}
