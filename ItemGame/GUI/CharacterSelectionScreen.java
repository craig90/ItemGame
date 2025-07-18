import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharacterSelectionScreen extends JPanel {

    ArrayList<CharacterFile> characterFiles;
    ArrayList<String> fileNames;
    Gui gui;

    public CharacterSelectionScreen(Gui gui) {
        this.gui = gui;
        this.characterFiles = new ArrayList<CharacterFile>();
        this.fileNames = new ArrayList<String>();

        try {
            fileNames = FileReader.readFileNames();
            characterFiles = this.gui.getGame().getCharacterNamesFromFile(fileNames);
        } catch (FileNotFoundException e) {
            this.gui.errorSequence("Error - no character files found");
        }

        for (CharacterFile characterFile : characterFiles) {
            
            JLabel label = new JLabel(characterFile.getCharacterName() + " Level: " + characterFile.getLevel());
            JButton button = new JButton("Load");
            this.add(label);
            this.add(button);
            
            button.addActionListener(e -> {
                
                try {
                    this.gui.cardLayout.show(this.gui.mainPanel, "Character Screen");
                    this.gui.getGame().setCharacter(this.gui.getGame().fileParcer.loadCharacterFromFile(characterFile));
                    this.gui.getGame().getCharacter().updateStats();
                    // TODO: not sure if this is the best way
                    this.gui.characterScreen.setCharacter(this.gui.getGame().getCharacter());
                    this.gui.characterScreen.update();
                } catch (FileNotFoundException ex) {
                    this.gui.errorSequence("Error loading character from file");
                }
            });   

        }
        
        JButton backToHomeScreenButton = new JButton("Return to Home Screen");
        this.add(backToHomeScreenButton);

        backToHomeScreenButton.addActionListener(e -> {
            this.gui.cardLayout.show(this.gui.mainPanel, "Intro Screen");
        });

    }

    

    

}
