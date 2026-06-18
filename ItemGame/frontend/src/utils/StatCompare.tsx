import { ITEM_TYPES, type Item, type ItemType } from "../models/Item";
import type { Stat } from "../models/Stat";
import type { SlotType } from "../models/SlotType";
import { SLOT_TYPES } from "../models/SlotType";


export function itemToSlot(type: ItemType) {

    if (type === ITEM_TYPES.AMULET) {
        return SLOT_TYPES.AMULET;
    } else if (type === ITEM_TYPES.BELT) {
        return SLOT_TYPES.BELT;
    } else if (type === ITEM_TYPES.BOOTS) {
        return SLOT_TYPES.BOOTS;
    } else if (type === ITEM_TYPES.CHEST) {
        return SLOT_TYPES.CHEST;
    } else if (type === ITEM_TYPES.GLOVES) {
        return SLOT_TYPES.GLOVES;
    } else if (type === ITEM_TYPES.HELM) {
        return SLOT_TYPES.HELM;
    } else if (type === ITEM_TYPES.SHIELD) {
        return SLOT_TYPES.SHIELD;
    } else if (type === ITEM_TYPES.WEAPON) {
        return SLOT_TYPES.WEAPON;
    }

    // should never happen - but not sure what else to return here
    return SLOT_TYPES.RING_LEFT;

}

export function positiveStat(stat: Stat) {

    return (
        <span className="positive-hover-stat">
            &nbsp;(+{stat.value})
        </span>
    );

}


export function statCompare(equippedItems: Partial<Record<SlotType, string>>, 
    itemsByID: Record<string, Item> , item: Item, stat: Stat) {

    if (item.type == ITEM_TYPES.RING) {
        
        if ((equippedItems[SLOT_TYPES.RING_LEFT] == null || equippedItems[SLOT_TYPES.RING_RIGHT] == null)) {
            return positiveStat(stat);
        }
        return "";
        
    }
    
    const itemId = equippedItems[itemToSlot(item.type)];
        
    if (itemId == null) {
        return positiveStat(stat);
    }
    
    const compareItem = itemsByID[itemId];

    const compareStat = compareItem.stats.find(searchStat => searchStat.type === stat.type);

    if (compareStat == null) {
        return positiveStat(stat);
    }

    if (compareStat.value < stat.value) {
        return (
        <span className="positive-hover-stat">
            &nbsp;+({stat.value - compareStat.value})
        </span>
        );
    } else if (compareStat.value > stat.value) {
        return (
        <span className="negative-hover-stat">
             &nbsp;-({compareStat.value - stat.value})
        </span>
        );
    }

    return "";

}

export function missingStats(equippedItems: Partial<Record<SlotType, string>>, 
    itemsByID: Record<string, Item> , unequippedItem: Item) {

    if (unequippedItem.type == ITEM_TYPES.RING) {
        return "";
    }
    
    const itemId = equippedItems[itemToSlot(unequippedItem.type)];
        
    if (!itemId) {
        return "";
    }

    const equippedItem = itemsByID[itemId];

    const missingStats = equippedItem.stats.filter(
        equippedStat => 
            !unequippedItem.stats.some(
                itemStat => itemStat.type === equippedStat.type
            )
    )
    
    if (missingStats.length > 0) {
        return (
            <>
            <hr></hr>
            {missingStats.map(missingStat => 
                <div className="negative-hover-stat">
                     {missingStat.type}: -{missingStat.value}
                </div>
            )}
            </>
        )

    }

    return ""
}