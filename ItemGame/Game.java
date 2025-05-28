import java.util.ArrayList;

public class Game {

    Character character;

    // maybe initialize the GUI, and Character here?
    public Game () {


    }

    public void runGame() {

        this.initializeGame();

        // this.character = new Character();
        // this.character.dummyData();
        System.out.println("Character Without Gear");
        this.character.updateStats();
        // character.displayCharacter();
        
        // this.character.dummyItems();
        System.out.println("Character With Gear");
        this.character.displayCharacter();


        // write to file when we're done
        // fileIO.write();


    }

    /**
     * intialize the character and item list from file data
     */
    public void initializeGame() {

        this.character = new Character();

        FileReader fileReader;
        fileReader = new FileReader();
        fileReader.openFile();

        String line;
        String regex = "[\s]";
        String[] stringArray;
        
        Item item = null;

        while ((line = fileReader.getLine()) != "EndOfFile") {
            
            stringArray = line.split(regex);

            if (stringArray.length < 1) {
                System.out.println("Error: expected more arguements in file line");
            }

            // TODO: add some shit for error catching
            // also the file keywords should be saved as constants
            if (stringArray[0].equals("Character-Name")) {
                this.character.setName(stringArray[1]);
            } 
            
            else if (stringArray[0].equals(StatNames.LEVEL)) {
                this.character.setLevel(Double.parseDouble(stringArray[1])); // TODO: try
            }

            else if (stringArray[0].equals(StatNames.EXPERIENCE)) {
                this.character.setExperience(Double.parseDouble(stringArray[1])); // TODO;try
            } 
            
            // TODO: move these keywords somewhere as constants
            else if (stringArray[0].equals("Item-Start")) {
                initItem(fileReader, item, regex);
            }
            
            else if (stringArray[0].equals("")) {
                System.out.println("New Line in File");
            }
            
            else {
                System.out.println("Error in file");
            }
            
            for (int i = 0; i < stringArray.length; i ++) {
                System.out.println(stringArray[i]);
            }

        }
        
    }

    /*
     * 
     */
    private void initItem(FileReader fileReader, Item item, String regex) {

        String line;
        String itemName = "";
        String itemType = "";
        String[] stringArray;
        ArrayList<Stat> stats = new ArrayList<Stat>();

        while((line = fileReader.getLine()) != "EndOfFile") {

            stringArray = line.split(regex);



            if (stringArray[0].equals("Item-Type")) {
                itemType = stringArray[1];
            }

            if (stringArray[0].equals("Item-Name")) {
                itemName = stringArray[1];
            }

            if (stringArray[0].equals(StatNames.DAMAGE)) {
                Stat stat = new Stat(StatNames.DAMAGE, Double.parseDouble(stringArray[1])); // TODO: try block this
                stats.add(stat);
            }
             
            if (stringArray[0].equals(StatNames.DEFENSE)) {
                Stat stat = new Stat(StatNames.DEFENSE, Double.parseDouble(stringArray[1]));
                stats.add(stat);
            }

            if (stringArray[0].equals(StatNames.HEALTH)) {
                Stat stat = new Stat(StatNames.HEALTH, Double.parseDouble(stringArray[1]));
                stats.add(stat);
            }

            if (stringArray[0].equals("Fire_Resistance")) {
                Stat stat = new Stat(StatNames.FIRE_RESISTANCE, Double.parseDouble(stringArray[1]));
                stats.add(stat);
            }
            
            if (stringArray[0].equals("Poison_Resistance")) {
                Stat stat = new Stat(StatNames.POISON_RESISTANCE, Double.parseDouble(stringArray[1]));
                stats.add(stat);
            }

            if (stringArray[0].equals("Magic_Resistance")) {
                Stat stat = new Stat(StatNames.MAGIC_RESISTANCE, Double.parseDouble(stringArray[1]));
                stats.add(stat);
            }

            if (stringArray[0].equals("Lightning_Resistance")) {
                Stat stat = new Stat(StatNames.LIGHTNING_RESISTANCE, Double.parseDouble(stringArray[1]));
                stats.add(stat);
            }

            if (stringArray[0].equals("Cold_Resistance")) {
                Stat stat = new Stat(StatNames.COLD_RESISTANCE, Double.parseDouble(stringArray[1]));
                stats.add(stat);
            }

            if (stringArray[0].equals("Item-End")) {
                if (itemName.equals("") || itemType.equals("")) {
                    System.out.println("Error With Item Name or Type");
                } else if (stats.size() == 0){
                    System.out.println("Error: Item has no stats!");
                } else {
                    System.out.println("Successfully found item");
                    item = new Item(itemType, itemName);
                    for (int i = 0; i < stats.size(); i++){
                        item.addStat(stats.get(i));
                    }
                    character.getItemList().add(item);
                    
                }
                return;
            }

        }
        
    }

}
