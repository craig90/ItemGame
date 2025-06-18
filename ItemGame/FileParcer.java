import java.io.FileNotFoundException;

public class FileParcer {

    private static final String REGEX = "[:]";
    private static final int CHARACTER_FILES_LINES = 3;

    public FileParcer() {

    }

    /**
     * This method will parse the character name from the first line of the file
     * @param file
     * @param characterName
     * @return the characterName or "" if not is found
     */
    public String parceCharacterName(FileReader file, String fileName) {

        String characterName = "";
        String line;
        String[] lineArray;

        file.openFile(fileName);

        line = file.getLine();
        lineArray = line.split(REGEX);

        // check if the file line starts with Character Name keyword, if so grab the Character Name
        if (lineArray[0].equals(FileKeywords.CHARACTER_NAME)) {
            characterName = lineArray[1];
            // System.out.println(characterName);
        }

        file.closeFile();
        return characterName;

    }

    /**
     * pulls the character data out of the file with some error checking
     * TODO: define some more constants somewhere and add error logic in character class
     * @param lineArray
     * @param lineNumber
     * @return
     */
    private boolean parceCharacter(Character character, String lineArray[], IntegerWrapper lineNumber) {
        
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
    }


    /**
     * intialize the character and item list from file data
     */
    public void loadCharacterFromFile(String fileName, Character character) throws FileNotFoundException {

        FileReader fileReader;
        fileReader = new FileReader();
        fileReader.openFile(fileName);

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
                if (parceItem(character, fileReader, lineNumber) == false) {
                    throw new FileNotFoundException("Error reading item at line " + lineNumber.getNumber());
                }
            }
            
            else {
                throw new FileNotFoundException("Error in file: invalid identifier at line " + lineNumber.getNumber());
            }
            
            lineNumber.setNumber(lineNumber.getNumber()+1);

        }
        
    }


    /**
     * This method parces an item out of the character file
     * @param fileReader
     * @param regex
     * @param lineNumber
     * @return
     */
    private boolean parceItem (Character character, FileReader fileReader, IntegerWrapper lineNumber) {

        String line;
        String[] lineArray;
        Item item = new Item("", "");

        while((line = fileReader.getLine()) != FileKeywords.END_OF_FILE) {

            lineArray = line.split(REGEX);

            if (lineArray[0].equals(FileKeywords.ITEM_TYPE)) {
                if (item.setItemType(lineArray[1]) == false) {
                    return false;
                }
            }

            else if (lineArray[0].equals(FileKeywords.ITEM_NAME)) {
                if (item.setItemName(lineArray[1]) == false) {
                    return false;
                }
            }

            // check the stat type and add it to the stat list
            else if (ItemStat.isLegit(lineArray[0]) == true) {
                
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
                
                if (item.isLegit() == false) {
                   return false;
                } else {
                    character.addItem(item);
                    return true;
                }
            } else {
                System.out.println("Error reading in item at line " + lineNumber.getNumber());
                return false;
            }
            
            // System.out.println(lineNumber.getNumber() + ": " + lineArray[0]);
            lineNumber.setNumber(lineNumber.getNumber()+1);
        
        }
        return false;
    }

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
    public static void main(String args[]){

        FileReader file = new FileReader();
        FileParcer fileParcer = new FileParcer();
        fileParcer.parceCharacterName(file, "ItemGame/CharacterFiles/character1.txt");

    }    


}
