import type { Item } from "../models/Item";
import { SLOT_TYPES, type SlotType } from "../models/SlotType";
import { statCompare } from "../utils/StatCompare";
import { missingStats } from "../utils/StatCompare";
import "./hover.css";


export function hoverTip(item: Item, equippedItems: Partial<Record<SlotType, string>>, 
    itemsById: Record<string, Item>, slotType: SlotType) {

    return (
        <span className={`item-hover item--${item.type}`}><div className="item-name">{item.name}</div>
            {item.stats.map((stat) => (
            <>
                <p key={stat.type} className="stat">
                    {stat.type}: {stat.value}
                    {   // equipped items don't need the comparison
                        slotType === SLOT_TYPES.NEW_ITEM || slotType === SLOT_TYPES.DELETE_ITEM
                            ? statCompare(equippedItems, itemsById, item, stat)
                            : null
                    }
                </p>
            </>
            ))}
            { slotType === SLOT_TYPES.NEW_ITEM || slotType === SLOT_TYPES.DELETE_ITEM
                ? missingStats(equippedItems, itemsById, item)
                : null}
        </span>
    );
}