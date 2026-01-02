package com.craig.itemgame.model.Items;

import java.util.Random;

import com.craig.itemgame.model.ItemStat;
import com.craig.itemgame.model.ItemType;

public class Weapon extends Item {

    public Weapon(String itemName) {
        super(itemName, ItemType.WEAPON);
    }

    @Override
    public String getImageKey() {
        return "weapon";
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.WEAPON_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.WEAPON_GRID_UNIT_HEIGHT;
    }

    /**
     * Weapon is a special case where it must have a damage stat
     * @param random
     */
    public void generateItem(Random random) {
        
        // System.out.println("Weapon has to have damage");
        int statValue = random.nextInt(ItemStat.WEAPON_DAMAGE_MIN_VALUE, ItemStat.WEAPON_DAMGE_MAX_VALUE);
        ItemStat stat = new ItemStat(ItemStat.DAMAGE_STAT_NAME, statValue);
        this.addStat(stat);
        
        super.generateItem(random);
    }

}
