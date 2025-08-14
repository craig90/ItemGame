import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CharacterCreationScreen extends JPanel {

    Gui gui;

    public CharacterCreationScreen(Gui gui) {

        this.gui = gui;

        JLabel createCharLabel = new JLabel("Create a New Character");
        JLabel enterCharNameLabel = new JLabel("Enter a character name");
        JTextField charNameTextField = new JTextField(20);
        JButton submitCharNameButton = new JButton("Submit");
        JButton backToHomeScreenButtonTwo = new JButton("Return to Home Screen");
        this.add(createCharLabel);
        this.add(enterCharNameLabel);
        this.add(charNameTextField);
        this.add(submitCharNameButton);
        this.add(backToHomeScreenButtonTwo);


        submitCharNameButton.addActionListener(e -> {
            System.out.println("Creating new Character");
            Character newCharacter = new Character();
            
            if (!newCharacter.setName(charNameTextField.getText()) ) {
                this.gui.errorSequence("Name must be at least three characters", Gui.CHARACTER_CREATION_SCREEN);
                return;
            }

            newCharacter.setLevel(30); // TODO: implement this later
            newCharacter.setExperience(123456);
            // TODO: I don't like that these are separate calls
            this.gui.getGame().setCharacter(newCharacter);
            this.gui.characterScreen.loadCharacter();
            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.CHARACTER_SCREEN);
        });

        backToHomeScreenButtonTwo.addActionListener(e -> {
            System.out.println("Switching to: " + Gui.INTRO_SCREEN);
            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.INTRO_SCREEN);
        });


    }

}
