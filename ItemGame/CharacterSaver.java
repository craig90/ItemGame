public class CharacterSaver {

    Character character;
    FileWrite fileWrite;
    private String fileName = "";
    public static final String LINE_SEPARATOR = ":";
    public static final String FILE_EXTENSTION = ".txt";
    public static final String FILE_NAME_REGEX = "[\\s+]";
    
    public CharacterSaver(Character character) {
        this.character = character;
        this.fileWrite = new FileWrite();
    }

    public void setSaveFileName(String fileName) {
        this.fileName = fileName;
    }

    private String getFileName() {
        if (this.fileName != "") {
            return this.fileName;
        }
        
        // generate fileName
        return (character.getName().replaceAll(FILE_NAME_REGEX, "") + FILE_EXTENSTION);
    }

    
    /**
     * Writes the character out to file
     */
    public void saveCharacter() {

        if (this.character == null) {
            System.out.println("Error saving character to file.. no character to save");
        }

        this.fileWrite.openFile(getFileName());

        // follow the ordre Charcter Name, Level, Experience
        this.fileWrite.writeLine(FileKeywords.CHARACTER_NAME + LINE_SEPARATOR + this.character.getName());
        this.fileWrite.writeLine(CharacterStatNames.LEVEL + LINE_SEPARATOR + this.character.getLevel());
        this.fileWrite.writeLine(CharacterStatNames.EXPERIENCE + LINE_SEPARATOR + this.character.getExperience());

        // write character's items
        for (int i = 0; i < this.character.getItemManager().getItemListSize(); i++) {
            
            Item item = this.character.getItemManager().getItem(i);

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
