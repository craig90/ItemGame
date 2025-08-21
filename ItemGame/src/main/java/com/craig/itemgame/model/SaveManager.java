package com.craig.itemgame.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;


public class SaveManager {
    
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void saveCharacter(Character character, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(character, writer);
        }
    }

}
