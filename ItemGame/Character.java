import java.util.ArrayList;

public class Character {

    String characterName;

    public Health health; // TODO: public just for now

    private CharacterStat level;
    private CharacterStat healthDummy;
    private CharacterStat damage;
    private CharacterStat defence;

    // private ArrayList<Item> items;
    private ArrayList<Item> itemList;

    // eventually this should be read in from a file
    public Character () {
        
        health = new Health(this);


        characterName = "";
        level = new CharacterStat(this, StatNames.LEVEL, 0);
        healthDummy = new CharacterStat(this, StatNames.HEALTH, 0);
        damage = new CharacterStat(this, StatNames.DAMAGE, 0);
        defence = new CharacterStat(this, StatNames.DEFENSE, 0);
        
        // items - new ArrayList<Item>(); // uncomment this when Item class has been implemented
        itemList = new ArrayList<Item>();
    }
    
    // return a double version of the level
    public double getLevel() {
        return this.level.getValue();
    }
    
    public ArrayList<Item> getItemList() {
        return this.itemList;
    }
    



    // just for testing purposes - fill this with dummy data
    public void dummyData(){

        this.characterName = "Duder";

        level.setValue(15);
        healthDummy.setValue(55);
        damage.setValue(15.5);
        defence.setValue(33);

        damage.updateValue();

        this.itemList.add(new Item("Helm", "Cool Helm"));
        this.itemList.add(new Item("Gloves", "Gold Gloves"));

        damage.updateValue();

    }

    // temporary method - the model should not be printing a display 
    public void displayCharacter() {
        System.out.println("\n" + this.characterName + " " + level.getStatType() + " " + level.getValue() + "\n");
        
        System.out.println("Stats:");
        System.out.println(healthDummy.getStatType() + ": " + healthDummy.getValue());
        System.out.println(damage.getStatType() + ": " + damage.getValue());
        System.out.println(defence.getStatType() + ": " + defence.getValue());

        System.out.println("");
        System.out.println("Items:");

        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(this.itemList.get(i).getItemType() + " " + " " + this.itemList.get(i).getItemName() );
        }

    }

}
