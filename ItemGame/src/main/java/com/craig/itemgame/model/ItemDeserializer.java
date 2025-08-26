package com.craig.itemgame.model;

import java.lang.reflect.Type;

import com.craig.itemgame.model.Items.Amulet;
import com.craig.itemgame.model.Items.Belt;
import com.craig.itemgame.model.Items.Boots;
import com.craig.itemgame.model.Items.Chest;
import com.craig.itemgame.model.Items.Gloves;
import com.craig.itemgame.model.Items.Helm;
import com.craig.itemgame.model.Items.Pants;
import com.craig.itemgame.model.Items.Ring;
import com.craig.itemgame.model.Items.Shield;
import com.craig.itemgame.model.Items.Weapon;
import com.craig.itemgame.model.Items.Item;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ItemDeserializer implements JsonDeserializer<Item> {

    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Determine the concrete type based on JSON data (e.g., a "type" field)
        JsonObject jsonObject = json.getAsJsonObject();
        String itemType = jsonObject.get("itemType").getAsString();
        // jsonObject.getClass().getName();
    
        System.out.println(itemType);

        switch (itemType) {
            case "AMULET":
                return context.deserialize(jsonObject, Amulet.class);
            case "BELT":
                return context.deserialize(jsonObject, Belt.class);
            case "BOOTS":
                return context.deserialize(jsonObject, Boots.class);
            case "CHEST":
                return context.deserialize(jsonObject, Chest.class);
            case "GLOVES":
                return context.deserialize(jsonObject, Gloves.class);
            case "HELM":
                return context.deserialize(jsonObject, Helm.class);
            case "PANTS":
                return context.deserialize(jsonObject, Pants.class);
            case "RING":
                return context.deserialize(jsonObject, Ring.class);
            case "SHIELD":
                return context.deserialize(jsonObject, Shield.class);
            case "WEAPON":
                return context.deserialize(jsonObject, Weapon.class);
            default:
                throw new JsonParseException("Unknown type: " + itemType);
        }

        
        
    }

}
