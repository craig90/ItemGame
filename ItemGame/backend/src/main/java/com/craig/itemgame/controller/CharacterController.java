package com.craig.itemgame.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CharacterController {

    @GetMapping("/character/{id}")
    public String getCharacterByID(@PathVariable String id) {
        return "Spring boot is running";
    }

    // get Characaters

    // generate item

    // delete item

    // move item

}
