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
        System.out.println("Character Without Gear");
        character.displayCharacter();
        
        character.dummyItems();
        System.out.println("Character With Gear");
        character.displayCharacter();


        // write to file when we're done
        // fileIO.write();


    }

}
