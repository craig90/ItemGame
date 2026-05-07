import type { Item } from "../models/Item";
import "./ItemSlot.css";
import "./hover.css";
import { itemTypeToImage } from "../utils/ItemTypeToImage";

type ItemSlotProps = {
    slotType: string;
    item?: Item;
}

export function ItemSlot({slotType, item}: ItemSlotProps) {
    return (
        // 
        
        <div className={`item-slot item-slot--${slotType}`}>
            <div className="slot-type">{slotType.startsWith("ring") ? "ring" : slotType}</div>
            {item && (
                <>
                <img 
                    src={`${itemTypeToImage[item.type]}`}
                    alt=""
                    className={`item-slot--${slotType} gear-image`}
                />
                <span className="item-hover"><div className="item-name">{item.name}</div>
                    {item.stats.map((stat) => (
                        <p key={stat.type} className="stat">{stat.type}: {stat.value}
                        </p>
                    ))}
                </span>
                </>
                )
            }
            
        </div>
    );
}