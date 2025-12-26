import type { Item } from "../models/Item";
import "./ItemSlot.css";
import { itemTypeToImage } from "../utils/itemTypeToImage";

type ItemSlotProps = {
    slotType: string;
    item?: Item;
}

export function ItemSlot({slotType, item}: ItemSlotProps) {
    return (
        // 
        
        <div className={`item-slot item-slot--${slotType}`}>
            <div className="slot-type">{slotType}</div>
            {item && (
                <>
                <img 
                    src={`/src${itemTypeToImage[item.type]}`}
                    alt=""
                    className={`item-slot--${slotType} gear-image`}
                />
                <span className="item-hover"><div className="item-name">{item.name}</div>
                    {item.stats.map((stat) => (
                        <p className="stat">{stat.type}: {stat.value}
                        </p>
                    ))}
                </span>
                </>
                )
            }
            
        </div>
    );
}