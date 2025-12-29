package com.craig.itemgame.model.Items;

import java.util.Random;

import com.craig.itemgame.model.ItemStat;
import com.craig.itemgame.model.ItemType;

public class Shield extends Item {

    public Shield (String itemName) {
        super(itemName, ItemType.SHIELD);
    }

    // public ItemType getItemType() {
        // return ItemType.SHIELD;
    // }

    public String getImageFile() {
        return Item.IMAGE_DIR + Item.SHIELD_FILE;
    }

    /**
     * Special case for shield where it has to have a defence stat
     */
    public void generateItem(Random random) {
        
        // System.out.println("Shield has to have defence");
        int statValue = random.nextInt(ItemStat.SHIELD_DEFENCE_MIN_VALUE, ItemStat.SHIELD_DEFENCE_MAX_VALUE);
        ItemStat stat = new ItemStat(ItemStat.DEFENCE_STAT_NAME, statValue);
        this.addStat(stat);
        
        super.generateItem(random);
    }

    @Override
    public int getWidthInGridUnits() {
        return ItemConstants.SHIELD_GRID_UNIT_WIDTH;
    }

    @Override
    public int getHeightInGridUnits() {
        return ItemConstants.SHIELD_GRID_UNIT_HEIGHT;
    }


}