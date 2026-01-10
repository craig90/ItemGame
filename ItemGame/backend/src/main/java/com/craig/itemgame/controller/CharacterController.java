package com.craig.itemgame.controller;

import org.springframework.web.bind.annotation.RestController;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.game.GameService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CharacterController {

    private GameService gameService;

    public CharacterController() {
        this.gameService = new GameService();
    }


    @GetMapping("/character/{id}")
    public CharacterDTO getCharacterByID(@PathVariable String id) {
        
        return this.gameService.getCharacter(id);
        
    }

    // get Characaters

    // generate item

    // delete item

    // move item

}
