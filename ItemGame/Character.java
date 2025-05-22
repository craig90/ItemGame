import java.util.ArrayList;

public class Character {

    String characterName;

    private Stat level;
    private Stat health;
    private Stat damage;
    private Stat defence;

    // private ArrayList<Item> items;
    private ArrayList<String> items;

    // eventually this should be read in from a file
    public Character () {
        
        characterName = "";
        level = new Stat(StatNames.LEVEL, 0);
        health = new Stat(StatNames.HEALTH, 0);
        damage = new Stat(StatNames.DAMAGE, 0);
        defence = new Stat(StatNames.DEFENSE, 0);
        
        // items - new ArrayList<Item>(); // uncomment this when Item class has been implemented
        items = new ArrayList<String>();
    }
    

    // public static void main(String[] args) {
    // }
    
    // just for testing purposes - fill this with dummy data
    public void dummyData(){

        this.characterName = "Duder";

        level.setValue(15);
        health.setValue(55);
        damage.setValue(15.5);
        defence.setValue(33);

        this.items.add(0, "Long Sword");
        this.items.add(1, "Gold Gloves");

    }

    // temporary method - the model should not be printing a display 
    public void displayCharacter() {
        System.out.println("\n" + this.characterName + " " + level.getName() + " " + level.getValue() + "\n");
        
        System.out.println("Stats:");
        System.out.println(health.getName() + ": " + health.getValue());
        System.out.println(damage.getName() + ": " + damage.getValue());
        System.out.println(defence.getName() + ": " + defence.getValue());

        System.out.println("");
        System.out.println("Items:");

        for (int i = 0; i < items.size(); i++) {
            System.out.println(this.items.get(i));
        }

    }

}
