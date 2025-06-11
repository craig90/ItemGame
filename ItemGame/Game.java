import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    Character character;
    ItemGenerator itemGenerator;
    FileParcer fileParcer;

    // maybe initialize the GUI
    public Game () {
        this.itemGenerator = new ItemGenerator();
        this.fileParcer = new FileParcer();
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

        int input = -1;

        // get the file Names from the directory
        FileReader.readFileNames(fileNames);

        // load the character Names
        for (int i = 0; i <fileNames.size(); i++) {
            String characterName = "";
            characterName = this.fileParcer.parceCharacterName(file, fileNames.get(i));
            
            if (characterName.equals("")) {
                System.out.println("Error reading character name");
            }
            characterNames.add(characterName);
        }

        System.out.println("\nPress the Number which cooresponds to the character you'd like to load");
        // show the characters that we can load from file
        for (int i = 0; i < characterNames.size(); i++) {
            System.out.println(i + " - " + characterNames.get(i));
        }

        input = scanner.nextInt();

        if (input < 0 || input >= characterNames.size()) {
            System.out.println("Error, input is not valid"); // TODO: what to do here
            System.exit(0);
        }

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
        }

    }

    /**
     * Determine if user wants to create a new character or load one from file
     * @param scanner
     */
    public void gameInitialization(Scanner scanner) {

        int input = -1;
        input = introScreen(scanner);
        this.character = new Character();

        if (input == 1) {
            String fileName = selectCharacterFromFile(scanner);
            this.fileParcer.loadCharacterFromFile(fileName, character);
        } else if (input == 2) {
            System.out.println("Create new Character... yet to be implemented");
            return;
        } else {
            System.out.println("Closing game");
            System.exit(0);
        }

    }

    /**
     * shows the main menu of thing the user can view and change for the character
     * @param scanner
     */
    public int characterScreen(Scanner scanner) {

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
            input = scanner.nextInt();

            if (input == 1) {
                this.character.displayBaseStats();
            } else if (input == 2) {
                this.character.displayStats();
            } else if (input == 3) {
                this.character.displayItems();
            } else if (input == 4) {
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
            input = characterScreen(scanner);
        }
        
        scanner.close();

    }

}
