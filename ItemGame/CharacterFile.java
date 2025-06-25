public class CharacterFile {

    private String characterName;
    private String fileName;
    private double level;

    public CharacterFile() {

    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public String getCharacterName() {
        return this.characterName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public double getLevel() {
        return this.level;
    }
}
