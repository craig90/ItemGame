package com.craig.itemgame.model;

import com.craig.itemgame.model.Items.Item;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class SaveManager {
    
    private static final ExclusionStrategy swingExclusion = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            if (f.getDeclaredClass() == JLabel.class) {
                return true;
            }

            if (f.getDeclaredClass() == ImageIcon.class) {
                return true;
            }
            
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };
    
    private static final Gson gson = new GsonBuilder()
            .setExclusionStrategies(swingExclusion)
            .setPrettyPrinting()
            .registerTypeAdapter(Item.class, new ItemDeserializer())
            .create();

    public static void saveCharacter(Character character, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(character, writer);
        }
    }

    public static void saveItemManager(ItemManager itemManager, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(itemManager, writer);
        }
    }

    public static Character loadCharacter(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Character.class);
        }
    }

    

}
