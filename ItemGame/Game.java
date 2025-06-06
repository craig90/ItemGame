import java.util.Scanner;

public class Game {

    private static final int CHARACTER_FILES_LINES = 3; // TODO: move this later, it should not be here


    Character character;
    ItemGenerator itemGenerator;

    // maybe initialize the GUI, and Character here?
    public Game () {


    }

    public void runGame() {

        Scanner scanner = new Scanner(System.in);
        int input = -1;
        
        System.out.println("Welcome to the Item Game");
        // System.out.println("Enter the Character File You'd Like to Load");
        // TODO: for now lets not implment this - later on read from user selected file

        this.initializeGame();
        this.character.updateStats();
        this.itemGenerator = new ItemGenerator();

        while (input != 0) {

            System.out.println("1 - Show Character's Base Stats");
            System.out.println("2 - Show Character's Stats");
            System.out.println("3 - Show Character's Items");
            System.out.println("4 - Get a New Item");
            System.out.println("0 - Exit Program");
            input = scanner.nextInt();

            if (input == 1) {
                this.character.displayBaseStats();
            } else if (input == 2) {
                this.character.displayStats();
            } else if (input == 3) {
                this.character.displayItems();
            } else if (input == 4 ) {
                
                Item newItem = itemGenerator.createNewItem();
                System.out.println("Your new item: ");
                newItem.displayItem();
                
                Item existingItem = this.character.getItem(newItem.getItemType());
                if (existingItem != null) {
                    existingItem.itemComparison(newItem);                    
                }

                System.out.println("Would you like to equip this item? 1 - yes");
                System.out.println("Press any other key to discard the item");
                if (scanner.nextInt() == 1) {
                    this.character.addItem(newItem);
                    this.character.updateStats();
                }

            }

        }
        
        scanner.close();
        
        // TODO: write to file when we're done

    }

    /**
     * TODO: this should not be in this file LOL
     * @param string
     * @return
     */
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
     * pulls the character data out of the file with some error checking
     * TODO: define some more constants somewhere and add error logic in character class
     * @param lineArray
     * @param lineNumber
     * @return
     */
    private boolean parceCharacter(String lineArray[], IntegerWrapper lineNumber) {
        
        if (lineNumber.getNumber() == 1) {
            if (lineArray[0].equals(FileKeywords.CHARACTER_NAME)) {
                this.character.setName(lineArray[1]);
            } else {
                return false;
            }
        } 
        
        if (lineNumber.getNumber() == 2) {
            if (lineArray[0].equals(CharacterStatNames.LEVEL)) {
                this.character.setLevel(StringToDouble(lineArray[1]));
            } else {
                return false;
            }
        }

        if (lineNumber.getNumber() == 3) {
            if (lineArray[0].equals(CharacterStatNames.EXPERIENCE)) {
                this.character.setExperience(StringToDouble(lineArray[1]));
            } else {
                return false;
            }
        }
        
        return true;
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
        String[] lineArray;
        
        IntegerWrapper lineNumber = new IntegerWrapper();
        lineNumber.setNumber(1);

        while ((line = fileReader.getLine()) != FileKeywords.END_OF_FILE) {
            
            lineArray = line.split(regex);
            
            // some error checking, every line should return an arguement
            // most lines should have two, the exception is a blank line, Item-Start and Item-End
            if (lineArray.length < 1) {
                System.out.println("Error reading file, insufficient arguements on a line " + lineNumber);
            } else if (lineArray.length < 2 && (!(lineArray[0].equals("") 
                || lineArray[0].equals(FileKeywords.ITEM_START) || lineArray[0].equals(FileKeywords.ITEM_END)))) {
                System.out.println("Error reading file, insufficient arguements on line " + lineNumber);
                System.out.println("The error string " + lineArray[0]);
            }

            // we start by reading the character at the beginning of the file
            if (lineNumber.getNumber() <= CHARACTER_FILES_LINES) {
                if (parceCharacter(lineArray, lineNumber) == false ) {
                    System.out.println("Error reading character at line " + lineNumber.getNumber());
                    return;
                }
            }
            
            // item start - this is still ugly
            else if (lineArray[0].equals(FileKeywords.ITEM_START)) {
                // System.out.println(lineNumber.getNumber() + ": " + lineArray[0]);
                lineNumber.setNumber(lineNumber.getNumber()+1);
                if (parceItem(fileReader, regex, lineNumber) == false) {
                    System.out.println("Error reading item at line " + lineNumber.getNumber());
                    return;
                }
            
            }
            
            else if (lineArray[0].equals(FileKeywords.EMPTY_LINE)) { // emptyline
            }
            
            else {
                System.out.println("Error in file: invalid identifier at line " + lineNumber.getNumber());
            }
            
            // System.out.println(lineNumber.getNumber() + ": " + lineArray[0]);
            lineNumber.setNumber(lineNumber.getNumber()+1);

        }
        
    }

    /*
     * 
     */
    private boolean parceItem(FileReader fileReader, String regex, IntegerWrapper lineNumber) {

        String line;
        String[] lineArray;
        Item item = new Item("", "");

        while((line = fileReader.getLine()) != FileKeywords.END_OF_FILE) {

            lineArray = line.split(regex);

            if (lineArray[0].equals(FileKeywords.ITEM_TYPE)) {
                if (item.setItemType(lineArray[1]) == false) {
                    return false;
                }
            }

            if (lineArray[0].equals(FileKeywords.ITEM_NAME)) {
                if (item.setItemName(lineArray[1]) == false) {
                    return false;
                }
            }

            // check the stat type and add it to the stat list
            if ((lineArray[0].equals(Item.DAMAGE_STAT_NAME)) || (lineArray[0].equals(Item.DEFENCE_STAT_NAME))
                || (lineArray[0].equals(Item.HEALTH_STAT_NAME)) || (lineArray[0].equals(Item.FIRE_RESISTANCE_STAT_NAME))
                || (lineArray[0].equals(Item.POISON_RESISTANCE_STAT_NAME)) || (lineArray[0].equals(Item.MAGIC_RESISTANCE_STAT_NAME))
                || (lineArray[0].equals(Item.LIGHTNING_RESISTANCE_STAT_NAME)) || (lineArray[0].equals(Item.COLD_RESISTANCE_STAT_NAME))) {
                Stat stat = new Stat(lineArray[0], StringToDouble(lineArray[1]));
                item.addStat(stat);
            }

            // found the end of the item - do a few error checks to make sure the item is legit
            // TODO: some of this logic should potentially be moved to the item class
            if (lineArray[0].equals(FileKeywords.ITEM_END)) {
                
                // if (item.isLegit() == false) {
                //    return false;
                //} else {
                // this.character.addItem(item);
                //}
                
                if (item.getItemName().equals("") || item.getItemType().equals("")) {
                    System.out.println("Error With Item Name or Type");
                    // weird case
                    return false;
                } else if (item.getStatList().size() < Item.MIN_STATS) {
                    System.out.println("Error: doesn't have enough stats!");
                    return false;
                } else {
                    this.character.addItem(item);
                    return true;       
                }
            }
            
            // System.out.println(lineNumber.getNumber() + ": " + lineArray[0]);
            lineNumber.setNumber(lineNumber.getNumber()+1);
        
        }
        return false;
    }

}
