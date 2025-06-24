import java.util.ArrayList;

public class Character {

    private String characterName;

    // TODO: might have to subclass some of these
    private CharacterStat level;
    private CharacterStat experience;
    private CharacterStat damage;
    private CharacterStat health;
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
        
        characterName = "";
        level = new CharacterStat(this, CharacterStatNames.LEVEL);
        experience = new CharacterStat(this, CharacterStatNames.EXPERIENCE);
        damage = new CharacterStat(this, CharacterStatNames.DAMAGE);
        health = new CharacterStat(this, CharacterStatNames.HEALTH);
        defence = new CharacterStat(this, CharacterStatNames.DEFENCE);
        
        fireResistance = new CharacterStat(this, CharacterStatNames.FIRE_RESISTANCE);
        coldResistance = new CharacterStat(this, CharacterStatNames.COLD_RESISTANCE);
        lightningResistance = new CharacterStat(this, CharacterStatNames.LIGHTNING_RESISTANCE);
        poisonResistance = new CharacterStat(this, CharacterStatNames.POISON_RESISTANCE);
        magicResistance = new CharacterStat(this, CharacterStatNames.MAGIC_RESISTANCE);

        statList = new ArrayList<CharacterStat>();

        statList.add(damage);
        statList.add(health);
        statList.add(defence);
        statList.add(fireResistance);
        statList.add(coldResistance);
        statList.add(lightningResistance);
        statList.add(poisonResistance);
        statList.add(magicResistance);

        itemList = new ArrayList<Item>();
    
    }
    
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
        return this.health.getValue();
    }

    public double getDamage() {
        return this.damage.getValue();
    }

    public double getDefence() {
        return this.defence.getValue();
    }

    public double getPoisonResistance() {
        return this.poisonResistance.getValue();
    }

    public double getLightningResistance() {
        return this.lightningResistance.getValue();
    }

    public double getFireResistance() {
        return this.fireResistance.getValue();
    }

    public double getColdResistance() {
        return this.coldResistance.getValue();
    }

    public double getMagicResistance() {
        return this.magicResistance.getValue();
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
     * Displays the character's stats
     */
    public void displayStats() {
        
        System.out.println("\n" + this.characterName);
        System.out.println(level.getStatType() + " " + level.getValue()); 
        System.out.println(experience.getStatType() + " " + experience.getValue() + "\n");
        
        System.out.println("Stats:");

        for (int i = 0; i < this.statList.size(); i ++) {
            System.out.println(this.statList.get(i).getStatType() + ": " + this.statList.get(i).getValue());
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

        for (int i = 0; i < this.statList.size(); i++) {
            System.out.println(this.statList.get(i).getStatType() + ": " + this.statList.get(i).getBaseValue());
        }

    }

    /**
     * Displays the character's items
     */
    public void displayItems() {
        
        if (this.itemList.size() == 0) {
            System.out.println("No items equipped");
            return;
        }

        System.out.println("Items:");

        for (int i = 0; i < this.itemList.size(); i++) {
            this.itemList.get(i).displayItem();
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

        for (int i = 0; i < this.itemList.size(); i++) {
            if (this.itemList.get(i).getItemType().equals(itemType)) {
                return this.itemList.get(i);
            }
        }

        return null;
    }

    /**
     * return an item at the specified index
     * @param index
     * @return
     */
    public Item getItem(int index) {
        return this.itemList.get(index);
    }
 

    /**
     * this will remove an item from the character's item list with type itemType
     * @param itemType
     */
    public void removeItem(String itemType) {

        for (int i = 0; i < this.itemList.size(); i++) {
            if (this.itemList.get(i).getItemType().equals(itemType)) {
                System.out.println("Removing item " + this.itemList.get(i).getItemName());
                this.itemList.remove(i);
            }
        }

    }

    /**
     * this will add the item to the list and remove any existing item of the same item type
     * @param item
     */
    public void addItem(Item item) {

        // see if we have to remove the item first
        if (isEquipped(item.getItemType())) {
            removeItem(item.getItemType());
        }

        this.itemList.add(item);
    }

    /**
     * returns the number of items the character has equipped
     * @return
     */
    public int getItemListSize() {
        return this.itemList.size();
    }

}