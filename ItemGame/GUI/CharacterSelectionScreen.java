import java.awt.Component;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharacterSelectionScreen extends JPanel {

    ArrayList<CharacterFile> characterFiles;
    ArrayList<String> fileNames;
    Gui gui;
    JButton backToHomeScreenButton;

    public CharacterSelectionScreen(Gui gui) {
        this.gui = gui;
        this.characterFiles = new ArrayList<CharacterFile>();
        this.fileNames = new ArrayList<String>();

        this.backToHomeScreenButton = new JButton("Return to Home Screen");

        this.backToHomeScreenButton.addActionListener(e -> {
            System.out.println("Switching to: " + Gui.INTRO_SCREEN);
            this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.INTRO_SCREEN);
        });

    }

    public boolean update() {

        removeComponents();

        try {
            fileNames = FileReader.readFileNames();
            characterFiles = this.gui.getGame().getCharacterNamesFromFile(fileNames);
        } catch (FileNotFoundException e) {
            System.out.println("No files found");
            this.gui.errorSequence("Error - no character files found", Gui.INTRO_SCREEN);
            return false;
        }

        for (CharacterFile characterFile : characterFiles) {
            
            JLabel label = new JLabel(characterFile.getCharacterName() + " Level: " + characterFile.getLevel());
            JButton button = new JButton("Load");
            this.add(label);
            this.add(button);
            
            button.addActionListener(e -> {
                
                try {
                    this.gui.getGame().setCharacter(this.gui.getGame().fileParcer.loadCharacterFromFileReWrite(characterFile));
                    this.gui.characterScreen.setCharacter();
                    System.out.println("Switching to: " + Gui.CHARACTER_SCREEN);
                    this.gui.cardLayout.show(this.gui.cardLayoutPanel, Gui.CHARACTER_SCREEN);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    // ex.printStackTrace();
                    this.gui.errorSequence("Error loading character from file", Gui.CHARACTER_SELECTION_SCREEN);
                }
            });   

        }

        this.add(this.backToHomeScreenButton);
        return true;

    }

    public void removeComponents() {

        Component[] componentList = this.getComponents();

        for(Component component : componentList){
            this.remove(component);
        }

        this.revalidate();
        this.repaint();

    }

}
