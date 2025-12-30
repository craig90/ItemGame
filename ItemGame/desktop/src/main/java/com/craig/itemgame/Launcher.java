package com.craig.itemgame;

// import java.io.File;

import com.craig.itemgame.controller.GameController;
import com.craig.itemgame.model.Game;
import com.craig.itemgame.ui.Gui;

public class Launcher {

    public static void main(String[] args) {

        // System.out.println("Working directory: " + new File(".").getAbsolutePath());

        Game game = new Game();
        GameController gameController = new GameController(game);
        Gui gui = new Gui(gameController);
        gui.init();
    }

}
