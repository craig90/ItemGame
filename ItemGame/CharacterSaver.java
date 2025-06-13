public class CharacterSaver {

    Character character;
    FileWrite fileWrite;
    public static final String testFileName = "output.txt";
    public static final String lineSeparator = ":";
    
    public CharacterSaver(Character character) {
        this.character = character;
        this.fileWrite = new FileWrite();
    }

    

    public void saveCharacter() {

        if (this.character == null) {
            System.out.println("Error saving character to file.. no character to save");
        }

        this.fileWrite.openFile(testFileName);

        // first thing is character name
        this.fileWrite.writeLine(FileKeywords.CHARACTER_NAME + lineSeparator + this.character.getName());

        // next is level
        this.fileWrite.writeLine(CharacterStatNames.LEVEL + lineSeparator + this.character.getLevel());

        // next is experience
        this.fileWrite.writeLine(CharacterStatNames.EXPERIENCE + lineSeparator + this.character.getLevel());
        System.out.println(CharacterStatNames.EXPERIENCE + lineSeparator + this.character.getLevel());

        // character end.. write extra line
        this.fileWrite.writeLine("");
        
        // write character's items
        for (int i = 0; i < this.character.getItemListSize(); i++) {
            
            Item item = this.character.getItem(i);

            this.fileWrite.writeLine(FileKeywords.ITEM_START);
            this.fileWrite.writeLine(FileKeywords.ITEM_TYPE + lineSeparator + item.getItemType());
            this.fileWrite.writeLine(FileKeywords.ITEM_NAME + lineSeparator + item.getItemName());

            // write out the items stats
            for (int j = 0; j < item.statListSize(); j++) {
                this.fileWrite.writeLine(item.getStat(j).getStatType() + lineSeparator + item.getStat(j).getValue());
            }

            this.fileWrite.writeLine(FileKeywords.ITEM_END);

        }

        this.fileWrite.closeFile();
    }

}
