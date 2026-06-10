import type { Item } from "../models/Item";
import "./ItemSlot.css";
import "./hover.css";
import { itemTypeToImage } from "../utils/ItemTypeToImage";
import type { SlotType } from "../models/SlotType";
// import type { InventoryItem } from "../models/InventoryItem";

type ItemSlotProps = {
    slotType: string;
    item?: Item;
    itemSlotDrop: (itemId: string, slotType: SlotType) => void;

    draggedItem: {
        itemId: string;
    } | null;

    setDraggedItem: React.Dispatch<
        React.SetStateAction<{
            itemId: string;
        } | null>
    >;

}

export function ItemSlot({slotType, item, draggedItem, setDraggedItem, itemSlotDrop}: ItemSlotProps) {
    
    const handleMouseUp = () => {
        
        // console.log(draggedItem?.itemId);
        if (!draggedItem) return;
        itemSlotDrop(draggedItem.itemId, slotType as SlotType);

    }
    
    
    return (
        
        <div className={`item-slot item-slot--${slotType} ${item ? "slot--occupied" : ""}`} onMouseUp={handleMouseUp}>
            <div className="slot-type">{slotType.startsWith("ring") ? "ring" : slotType}</div>
            {item && (
                <div className="item-container">
                    <img 
                        src={`${itemTypeToImage[item.type]}`}
                        alt=""
                        className={`item-image--${item.type.toLowerCase()} gear-image`}
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