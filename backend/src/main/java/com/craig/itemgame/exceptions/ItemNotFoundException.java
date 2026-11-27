package com.craig.itemgame.exceptions;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String characterName, String itemId) {
        super("Could not find item item id: " + itemId + " for character " + characterName);
    }
    
}
