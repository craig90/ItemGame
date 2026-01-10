package com.craig.itemgame.model;

import java.util.UUID;

public class IdGenerator {

    public static String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

}
