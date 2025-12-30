package com.craig.itemgame;

import com.craig.itemgame.controller.GameController;
import com.craig.itemgame.model.Game;
import com.craig.itemgame.ui.Gui;

public class Launcher {

    public static void main(String[] args) {

        Game game = new Game();
        GameController gameController = new GameController(game);
        Gui gui = new Gui(gameController);
        gui.init();
    }

}
