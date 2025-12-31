package com.craig.itemgame.controller;

import org.springframework.web.bind.annotation.RestController;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.DummyDataSets.DummyCharacter;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CharacterController {

    @GetMapping("/character/{id}")
    public CharacterDTO getCharacterByID(@PathVariable String id) {
        
        DummyCharacter dummyCharacter = new DummyCharacter();
        return dummyCharacter.generate();

    }

    // get Characaters

    // generate item

    // delete item

    // move item

}
