import java.io.FileNotFoundException;

public class FileParcer {

    private static final String REGEX = "[:]";
    // private static final int CHARACTER_FILES_LINES = 3;
    private static final int TWO_ELEMENT_LENGTH = 2;
    private static final int ONE_ELEMENT_LENGTH = 1;

    private static final int ITEM_END_ARRAY_LENGTH = 1;

    private int lineNumber;

    public FileParcer() {
        this.lineNumber = 0;
    }

    /**
     * Used when caller is looking for a line with exactly two elements, with throw exception if it find more or less
     * than two elements
     * @param file
     * @return
     * @throws Exception
     */
    public String[] parceTwoElementLine(FileReader file) throws Exception {

        String line = file.getLine();
        String[] lineArray = line.split(REGEX);

        if (lineArray.length != TWO_ELEMENT_LENGTH) {
            throw new Exception("Incorrect number of elements on line " + this.lineNumber);
        }

        this.lineNumber++;
        return lineArray;

    }

    /**
     * Used when the caller is expecting a line wiht exactly one element, otherwise throws exception
     * @param file
     * @return
     * @throws Exception
     */
    public String parceOneElementLine(FileReader file) throws Exception {

        String line = file.getLine();
        String[] lineArray = line.split(REGEX);

        if (lineArray.length != ONE_ELEMENT_LENGTH) {
            System.out.println("Incorrect number of elements on line " + this.lineNumber);
            throw new Exception();
        }

        this.lineNumber++;
        return lineArray[0];
        
    }

    /**
     * used when we don't know how many elements may be on a line
     * @param file
     * @param lineArray
     * @return
     */
    public String[] parceLine(FileReader file) {

        String line = file.getLine();
        String[] lineArray = line.split(REGEX);

        this.lineNumber++;
        return lineArray;
    }


    /**
     * This method will parse the character name from the first line of the file
     * @param file
     * @param characterName
     * @return the characterName or "" if not is found
     */
    public void parceCharacterNameAndLevel(FileReader file, CharacterFile characterFile) throws Exception {

        String[] lineArray;
        this.lineNumber = 0;

        file.openFile(characterFile.getFileName());

        lineArray = parceTwoElementLine(file);

        // check if the file line starts with Character Name keyword, if so grab the Character Name
        if (lineArray[0].equals(FileKeywords.CHARACTER_NAME)) {
            if (characterFile.setCharacterName(lineArray[1]) == false) {
                throw new Exception("Error reading character name at line " + this.lineNumber);
            }
        } else {
            throw new Exception("Error reading Character Name identifier at line " + this.lineNumber);
        }

        lineArray = parceTwoElementLine(file);

        // check if the file line starts with Character Level keyword, if so grab the Character Name
        if (lineArray[0].equals(CharacterStatNames.LEVEL)) {
            try {
                characterFile.setLevel(Double.parseDouble(lineArray[1]));
            } catch (Exception e) {
                throw new Exception("Error reading level on line " + this.lineNumber);
            }
        } else {
            throw new Exception("Error reading Level identifier at line " + this.lineNumber);
        }

        file.closeFile();

    }

    /**
     * pulls the character data out of the file with some error checking
     * @param lineArray
     * @param lineNumber
     * @return
     */
    /*private boolean parceCharacter(Character character, String lineArray[], IntegerWrapper lineNumber) {
        
        if (lineNumber.getNumber() == 1) {
            if (lineArray[0].equals(FileKeywords.CHARACTER_NAME)) {
                character.setName(lineArray[1]);
            } else {
                return false;
            }
        } 
        
        if (lineNumber.getNumber() == 2) {
            if (lineArray[0].equals(CharacterStatNames.LEVEL)) {
                double level = 0.0;
                try {
                    level = Double.parseDouble(lineArray[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Error with character level at line " + lineNumber);
                    return false;
                }
                character.setLevel(level);
            } else {
                return false;
            }
        }

        if (lineNumber.getNumber() == 3) {
            if (lineArray[0].equals(CharacterStatNames.EXPERIENCE)) {
                double experience = 0.0;
                try {
                    experience = Double.parseDouble(lineArray[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Error with character experience at line " + lineNumber);
                    return false;
                }
                character.setExperience(experience);

            } else {
                return false;
            }
        }
        
        return true;
    }*/

    /**
     * Parces the character name from file an sets it, throws exceptions for incorrect identifier and for invalid name
     * @param lineArray
     * @param character
     * @throws Exception
     */
    public void parceCharacterName(String[] lineArray, Character character) throws Exception {

        if (!lineArray[0].equals(FileKeywords.CHARACTER_NAME)) {
            throw new Exception("Error reading Character Name at line " + this.lineNumber);
        } else {
            if (character.setName(lineArray[1]) == false) {
                throw new Exception("Error reading Character Name at line " + this.lineNumber);
            }
        }
    }

    /**
     * Parces and sets the character's level. Throws exceptions for incorrect identifier or issue converting level to double
     * @param lineArray
     * @param character
     * @throws Exception
     * @throws NullPointerException
     * @throws NumberFormatException
     */
    public void parceCharacterLevel(String[] lineArray, Character character) throws Exception {

        if (!lineArray[0].equals(CharacterStatNames.LEVEL)) {
            throw new Exception("Error reading Character level at line " + this.lineNumber);
        } else {
            try {
                character.setLevel(Double.parseDouble(lineArray[1]));
            } catch (Exception e) {
                throw new Exception("Error setting level at line " + this.lineNumber);
            }
        }
    }

    /**
     * Parces and saves the experience, throws exceptions for unrecognized identifier or issue converting exp to double
     * @param lineArray
     * @param character
     * @throws Exception
     * @throws NullPointerException
     * @throws NumberFormatException
     */
    public void parceCharacterExperience(String[] lineArray, Character character) throws Exception {

        if (!lineArray[0].equals(CharacterStatNames.EXPERIENCE)) {
            throw new Exception("Error reading Character level at line " + this.lineNumber);
        } else {
            try {
                character.setExperience(Double.parseDouble(lineArray[1]));
            } catch (Exception e) {
                throw new Exception("Error setting experience at line " + this.lineNumber);
            }
        }
    }

    /**
     * Returns true for item start, false for EOF, otherwise it throws an exception - it should only be called when
     * searching for ItemStart and can find EOF if there are no items left
     * @param line
     * @return
     * @throws Exception
     */
    private boolean parceItemStart(String line) throws Exception {

        if (line.equals(FileKeywords.ITEM_START)) {
            return true;
        } else if (line.equals(FileKeywords.END_OF_FILE)) {
            return false;
        } else {
            throw new Exception("Unrecognized identifier at line " + this.lineNumber);
        }

    }
 
    /**
     * Parces the item type from file, throws exception if item type keyword is not found
     * @param lineArray
     * @return
     * @throws Exception
     */
    private String parceItemType(String[] lineArray) throws Exception {
        
        if (!lineArray[0].equals(FileKeywords.ITEM_TYPE)) {
            throw new Exception("Error reading item type at line " + this.lineNumber);
        } else {
            return lineArray[1];
        }

    }

    /**
     * parces the item name from file, throws exception for unrecognized identifier or issue with item name
     * @param lineArray
     * @param item
     * @throws Exception
     */
    private void parceItemName(String[] lineArray, Item item) throws Exception {

        if (!lineArray[0].equals(FileKeywords.ITEM_NAME)) {
            throw new Exception("Error reading item name at line " + this.lineNumber);
        } else {
            if (item.setItemName(lineArray[1]) == false ) {
                throw new Exception("Error with item name length at line " + this.lineNumber);
            }
        }

    }

    /**
     * returns true if sucessfully parsed a new item stat, false if item end, otherwise throws exception
     * @param lineArray
     * @param stat
     * @return
     * @throws Exception
     */
    private String[] parceItemStat(String[] lineArray) throws Exception {

        if (lineArray[0].equals(FileKeywords.ITEM_END)) {
            return lineArray;
        }
        
        if (ItemStat.isLegitStat(lineArray[0]) == false) {
            throw new Exception("Error reading stat name at line " + this.lineNumber);
        }

        return lineArray;
    }

    public void parceRingSlot(String[] lineArray, Item item) throws Exception {
        
        // System.out.println(lineArray[0]);
        if (!lineArray[0].equals(FileKeywords.RING_SLOT)) {
            throw new Exception("Error reading Ring slot at line " + this.lineNumber);
        }

        System.out.println(lineArray[1]);

        if (item instanceof Ring) {
            Ring ring = (Ring) item;
            try {
                EquipmentSlot slot = EquipmentSlot.valueOf(lineArray[1]);
                System.out.println(slot.toString());
                ring.setRingSlot(slot);
            } catch (Exception e) {
                throw new Exception("Error reading ring slot type at line " + this.lineNumber);
            }
        }
        
    }


    private boolean parceItem(FileReader fileReader, Character character) throws Exception {

        Item item = null;
        String itemType = "";        
        
        if (parceItemStart(parceOneElementLine(fileReader)) == false) {
            return false;
        }

        itemType = parceItemType(parceTwoElementLine(fileReader));
        item = createItem(itemType);

        parceItemName(parceTwoElementLine(fileReader), item);
        
        if (itemType.equals(ItemType.RING.toString())) {
            parceRingSlot(parceTwoElementLine(fileReader), item);
        }

        ItemStat stat = null;
        String[] lineArray;

        while ((lineArray = parceItemStat(parceLine(fileReader))).length > ITEM_END_ARRAY_LENGTH) {
            try {
                stat = new ItemStat(lineArray[0], Double.parseDouble(lineArray[1]));
            } catch (Exception e) {
                throw new Exception("Error converting stat value at line " + this.lineNumber);
            }
            item.addStat(stat);
        }

        if (item.isLegit() == true) {
            character.getItemManager().equipItem(item.getItemType().getEquipmentSlot(), item);
            return true;
        }

        throw new Exception("Error reading item in from file at line " + this.lineNumber);

    }

    public Character loadCharacterFromFile(CharacterFile characterFile) throws Exception {

        Character character = new Character();
        FileReader fileReader;
        boolean parceItem = true;
        
        fileReader = new FileReader();
        fileReader.openFile(characterFile.getFileName());

        this.lineNumber = 0;
        
        // the first three lines in the file are character name, level, and experience
        parceCharacterName(parceTwoElementLine(fileReader), character);
        parceCharacterLevel(parceTwoElementLine(fileReader), character);
        parceCharacterExperience(parceTwoElementLine(fileReader), character);

        // System.out.println("Added name, level, exp");

        // the character can have 0 items or many items, read them in one at a time
        while (parceItem == true) { 
            parceItem = parceItem(fileReader, character);
        }

        return character;

    }



    /**
     * intialize the character and item list from file data
     */
    /*public Character loadCharacterFromFile(CharacterFile characterFile) throws FileNotFoundException {

        Character character = new Character();

        FileReader fileReader;
        fileReader = new FileReader();
        fileReader.openFile(characterFile.getFileName());

        String line;
        String[] lineArray;
        
        IntegerWrapper lineNumber = new IntegerWrapper();
        lineNumber.setNumber(1);

        while ((line = fileReader.getLine()) != FileKeywords.END_OF_FILE) {
            
            lineArray = line.split(REGEX);
            
            // some error checking, every line should return an arguement
            // most lines should have two, the exception is a blank line, Item-Start and Item-End
            if (lineArray.length < 1) {
                throw new FileNotFoundException("Error reading file, insufficient arguements on a line " + lineNumber);
            } else if (lineArray.length < 2 && (!(lineArray[0].equals("") 
                || lineArray[0].equals(FileKeywords.ITEM_START) || lineArray[0].equals(FileKeywords.ITEM_END)))) {
                throw new FileNotFoundException("Error reading file, insufficient arguements on line " + lineNumber);
            }

            // we start by reading the character at the beginning of the file
            if (lineNumber.getNumber() <= CHARACTER_FILES_LINES) {
                if (parceCharacter(character, lineArray, lineNumber) == false ) {
                    throw new FileNotFoundException("Error reading character at line " + lineNumber.getNumber());
                }
            }
            
            // item start - this is still ugly
            else if (lineArray[0].equals(FileKeywords.ITEM_START)) {
                // System.out.println(lineNumber.getNumber() + ": " + lineArray[0]);
                lineNumber.setNumber(lineNumber.getNumber()+1);
                if (parceItem(character.getItemManager(), fileReader, lineNumber) == false) {
                    throw new FileNotFoundException("Error reading item at line " + lineNumber.getNumber());
                }
            }
            
            else {
                throw new FileNotFoundException("Error in file: invalid identifier at line " + lineNumber.getNumber());
            }
            
            lineNumber.setNumber(lineNumber.getNumber()+1);

        }
        
        return character;
    }*/



    private Item createItem(String itemType) throws Exception {
        
        Item item;

        // System.out.println(itemType);

        if (itemType.equals(ItemType.AMULET.toString())) {
            item = new Amulet("");
        } else if (itemType.equals(ItemType.BELT.toString())) {
            item = new Belt("");
        } else if (itemType.equals(ItemType.BOOTS.toString())) {
            item = new Boots("");
        } else if (itemType.equals(ItemType.CHEST.toString())) {
            item = new Chest("");
        } else if (itemType.equals(ItemType.GLOVES.toString())) {
            item = new Gloves("");
        } else if (itemType.equals(ItemType.HELM.toString())) {
            item = new Helm("");
        } else if (itemType.equals(ItemType.PANTS.toString())) {
            item = new Pants("");
        } else if (itemType.equals(ItemType.SHIELD.toString())) {
            item = new Shield("");
        } else if (itemType.equals(ItemType.WEAPON.toString())) {
            item = new Weapon("");
        } else if (itemType.equals(ItemType.RING.toString())) {
            item = new Ring("");
        } else {
            throw new Exception("Invalid item type");
        }

        return item;

    }

    /**
     * Reads the item type from file
     * @param line
     * @return
     * @throws Exception
     */
    /*private String parceItemType(String line) throws Exception {

        if (line == FileKeywords.END_OF_FILE) {
            throw new Exception();
        }

        String[] lineArray = line.split(REGEX);

        // first line must be item type
        if (!lineArray[0].equals(FileKeywords.ITEM_TYPE)) {
            throw new Exception();
        }
        
        return lineArray[1];

    }*/

    /**
     * Reads the item name from the file
     * @param line
     * @return
     * @throws Exception
     */
    /*private String readItemName(String line) throws Exception {
        
        if (line == FileKeywords.END_OF_FILE) {
            throw new Exception();
        }

        String[] lineArray = line.split(REGEX);

        if (!lineArray[0].equals(FileKeywords.ITEM_NAME)) {
            throw new Exception();
        }
    
        return lineArray[1];
    }*/

    /**
     * Reads the ring slot from the files
     * @param line
     * @return
     * @throws Exception
     */
    public String readRingSlot(String line) throws Exception { 
        
        if (line == FileKeywords.END_OF_FILE) {
            throw new Exception();
        }
        
        String[] lineArray = line.split(REGEX);

        if (lineArray[0].equals(FileKeywords.RING_SLOT)) {
            return lineArray[1];
        } else {
            throw new Exception();
        }
            

    }


    /**
     * This method parces an item out of the character file
     * @param fileReader
     * @param regex
     * @param lineNumber
     * @return
     */
    /*private boolean parceItem (ItemManager itemManager, FileReader fileReader, IntegerWrapper lineNumber) {

        String line;
        String[] lineArray;
        Item item = null;
        String itemType, itemName, ringSlot;

        // first line in the item MUST be item type
        try {
            itemType = parceItemType(fileReader.getLine());
            item = createItem(itemType);
        } catch (Exception e) {
            System.out.println("Error reading item type");
            return false;
        }
        
        lineNumber.setNumber(lineNumber.getNumber()+1);

        // second line in the item MUST be item type
        try {
            itemName = readItemName(fileReader.getLine());
            item.setItemName(itemName);
        } catch (Exception e) {
            System.out.println("Error reading item name");
            return false;
        }

        lineNumber.setNumber(lineNumber.getNumber()+1);

        // if it's a ring the third line must be the ring slot
        if (item instanceof Ring) {

            Ring ring = (Ring) item;
            EquipmentSlot slot;

            try {
                ringSlot = readRingSlot(fileReader.getLine());
                slot = EquipmentSlot.valueOf(ringSlot);
                System.out.println(slot.toString());
            } catch (Exception e) {
                System.out.println("Error reading ring slot");
                return false;
            }

            ring.setRingSlot(slot);
            lineNumber.setNumber(lineNumber.getNumber()+1);
        } 

        // now read in all the item stats
        while ((line = fileReader.getLine()) != FileKeywords.END_OF_FILE) {

            lineArray = line.split(REGEX);

            // check the stat type and add it to the stat list
            if (ItemStat.isLegitStat(lineArray[0]) == true) {
                
                double statValue = 0.0;
                try {
                    statValue = Double.parseDouble(lineArray[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Error reading stat value at line " + lineNumber.getNumber());
                    return false;
                }

                ItemStat stat = new ItemStat(lineArray[0], statValue);
                item.addStat(stat);
                
            }

            // found the end of the item - do a few error checks to make sure the item is legit
            else if (lineArray[0].equals(FileKeywords.ITEM_END)) {
       
                // System.out.println("Item End");
                if (item.isLegit() == false) {
                   return false;
                } else {
                    itemManager.equipItem(item, item.getItemType().getEquipmentSlot());
                    return true;
                }
            } else {
                System.out.println("Error reading item at line " + lineNumber.getNumber());
                return false;
            }
            
            // System.out.println(lineNumber.getNumber() + ": " + lineArray[0]);
            lineNumber.setNumber(lineNumber.getNumber()+1);
        
        }
        return false;
    }*/

    /**
     * Auxilary method - Attempts to convert a String to a double
     * @param string
     * @return
     */
    // public static boolean StringToDouble(String string, DoubleWrapper doubleWrapper) {

    //     double number = 0.0;
    //     try {
    //         number = Double.parseDouble(string);
    //         doubleWrapper.setNumber(number);
    //         return true;
    //     } catch (NumberFormatException e) {
    //         System.out.println("Error converting string to double");
    //         return false;
    //     }

    // }

    /**
     * this main method is purely for testing file parcer class
     * @param args
     */
    public static void main(String args[]) {

        Character character;
        FileParcer fileParcer = new FileParcer();
        CharacterFile characterFile = new CharacterFile();
        characterFile.setFileName("ItemGame/CharacterFiles/Craig.txt");
        //fileParcer.parceCharacterNameAndLevel(file, characterFile);
        
        try {
            character = fileParcer.loadCharacterFromFile(characterFile);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        } catch (Exception e) { // TODO: exceptions sould be more specific
            System.out.println(e.getMessage());
            return;
        }
        
        character.updateStats();
        character.displayStats();
    
    }    


}
