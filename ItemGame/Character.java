import java.util.ArrayList;

public class Character {

    String characterName;

    // TODO: these top three will likely have to be subclassed
    private CharacterStat level;
    private CharacterStat experience;
    private CharacterStat health;
    
    private CharacterStat damage;
    private CharacterStat defence;
    private CharacterStat fireResistance;
    private CharacterStat coldResistance;
    private CharacterStat lightningResistance;
    private CharacterStat poisonResistance;
    private CharacterStat magicResistance;

    // it's probably easier if all the character stats are on a list
    private ArrayList<CharacterStat> statList;

    // list of the character's items
    private ArrayList<Item> itemList;

    // eventually this should be read in from a file
    public Character () {
        
        // TODO: Character Stat constructor should initialize these values instead of class Character
        characterName = "";
        level = new CharacterStat(this, StatNames.LEVEL, 0.0);
        experience = new CharacterStat(this, StatNames.EXPERIENCE, 0.0);
        health = new CharacterStat(this, StatNames.HEALTH, 0.0);
        damage = new CharacterStat(this, StatNames.DAMAGE, 0.0);
        defence = new CharacterStat(this, StatNames.DEFENSE, 0.0);
        
        fireResistance = new CharacterStat(this, StatNames.FIRE_RESISTANCE, 0.0);
        coldResistance = new CharacterStat(this, StatNames.COLD_RESISTANCE, 0.0);
        lightningResistance = new CharacterStat(this, StatNames.LIGHTNING_RESISTANCE, 0.0);
        poisonResistance = new CharacterStat(this, StatNames.POISON_RESISTANCE, 0.0);
        magicResistance = new CharacterStat(this, StatNames.MAGIC_RESISTANCE, 0.0);

        statList = new ArrayList<CharacterStat>();

        statList.add(health);
        statList.add(damage);
        statList.add(defence);
        statList.add(fireResistance);
        statList.add(coldResistance);
        statList.add(lightningResistance);
        statList.add(poisonResistance);
        statList.add(magicResistance);

        itemList = new ArrayList<Item>();
    
    }
    
    // return a double version of the level
    public double getLevel() {
        return this.level.getValue();
    }
    
    public ArrayList<Item> getItemList() {
        return this.itemList;
    }
    
    public void setName(String name) {
        this.characterName = name;
    }

    // TODO: this should only by done at initialization unless I want to make the game much more complex in the future
    public void setLevel(double level) {
        this.level.setValue(level);
    }

    public void setExperience(double experience) {
        this.experience.setValue(experience);
    }


    /**
     * 
     */
    public void updateStats() {

        for (int i = 0; i < this.statList.size(); i++) {
            this.statList.get(i).updateValue();
        }        

    }

    /**
     * Displays the character's stats - not sure if this should be moved? TODO
     */
    public void displayStats() {
        
        System.out.println("\n" + this.characterName + " " + level.getStatType() + " " + level.getValue() + " " + 
        experience.getStatType() + " " + experience.getValue() + "\n");
        
        System.out.println("Stats:");

        for (int i = 0; i < this.statList.size(); i ++) {
            System.out.println(this.statList.get(i).getStatType() + ": " + this.statList.get(i).getValue());
        }
    
    }

    /**
     * displays the base stats for the character
     */
    public void displayBaseStats() {
        System.out.println("Character's base stats (not taking item stats into account):");

        for (int i = 0; i < this.statList.size(); i++) {
            System.out.println(this.statList.get(i).getStatType() + ": " + this.statList.get(i).getBaseValue());
        }

    }

    /**
     * Displays the character's items
     */
    public void displayItems() {
        
        System.out.println("Items:");

        for (int i = 0; i < this.itemList.size(); i++) {
            this.itemList.get(i).displayItem();
            System.out.println("");
        }
    }


    // TODO: I'm wondering about potentially moving this shit to an item manager class..
    // I'm not sure if it makes complete sense for the character class to be aware of this logic

    /**
     * This will return wheather a specific item type is already equipped
     * @return boolean
     */
    public boolean isEquipped(String itemType) {

        if (this.getItem(itemType) != null) {
            return true;
        }
        return false;
    }

    /**
     * Returns the character's item specified by itemType
     * @param itemType
     * @return will return an item if it exists, otherwise returns null
     */
    public Item getItem(String itemType) {

        for (int i = 0; i <this.itemList.size(); i++) {
            if (this.itemList.get(i).getItemType().equals(itemType)) {
                return this.itemList.get(i);
            }
        }

        return null;
    }


}