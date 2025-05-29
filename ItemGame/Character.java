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


    /*
     * 
     */
    public void updateStats() {

        for (int i = 0; i < this.statList.size(); i++) {
            this.statList.get(i).updateValue();
        }        

    }

    // temporary method - the model should not be printing a display 
    public void displayCharacter() {
        System.out.println("\n" + this.characterName + " " + level.getStatType() + " " + level.getValue() + " " + 
        experience.getStatType() + " " + experience.getValue() + "\n");
        
        System.out.println("Stats:");

        for (int i = 0; i < this.statList.size(); i ++) {
            System.out.println(this.statList.get(i).getStatType() + ": " + this.statList.get(i).getValue());
        }

        System.out.println("");
        System.out.println("Items:");

        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(this.itemList.get(i).getItemType() + " " + this.itemList.get(i).getItemName() );
            for (int j = 0; j < itemList.get(i).getStatList().size(); j++) {
                System.out.println( this.itemList.get(i).getStatList().get(j).type + " " + this.itemList.get(i).getStatList().get(j).value );
            }
            System.out.println("");
        }

    }

}