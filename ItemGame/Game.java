import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private static final int CHARACTER_FILES_LINES = 3; // TODO: move this later, it should not be here


    Character character;
    ItemGenerator itemGenerator;

    // maybe initialize the GUI, and Character here?
    public Game () {
        this.itemGenerator = new ItemGenerator();

    }

    /**
     * Shows the intro to the game giving user option to load a character from file or create a new character
     * @param scanner
     * @return
     */
    private int introScreen(Scanner scanner) {

        int input = -1;
        
        while (input != 1 && input != 2) {
            System.out.println("\nWelcome to the Item Game");
            System.out.println("Would you like to create a new character or load one from file?");
            System.out.println("1 - Load From File");
            System.out.println("2 - Create New Character");
            input = scanner.nextInt();
        }

        return input;

    }

    
    /**
     * allows user to select the character they wish to load - returns the filename which cooresponds to the users choice
     * @param scanner
     * @return
     */
    private String selectCharacterFromFile(Scanner scanner) {

        ArrayList <String> fileNames = new ArrayList <String>();
        ArrayList <String> characterNames = new ArrayList<String>();

        FileReader file = new FileReader();
        FileParcer fileParcer = new FileParcer();

        int input = -1;

        // get the file Names from the directory
        FileReader.readFileNames(fileNames);

        // load the character Names
        for (int i = 0; i <fileNames.size(); i++) {
            String characterName = "";
            characterName = fileParcer.parceCharacterName(file, fileNames.get(i));
            
            if (characterName.equals("")) {
                System.out.println("Error reading character name");
            }
            characterNames.add(characterName);
        }

        System.out.println("\nPress the Number which coorespons to the character you'd like to load");
        // show the characters that we can load from file
        for (int i = 0; i < characterNames.size(); i++) {
            System.out.println(i + " - " + characterNames.get(i));
        }

        input = scanner.nextInt();

        System.out.println("\nLoading character: " + characterNames.get(input));
        return fileNames.get(input);

    }


    public void itemCreationMenu(Scanner scanner) {

        Item newItem = this.itemGenerator.createNewItem();
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

    /**
     * Determine if user wants to create a new character or load one from file
     * @param scanner
     */
    public void gameInitialization(Scanner scanner) {

        int input = -1;
        input = introScreen(scanner);

        if (input == 1) {
            String fileName = selectCharacterFromFile(scanner);
            this.loadCharacterFromFile(fileName);
        } else if (input == 2) {
            System.out.println("Create new Character... yet to be implemented");
            return;
        } else {
            System.out.println("Invalid input");
            return;
        }

    }

    /**
     * shows the main menu of thing the user can view and change for the character
     * @param scanner
     */
    public int characterScreen(Scanner scanner) {

        int input = -1;

        while (input != 0) {

            System.out.println("\n1 - Show Character's Base Stats");
            System.out.println("2 - Show Character's Stats");
            System.out.println("3 - Show Character's Items");
            System.out.println("4 - Get a New Item");
            System.out.println("5 - Save Changes");
            System.out.println("6 - Return to Character Selection Screen");
            System.out.println("0 - Exit Program - note any unsaved changes will be lost");
            input = scanner.nextInt();

            if (input == 1) {
                this.character.displayBaseStats();
            } else if (input == 2) {
                this.character.displayStats();
            } else if (input == 3) {
                this.character.displayItems();
            } else if (input == 4 ) {
                itemCreationMenu(scanner);
            } else if (input == 5) {
                System.out.println("Implement file save here");
            } else if (input == 6) {
                return -1;
            }

        }

        return 0; // user wishes to exit game

    }



    /**
     * main game loop
     */
    public void runGame() {

        // TODO: take input as String and convert to int to prevent program from crashing
        // test what happens if a string is entered
        int input = -1;
        Scanner scanner = new Scanner(System.in);

        while (input != 0) { 
            gameInitialization(scanner);
            this.character.updateStats(); // TODO: find out whats going on with this
            input = characterScreen(scanner);
        }
        
        scanner.close();

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
    public void loadCharacterFromFile(String filename) {

        this.character = new Character();

        FileReader fileReader;
        fileReader = new FileReader();
        fileReader.openFile(filename);

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
                } else if (item.statListSize() < Item.MIN_STATS) {
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
