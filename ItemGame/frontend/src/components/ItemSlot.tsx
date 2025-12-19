import type { Item } from "../models/Item";
import "./ItemSlot.css";

type ItemSlotProps = {
    slotType: string;
    item?: Item;
}

export function ItemSlot({slotType, item}: ItemSlotProps) {
    return (
        // 
        
        <div className={`item-slot item-slot--${slotType}`}>
            {slotType}
            {item ? (
                <>
                    <div className="item-name">{item.name}</div>
                    {item.stats.map((stat) => (
                        <p className="stat">{stat.type}: {stat.value}</p>
                    ))}
                </>
            ) : (
                <div>Not Equipped</div>
            )
            
            }
            
        </div>
    );
}