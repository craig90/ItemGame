import java.io.File;
import java.util.ArrayList;

public class Game {

    Character character;

    // maybe initialize the GUI, and Character here?
    public Game () {


    }

    public void runGame() {

        this.initializeGame();

        // this.character = new Character();
        // System.out.println("Character Without Gear");
        this.character.updateStats();
        
        System.out.println("Character With Gear");
        this.character.displayCharacter();

        // write to file when we're done
        // fileIO.write();

    }


    private double StringToDouble(String string) {

        double number = 0.0;
        try {
            number = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            System.out.println("Error converting string to double");
        }
        return number;

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
        String regex = "[:]";
        String[] stringArray;
        
        int lineNumber = 1;

        // TODO: EndOfFile needs to be defined somewhere
        while ((line = fileReader.getLine()) != FileKeywords.END_OF_FILE) {
            
            stringArray = line.split(regex);
            
            // some error checking, every line should return an arguement
            // most lines should have two, the exception is a blank line, Item-Start and Item-End
            if (stringArray.length < 1) {
                System.out.println("Error reading file, insufficient arguements on a line .." + lineNumber);
            } else if (stringArray.length < 2 && (!(stringArray[0].equals("") 
                || stringArray[0].equals(FileKeywords.ITEM_START) || stringArray[0].equals(FileKeywords.ITEM_END)))) {
                System.out.println("Error reading file, insufficient arguements on line " + lineNumber);
                System.out.println("The error string " + stringArray[0]);
            }

            if (stringArray[0].equals(FileKeywords.CHARACTER_NAME)) {
                this.character.setName(stringArray[1]);
            } 
            
            else if (stringArray[0].equals(StatNames.LEVEL)) {
                this.character.setLevel(StringToDouble(stringArray[1]));
            }

            else if (stringArray[0].equals(StatNames.EXPERIENCE)) {
                this.character.setExperience(StringToDouble(stringArray[1]));
            } 
            
            // item start - this is still ugly
            else if (stringArray[0].equals(FileKeywords.ITEM_START)) {
                lineNumber = initItem(fileReader, regex, lineNumber);
            }
            
            else if (stringArray[0].equals(FileKeywords.EMPTY_LINE)) { // emptyline
            }
            
            else {
                System.out.println("Error in file: invalid identifier at line " + lineNumber);
            }
            
            lineNumber++;
        }
        
    }

    /*
     * 
     */
    private int initItem(FileReader fileReader, String regex, int lineNumber) {

        String line;
        String[] stringArray;
        Item item = new Item("", "");

        while((line = fileReader.getLine()) != FileKeywords.END_OF_FILE) {

            stringArray = line.split(regex);

            // TODO: define these somewhere
            if (stringArray[0].equals(FileKeywords.ITEM_TYPE)) {
                item.setItemType(stringArray[1]);
            }

            if (stringArray[0].equals(FileKeywords.ITEM_NAME)) {
                item.setItemName(stringArray[1]);
            }

            // check the stat type and add it to the stat list
            if ((stringArray[0].equals(StatNames.DAMAGE)) || (stringArray[0].equals(StatNames.DEFENSE))
                || (stringArray[0].equals(StatNames.HEALTH)) || (stringArray[0].equals(StatNames.FIRE_RESISTANCE))
                || (stringArray[0].equals(StatNames.POISON_RESISTANCE)) || (stringArray[0].equals(StatNames.MAGIC_RESISTANCE))
                || (stringArray[0].equals(StatNames.LIGHTNING_RESISTANCE)) || (stringArray[0].equals(StatNames.COLD_RESISTANCE))) {
                Stat stat = new Stat(stringArray[0], StringToDouble(stringArray[1]));
                item.getStatList().add(stat);
            }

            // found the end of the item - do a few error checks to make sure the item is legit
            // TODO: some of this logic should potentially be moved to the item class
            if (stringArray[0].equals(FileKeywords.ITEM_END)) {
                
                if (item.getItemName().equals("") || item.getItemType().equals("")) {
                    System.out.println("Error With Item Name or Type");
                } else if (item.getStatList().size() == 0) {
                    System.out.println("Error: Item has no stats!");
                } else {
                    this.character.getItemList().add(item);
                    
                }
                return lineNumber;
            }

        }
        return lineNumber;
    }

}
