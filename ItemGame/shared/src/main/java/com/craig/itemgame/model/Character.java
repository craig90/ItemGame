package com.craig.itemgame.model;

import java.util.Map;
import java.util.HashMap;

public class Character {

    public static final int MIN_NAME_LENGTH = 3;

    private static final double INIT_LEVEL = 1.0;
    private static final double INIT_EXPERIENCE = 0.0;

    private String characterName;

    private CharacterStat level;
    private CharacterStat experience;
    // private CharacterStat damage;
    // private CharacterStat health;
    // private CharacterStat defence;
    // private CharacterStat fireResistance;
    // private CharacterStat coldResistance;
    // private CharacterStat lightningResistance;
    // private CharacterStat poisonResistance;
    // private CharacterStat magicResistance;

    private Map<CharacterStatNames, CharacterStat> statMap;

    private ItemManager itemManager;


    // eventually this should be read in from a file
    public Character () {
        
        characterName = "";
        level = new CharacterStat(CharacterStatNames.LEVEL);
        experience = new CharacterStat(CharacterStatNames.EXPERIENCE);
        // damage = new CharacterStat(CharacterStatNames.DAMAGE);
        // health = new CharacterStat(CharacterStatNames.HEALTH);
        // defence = new CharacterStat(CharacterStatNames.DEFENCE);
        
        // fireResistance = new CharacterStat(CharacterStatNames.FIRE_RESISTANCE);
        // coldResistance = new CharacterStat(CharacterStatNames.COLD_RESISTANCE);
        // lightningResistance = new CharacterStat(CharacterStatNames.LIGHTNING_RESISTANCE);
        // poisonResistance = new CharacterStat(CharacterStatNames.POISON_RESISTANCE);
        // magicResistance = new CharacterStat(CharacterStatNames.MAGIC_RESISTANCE);

        statMap = new HashMap<>();

        statMap.put(CharacterStatNames.DAMAGE, new CharacterStat(CharacterStatNames.DAMAGE));
        statMap.put(CharacterStatNames.HEALTH, new CharacterStat(CharacterStatNames.HEALTH));
        statMap.put(CharacterStatNames.DEFENCE, new CharacterStat(CharacterStatNames.DEFENCE));
        statMap.put(CharacterStatNames.FIRE_RESISTANCE, new CharacterStat(CharacterStatNames.FIRE_RESISTANCE));
        statMap.put(CharacterStatNames.COLD_RESISTANCE, new CharacterStat(CharacterStatNames.COLD_RESISTANCE));
        statMap.put(CharacterStatNames.LIGHTNING_RESISTANCE, new CharacterStat(CharacterStatNames.LIGHTNING_RESISTANCE));
        statMap.put(CharacterStatNames.POISON_RESISTANCE, new CharacterStat(CharacterStatNames.POISON_RESISTANCE));
        statMap.put(CharacterStatNames.MAGIC_RESISTANCE, new CharacterStat(CharacterStatNames.MAGIC_RESISTANCE));

        itemManager = new ItemManager();

    }
    
    /**
     * Used to initialize some fields after load from a JSON file
     */
    // public void initAfterLoad() {
    //     this.itemManager.initAfterLoad();
    // }


    public double getExperience() {
        return this.experience.getValue();
    }

    public String getName() {
        return this.characterName;
    }

    // return a double version of the level
    public double getLevel() {
        return this.level.getValue();
    }
    
    public double getHealth() {
        return this.statMap.get(CharacterStatNames.HEALTH).getValue();
    }

    public double getDamage() {
        return this.statMap.get(CharacterStatNames.DAMAGE).getValue();
    }

    public double getDefence() {
        return this.statMap.get(CharacterStatNames.DEFENCE).getValue();
    }

    public double getPoisonResistance() {
        return this.statMap.get(CharacterStatNames.POISON_RESISTANCE).getValue();
    }

    public double getLightningResistance() {
        return this.statMap.get(CharacterStatNames.LIGHTNING_RESISTANCE).getValue();
    }

    public double getFireResistance() {
        return this.statMap.get(CharacterStatNames.FIRE_RESISTANCE).getValue();
    }

    public double getColdResistance() {
        return this.statMap.get(CharacterStatNames.COLD_RESISTANCE).getValue();
    }

    public double getMagicResistance() {
        return this.statMap.get(CharacterStatNames.MAGIC_RESISTANCE).getValue();
    }

    public Map<CharacterStatNames, CharacterStat> getCharacterStats() {
        return this.statMap;
    }

    
    public boolean setName(String name) {
        
        if (name.length() < MIN_NAME_LENGTH) {
            return false;
        } else {
            this.characterName = name;
            return true;
        }
    }

    public void setLevel(double level) {
        this.level.setValue(level);
    }

    public void setExperience(double experience) {
        this.experience.setValue(experience);
    }


    /**
     * Sets the character's level and experience to the defualt new character initialization values
     * and sets all of the base stats
     */
    public void initNewCharacter() {

        this.level.setValue(Character.INIT_LEVEL);
        this.experience.setValue(Character.INIT_EXPERIENCE);

        updateStats();

    }


    /**
     * 
     */
    public void updateStats() {

        for (Map.Entry<CharacterStatNames, CharacterStat> entry : statMap.entrySet()) {
            entry.getValue().updateValue(this.itemManager, getLevel());
        }        

    }


    /**
     * Displays the character's stats
     */
    public void displayStats() {
        
        System.out.println("\n" + this.characterName);
        System.out.println(level.getStatType() + " " + level.getValue()); 
        System.out.println(experience.getStatType() + " " + experience.getValue() + "\n");
        
        System.out.println("Stats:");

        for (Map.Entry<CharacterStatNames, CharacterStat> entry : this.statMap.entrySet()) {
            System.out.println(entry.getKey().getType() + ": " + entry.getValue().getValue());
        }
    
    }

    /**
     * displays the base stats for the character
     */
    public void displayBaseStats() {
        System.out.println("\nCharacter's base stats (not taking item stats into account):\n");
        System.out.println(this.characterName);
        System.out.println(this.level.getStatType() + " " + this.level.getValue());
        System.out.println(this.experience.getStatType() + " " + this.experience.getValue() + "\n");

        for (Map.Entry<CharacterStatNames, CharacterStat> entry : this.statMap.entrySet()) {
            System.out.println(entry.getKey().getType() + ": " + entry.getValue().getBaseValue());
        }

    }


    public ItemManager getItemManager() {
        return this.itemManager;
    }

}