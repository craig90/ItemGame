package com.craig.itemgame.ui;

import com.craig.itemgame.model.CharacterStatNames;

public interface CharacterView {

    public void updateCharacterName(String name);
    public void updateCharacterStat(CharacterStatNames stat, double value);

}
