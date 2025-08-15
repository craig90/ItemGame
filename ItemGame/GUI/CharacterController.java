public class CharacterController {

    Game game;

    CharacterScreen characterScreen;

    public CharacterController(Game game) {
        this.game = game;
    }

    
    public boolean createCharacter(String characterName) {
        System.out.println("Creating new Character");
        
        // backend call
        return this.game.createCharacter(characterName);

        // Gui call
    }


    // public void loadCharacter() {
    //     this.game.setCharacter();
    //     this.characterScreen.loadCharacter();
    // }
    

    public void loadCharacterFromFile(CharacterFile characterFile) throws Exception {

        this.game.loadCharacterFromFile(characterFile);

    }

    public void saveCharacter() {
        this.game.getCharacterSaver().saveCharacter(this.game.getCharacter());
    }


    public void updateStats() {
        this.game.getCharacter().updateStats();
    }

    public String getName() {
        return this.game.getCharacter().getName();
    }

    public double getLevel() {
        return this.game.getCharacter().getLevel();
    }

    public double getExperience() {
        return this.game.getCharacter().getExperience();
    }

    public double getDamage() {
        return this.game.getCharacter().getDamage();
    }

    public double getHealth() {
        return this.game.getCharacter().getHealth();
    }

    public double getDefence() {
        return this.game.getCharacter().getDefence();
    }

    public double getFireResistance() {
        return this.game.getCharacter().getFireResistance();
    }

    public double getLightningResistance() {
        return this.game.getCharacter().getLightningResistance();
    }

    public double getColdResistance() {
        return this.game.getCharacter().getColdResistance();
    }

    public double getPoisonResistance() {
        return this.game.getCharacter().getPoisonResistance();
    }

    public double getMagicResistance() {
        return this.game.getCharacter().getMagicResistance();
    }
}
