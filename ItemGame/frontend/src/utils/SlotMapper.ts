import type { Item } from "../models/Item";
import type { SlotType } from "../models/SlotType";

export type EquippedItems = Partial<Record<SlotType, Item>>;

export function mapItemsToSlots(items: Item[]): EquippedItems {

    const equippedItems: EquippedItems = {}

    for (const item of items) {
        switch(item.type) {
            case "AMULET":
                equippedItems.amulet = item;
                break;
            case "BELT":
                equippedItems.belt = item;
                break;    
            case "BOOTS":
                equippedItems.boots = item;
                break;
            case "CHEST":
                equippedItems.chest = item;
                break;
            case "GLOVES":
                equippedItems.gloves = item;
                break;    
            case "HELMET":
                equippedItems.helmet = item;
                break;
            case "PANTS":
                equippedItems.pants = item;
                break;
            case "SHIELD":
                equippedItems.shield = item;
                break;
            case "WEAPON":
                equippedItems.weapon = item;
                break;
            case "RING": // TODO: come back to this because rings are a special case
                break;
        
        }
    }

    return equippedItems;

}
