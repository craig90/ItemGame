import type { Item } from "../models/Item";
import type { SlotType } from "../models/SlotType";
import { statCompare } from "../utils/StatCompare";
import { missingStats } from "../utils/StatCompare";
import "./hover.css";


export function hoverTip(item: Item, equippedItems: Partial<Record<SlotType, string>>, itemsById: Record<string, Item>) {

    return (
        <span className={`item-hover item--${item.type}`}><div className="item-name">{item.name}</div>
            {item.stats.map((stat) => (
            <>
                <p key={stat.type} className="stat">
                    {stat.type}: {stat.value}
                    {statCompare(equippedItems, itemsById, item, stat)}
                </p>
            </>
            ))}
            {missingStats(equippedItems, itemsById, item)}
        </span>
    );
}