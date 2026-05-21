package com.craig.itemgame.controller;

import org.springframework.web.bind.annotation.RestController;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.DTOs.CharacterSummaryDTO;
import com.craig.itemgame.DTOs.ItemDTO;
import com.craig.itemgame.game.CharacterNotFoundException;
import com.craig.itemgame.game.GameService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CharacterController {

    private GameService gameService;

    public CharacterController() {
        this.gameService = new GameService();
    }


    @GetMapping("/character/{id}")
    public CharacterDTO getCharacterByID(@PathVariable String id) throws CharacterNotFoundException {
        return this.gameService.getCharacter(id);
    }

    @GetMapping("/characters")
    public List<CharacterSummaryDTO> getCharacters() {
        return this.gameService.getCharacters();
    }

    // generate item
    @PostMapping("/items/generate")
    public ItemDTO generateItem() {
        return this.gameService.generateItem();
    }

    // delete item
    @DeleteMapping("/character/{characterId}/items/{itemId}")
    public CharacterDTO deleteItem(@PathVariable String characterId, @PathVariable String itemId) {
        return this.gameService.deleteItem(characterId, itemId);
    }

    // move item
    @PostMapping("/character/{characterId}/equip/{itemId}/{itemSlot}")
    public CharacterDTO equipItem(@PathVariable String characterId, @PathVariable String itemId, 
        @PathVariable String itemSlot) {
            return this.gameService.equipItem(characterId, itemId, itemSlot);
    }
    

}
