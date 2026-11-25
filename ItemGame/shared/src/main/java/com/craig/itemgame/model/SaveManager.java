package com.craig.itemgame.model;

import com.craig.itemgame.model.Items.Item;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

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

    public static CharacterHeader getFileHeader(String file) {
        
        CharacterHeader header = null;

        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            header = gson.fromJson(reader, CharacterHeader.class);
            // headers.add(new CharacterFile(file, header.getCharacterName(), header.getLevel()));
        } catch (Exception e) {

        }

        return header;
    }

    /**
     * TODO: clean up how this is done.  I feel like I have an unessary class, plus I'm wondering if there
     * should be a parent class that these subclass from.  There is some duplication of class
     * variables accorss Character, CharacterFile, and CharacterHeader
     * @return
     */
    public static List<CharacterFile> getCharacterFiles() {
        
        List<CharacterFile> characterFiles = new ArrayList<>();
        List<String> files = new ArrayList<>();

        try {
            files = MyFileReader.readFileNames(FileKeywords.JSON_FILE_EXTENSTION);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        for (String file : files) {
            CharacterHeader header = getFileHeader(file);
            characterFiles.add(new CharacterFile(file, header.getCharacterName(), header.getLevel().value, header.getId()));
        }

        return characterFiles;

    }


    public static void main (String[] args) {

        // CharacterHeader characterHeader = getFileHeader(FileKeywords.DIRECTORY_PATH + "Bob.json");
        // System.out.println(characterHeader.getCharacterName());
        // System.out.println(characterHeader.getLevel().value);

        List<CharacterFile> characterFiles = getCharacterFiles();

        for (CharacterFile characterFile : characterFiles) {
            System.out.println("File name: " + characterFile.getFileName() + " Character Name: " + 
            characterFile.getCharacterName() + " Level: " + characterFile.getLevel());
        }

    }

}
