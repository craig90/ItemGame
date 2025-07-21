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

        backToHomeScreenButtonTwo.addActionListener(e -> {
            System.out.println("Switching to: " + Gui.INTRO_SCREEN);
            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.INTRO_SCREEN);
        });


    }

}
