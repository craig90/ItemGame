public class FileParcer {

    private static final String regex = "[:]";


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
        lineArray = line.split(regex);

        // check if the file line starts with Character Name keyword, if so grab the Character Name
        if (lineArray[0].equals(FileKeywords.CHARACTER_NAME)) {
            characterName = lineArray[1];
            // System.out.println(characterName);
        }

        file.closeFile();
        return characterName;

    }


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
