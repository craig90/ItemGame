public class CharacterSaver {

    FileWrite fileWrite;
    public static final String LINE_SEPARATOR = ":";
    public static final String FILE_EXTENSTION = ".txt";
    public static final String FILE_NAME_REGEX = "[\\s+]";
    
    public CharacterSaver() {
        this.fileWrite = new FileWrite();
    }

    // public void setSaveFileName(String fileName) {
    //     this.fileName = fileName;
    // }

    private String getFileName(Character character) {
        
        /*if (!this.fileName.equals("")) {
            return this.fileName;
        }*/
        
        //System.out.println(character.getName().replaceAll(FILE_NAME_REGEX, "") + FILE_EXTENSTION);
        
        // generate fileName
        return (character.getName().replaceAll(FILE_NAME_REGEX, "") + FILE_EXTENSTION);
        
    }

    /*public void setCharacter(Character character) {
        this.character = character;
    }*/

    /**
     * Writes the character out to file
     */
    public void saveCharacter(Character character) {

        if (character == null) {
            System.out.println("Error saving character to file.. no character to save");
        }

        this.fileWrite.openFile(getFileName(character));

        // follow the ordre Charcter Name, Level, Experience
        this.fileWrite.writeLine(FileKeywords.CHARACTER_NAME + LINE_SEPARATOR + character.getName());
        this.fileWrite.writeLine(CharacterStatNames.LEVEL + LINE_SEPARATOR + character.getLevel());
        this.fileWrite.writeLine(CharacterStatNames.EXPERIENCE + LINE_SEPARATOR + character.getExperience());

        // write character's items
        for (Item item : character.getItemManager().getAllItems()) {
            
            this.fileWrite.writeLine(FileKeywords.ITEM_START);
            this.fileWrite.writeLine(FileKeywords.ITEM_TYPE + LINE_SEPARATOR + item.getItemType());
            this.fileWrite.writeLine(FileKeywords.ITEM_NAME + LINE_SEPARATOR + item.getItemName());

            // write out the items stats
            for (int j = 0; j < item.statListSize(); j++) {
                this.fileWrite.writeLine(item.getStat(j).getStatType() + LINE_SEPARATOR + item.getStat(j).getValue());
            }

            this.fileWrite.writeLine(FileKeywords.ITEM_END);

        }

        this.fileWrite.closeFile();
    }

}
