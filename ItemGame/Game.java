public class Game {

    Character character;

    // maybe initialize the GUI, and Character here?
    public Game () {


    }

    public void runGame() {

        // read character information from file
        // fileIO.read();

        character = new Character();
        character.dummyData();
        // character.health.updateValue();
        character.displayCharacter();


        // write to file when we're done
        // fileIO.write();


    }

}
