import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Game {

    private Character character;
    ItemGenerator itemGenerator;
    FileParcer fileParcer;
    CharacterSaver characterSaver; // instiantiate early TODO:?
    CommandLineReader commandLineReader;

    public Game () {
        this.itemGenerator = new ItemGenerator();
        this.fileParcer = new FileParcer();
        this.commandLineReader = new CommandLineReader();
        this.characterSaver = new CharacterSaver();
    }

    public CharacterSaver getCharacterSaver() {
        return this.characterSaver;
    }

    public Character getCharacter() {
        return this.character;
    }

    /**
     * used by the GUI to set the character based on the user's choice
     * @param character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * Shows the intro to the game giving user option to load a character from file or create a new character
     * @param scanner
     * @return
     */
    private int introScreen() {

        int input = -1;
        
        System.out.println("\nWelcome to the Item Game");
        System.out.println("Would you like to create a new character or load one from file?");
        System.out.println("1 - Load From File");
        System.out.println("2 - Create New Character");

        while (input != 1 && input != 2) {
            input = this.commandLineReader.getInteger();
        }

        return input;

    }

    
    /**
     * allows user to select the character they wish to load - returns the filename which cooresponds to the users choice
     * @param scanner
     * @return
     */
    public ArrayList<CharacterFile> getCharacterNamesFromFile(ArrayList<String> fileNames) throws FileNotFoundException {

        ArrayList <CharacterFile> characterFiles = new ArrayList<CharacterFile>();
        FileReader file = new FileReader();

        // load the character Names
        for (int i = 0; i < fileNames.size(); i++) {
            CharacterFile characterFile = new CharacterFile();
            characterFile.setFileName(fileNames.get(i));
            this.fileParcer.parceCharacterNameAndLevel(file, characterFile);

            if (characterFile.getCharacterName().equals("")) { // invalid file
                System.out.println("Error reading character name from file" + fileNames.get(i));
            } else {
                characterFile.setFileName(fileNames.get(i));
                characterFiles.add(characterFile);
            }
        }

        if (fileNames.size() == 0) {
            throw new FileNotFoundException("No valid character files found");
        }

        return characterFiles;

    }

    /**
     * 
     * @param characterNames
     * @return
     */
    private CharacterFile chooseCharacterScreen(ArrayList<CharacterFile> characterFiles) {

        int input = -1;
        System.out.println("\nPress the Number which cooresponds to the character you'd like to load");
        // show the characters that we can load from file
        for (int i = 0; i < characterFiles.size(); i++) {
            System.out.println(i + " - " + characterFiles.get(i).getCharacterName());
        }

        while (input < 0 || input >= characterFiles.size()) {
            input = commandLineReader.getInteger();
        }

        System.out.println("\nLoading character: " + characterFiles.get(input).getCharacterName());
        return characterFiles.get(input);
    }


    /**
     * This function will call the appropriate funtions to read the possible character names from file and ask the user
     * which they would like to load
     */    
    private boolean loadFromFile() {

        ArrayList<String> fileNames = new ArrayList<String>();
        ArrayList<CharacterFile> characterFiles = new ArrayList<CharacterFile>();
        CharacterFile characterToLoad;

        try {
            // grab file names, read character names then load selected character from file
            fileNames = FileReader.readFileNames();
            characterFiles = getCharacterNamesFromFile(fileNames);
            characterToLoad = chooseCharacterScreen(characterFiles);
            this.character = this.fileParcer.loadCharacterFromFile(characterToLoad);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error loading character from file");
            return false;
        } 

    }


    private boolean createNewCharacter() {
        
        String characterName = "";
        System.out.println("Enter a character name");
        characterName = this.commandLineReader.getLine();
        this.character = new Character();
        this.character.setName(characterName);
        this.character.setLevel(20); // TODO: implement this later
        this.character.setExperience(12345); // dummy data
        
        return true;
    }

    /**
     * Determine if user wants to create a new character or load one from file
     * @param scanner
     */
    public void gameInitialization() {

        int input = -1;
        
        while (input != 0) {

            input = introScreen();
            
            if (input == 1) {
                if (loadFromFile() == true) {
                    return;
                }
            } else if (input == 2) {
                if (createNewCharacter() == true) {
                    return;
                }
            } else {
                System.out.println("Invalid input.. try agian");
            }
            
        }
        
    }

    /**
     * shows the main menu of thing the user can view and change for the character
     * @param scanner
     */
    public int characterScreen() {

        // TODO: handle these loop conditions with constants?
        int input = -1;

        while (input != 0) {

            // update the stats each loop interation so we don't have to worry about calling it throughout the program
            this.character.updateStats();

            System.out.println("\n1 - Show Character's Base Stats");
            System.out.println("2 - Show Character's Stats");
            System.out.println("3 - Show Character's Items");
            System.out.println("4 - Get a New Item");
            System.out.println("5 - Save Changes");
            System.out.println("6 - Return to Character Selection Screen");
            System.out.println("0 - Exit Program - note any unsaved changes will be lost");
            input = commandLineReader.getInteger();

            if (input == 1) {
                this.character.displayBaseStats();
            } else if (input == 2) {
                this.character.displayStats();
            } else if (input == 3) {
                this.character.getItemManager().displayItems();
            } else if (input == 4) {
                itemCreationMenu();
            } else if (input == 5) {
                this.characterSaver.saveCharacter(this.character);
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

        int input = -1;

        while (input != 0) { 
            gameInitialization();
            input = characterScreen();
        }
        
        this.commandLineReader.scanner.close();

    }

    /**
     * Brings up the menu to create and possibly equip a new item
     * @param scanner
     */
    public void itemCreationMenu() {

        Item newItem = this.itemGenerator.createNewItem();
        System.out.println("Your new item: ");
        newItem.displayItem();
        
        // check if this type of item is already equipped
        Item existingItem = this.character.getItemManager().getItem(newItem.getItemType());
        if (existingItem != null) {
            Item.itemComparison(existingItem, newItem);
        }

        System.out.println("Would you like to equip this item? 1 - yes");
        System.out.println("Press any other key to discard the item");
        
        if (commandLineReader.getInteger() == 1) {
            this.character.getItemManager().addItem(newItem);
        }

    }

}
