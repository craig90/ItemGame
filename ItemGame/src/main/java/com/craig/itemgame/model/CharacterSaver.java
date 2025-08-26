package com.craig.itemgame.model;

import java.awt.Point;
import java.io.IOException;
import java.util.Map;

import com.craig.itemgame.model.Items.Item;
import com.craig.itemgame.model.Items.Ring;

public class CharacterSaver {

    FileWrite fileWrite;
    public static final String LINE_SEPARATOR = ":";
    public static final String FILE_EXTENSTION = ".txt";
    public static final String FILE_NAME_REGEX = "[\\s+]";
    
    public CharacterSaver() {
        this.fileWrite = new FileWrite();
    }

    // public void setSaveFileName(String fileName) {
    //     this.fileName = fileName;
    // }

    private String getFileName(Character character) {
        
        /*if (!this.fileName.equals("")) {
            return this.fileName;
        }*/
        
        //System.out.println(character.getName().replaceAll(FILE_NAME_REGEX, "") + FILE_EXTENSTION);
        
        // generate fileName
        return (character.getName().replaceAll(FILE_NAME_REGEX, "") + FILE_EXTENSTION);
        
    }

    public void saveEquippedItems(Character character) {

        this.fileWrite.writeLine(FileKeywords.EQUIPPED_ITEMS_START);

        for (Item item : character.getItemManager().getAllEquippedItems()) {
            
            this.fileWrite.writeLine(FileKeywords.ITEM_START);
            this.fileWrite.writeLine(FileKeywords.ITEM_TYPE + LINE_SEPARATOR + item.getItemType());
            this.fileWrite.writeLine(FileKeywords.ITEM_NAME + LINE_SEPARATOR + item.getItemName());

            if (item instanceof Ring) {
                Ring ring = (Ring) item;
                this.fileWrite.writeLine(FileKeywords.RING_SLOT + LINE_SEPARATOR + ring.getRingSlot());
            }

            // write out the items stats
            for (int j = 0; j < item.statListSize(); j++) {
                this.fileWrite.writeLine(item.getStat(j).getStatType() + LINE_SEPARATOR + item.getStat(j).getValue());
            }

            this.fileWrite.writeLine(FileKeywords.ITEM_END);

        }

        this.fileWrite.writeLine(FileKeywords.EQUIPPED_ITEMS_END);

    }


    public void saveInventoryItems(Character character) {

        this.fileWrite.writeLine(FileKeywords.INVENTORY_START);

        for (Map.Entry<Item, Point> entry : character.getItemManager().getInventoryItems().entrySet()) {

            this.fileWrite.writeLine(FileKeywords.ITEM_START);

            this.fileWrite.writeLine(FileKeywords.ITEM_TYPE + LINE_SEPARATOR + entry.getKey().getItemType());
            this.fileWrite.writeLine(FileKeywords.ITEM_NAME + LINE_SEPARATOR + entry.getKey().getItemName());

            // x, y coordinates since we're in the inventory
            this.fileWrite.writeLine(FileKeywords.ITEM_X + LINE_SEPARATOR + entry.getValue().x);
            this.fileWrite.writeLine(FileKeywords.ITEM_Y + LINE_SEPARATOR + entry.getValue().y);

            for (int j = 0; j < entry.getKey().statListSize(); j++) {
                this.fileWrite.writeLine(entry.getKey().getStat(j).getStatType() + LINE_SEPARATOR + entry.getKey().getStat(j).getValue());
            }

            this.fileWrite.writeLine(FileKeywords.ITEM_END);

        }

        this.fileWrite.writeLine(FileKeywords.INVENTORY_END);

    }


    /*public void setCharacter(Character character) {
        this.character = character;
    }*/

    /**
     * Writes the character out to file
     */
    public void saveCharacter(Character character) {

        if (character == null) {
            System.out.println("Error saving character to file.. no character to save");
        }

        try {
            SaveManager.saveCharacter(character, (FileReader.directoryPath + "test.json"));
            //SaveManager.saveItemManager(character.getItemManager(), (FileReader.directoryPath + "test.json"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.fileWrite.openFile(getFileName(character));

        // follow the ordre Charcter Name, Level, Experience
        this.fileWrite.writeLine(FileKeywords.CHARACTER_NAME + LINE_SEPARATOR + character.getName());
        this.fileWrite.writeLine(CharacterStatNames.LEVEL.getType() + LINE_SEPARATOR + character.getLevel());
        this.fileWrite.writeLine(CharacterStatNames.EXPERIENCE.getType() + LINE_SEPARATOR + character.getExperience());

        saveEquippedItems(character);
        saveInventoryItems(character);
            
        this.fileWrite.closeFile();
    }

}
