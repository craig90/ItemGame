import java.util.ArrayList;

public class Character {

    public static final int MIN_NAME_LENGTH = 3;

    private String characterName;

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

    private ItemManager itemManager;


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

        itemManager = new ItemManager();
    
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


    public ItemManager getItemManager() {
        return this.itemManager;
    }

}