package com.craig.itemgame.DummyDataSets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.craig.itemgame.DTOs.CharacterDTO;
import com.craig.itemgame.DTOs.ItemDTO;
import com.craig.itemgame.DTOs.StatDTO;

public class DummyCharacter {

    public DummyCharacter() {

    }

    public CharacterDTO generate() {

        CharacterDTO dummyCharacter = new CharacterDTO();
        dummyCharacter.setId("asdfsaf");
        dummyCharacter.setName("Craig the test character!!");
        dummyCharacter.setLevel(1);
        dummyCharacter.setExperience(100);

        ItemDTO itemOne = new ItemDTO();
        itemOne.setId("adfasdf");
        itemOne.setName("Cool Sword");
        itemOne.setType("WEAPON");

        StatDTO statOne = new StatDTO("Damage", 22);
        StatDTO statTwo = new StatDTO("All Resistence", 5);

        List<StatDTO> statList = new ArrayList<>();
        statList.add(statOne);
        statList.add(statTwo);

        itemOne.setStats(statList);

        ItemDTO itemTwo = new ItemDTO();
        itemTwo.setId("adfasdfaaa");
        itemTwo.setName("Amulet of Beauty");
        itemTwo.setType("AMULET");

        StatDTO itemTwoStatOne = new StatDTO("Health", 50);
        StatDTO itemTwoStatTwo = new StatDTO("Lightning Resistence", 15);

        List<StatDTO> statListTwo = new ArrayList<>();
        statListTwo.add(itemTwoStatOne);
        statListTwo.add(itemTwoStatTwo);

        itemTwo.setStats(statListTwo);

        Map<String, ItemDTO> itemMap = new HashMap<>();

        itemMap.put("weapon", itemOne);  // TODO: frontend wants lower case so this needs to be thought through a bit
        itemMap.put("amulet", itemTwo);

        dummyCharacter.setItems(itemMap);

        return dummyCharacter;

    }

}
