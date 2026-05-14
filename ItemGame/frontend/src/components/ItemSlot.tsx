import type { Item } from "../models/Item";
import "./ItemSlot.css";
import "./hover.css";
import { itemTypeToImage } from "../utils/ItemTypeToImage";

type ItemSlotProps = {
    slotType: string;
    item?: Item;

    draggedItem: {
        itemId: string;
    } | null;

    setDraggedItem: React.Dispatch<
        React.SetStateAction<{
            itemId: string;
        } | null>
    >;
}

export function ItemSlot({slotType, item, draggedItem, setDraggedItem}: ItemSlotProps) {
    return (
        // 
        
        <div className={`item-slot item-slot--${slotType} ${item ? "slot--occupied" : ""}`}>
            <div className="slot-type">{slotType.startsWith("ring") ? "ring" : slotType}</div>
            {item && (
                <div className="item-container">
                    <img 
                        src={`${itemTypeToImage[item.type]}`}
                        alt=""
                        className={`item-slot--${slotType} gear-image`}
                        onMouseDown={() => {
                            setDraggedItem({itemId: item.id});
                        }}
                        draggable={false}
                        onDragStart={(e) => e.preventDefault()}
                    />
                    <span className="item-hover"><div className="item-name">{item.name}</div>
                        {item.stats.map((stat) => (
                            <p key={stat.type} className="stat">{stat.type}: {stat.value}
                            </p>
                        ))}
                    </span>
                </div>

                )
            }
            
        </div>
    );
}