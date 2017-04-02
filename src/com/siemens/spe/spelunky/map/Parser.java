package com.siemens.spe.spelunky.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by 152863eh on 21.03.2017.
 */
public class Parser {
    static final char SYMBOL_BLOCK = 'x';
    static final char SYMBOL_EMPTY = 'o';
    static final char SYMBOL_CHARACTER = 'c';
    static final char SYMBOL_ENEMY_BAT = 'G';
    static final char SYMBOL_ENEMY_BLUE_SLIME = 'K';
    static final char SYMBOL_ENEMY_ORANGE_SLIME = 'P';
    static final char SYMBOL_ENEMY_ZOMBIE = 'Y';
    static final char SYMBOL_ENEMY_SKELETON = 'S';
    static final char SYMBOL_ITEM_DAGGER = 'n';
    static final char SYMBOL_ITEM_LEATHER_ARMOR = 'm';
    static final char SYMBOL_ITEM_TITANIUM_SPEAR = 'l';
    static final char SYMBOL_ITEM_WHIP = 'g';
    static final char SYMBOL_ITEM_EMPTY_HEART_CONTAINER = 'z';
    static final char SYMBOL_ITEM_FILLED_HEART_CONTAINER = 'b';
    static final char SYMBOL_ITEM_RING_OF_MIGHT = 'r';
    static final char SYMBOL_EXIT = 'E';

    static ArrayList<String> parseMap(String filename) throws Exception {
        Path file;
        InputStream f = null;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            file = Paths.get(filename);
            f = Files.newInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(f));
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        } finally {
            try {
                f.close();
            } catch (IOException e) {
            }
        }
        return lines;
    }
}
