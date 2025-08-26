package com.craig.itemgame.model.Items;

import java.util.UUID;

public class ItemIdGenerator {

    public static String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

}
