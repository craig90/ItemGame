import { ITEM_TYPES, type Item } from "../models/Item";
import "./ItemSlot.css";
import "./hover.css";
import { itemTypeToImage } from "../utils/ItemTypeToImage";
import { SLOT_TYPES, type SlotType } from "../models/SlotType";
import type { InventoryItem } from "../models/InventoryItem";

type ItemSlotProps = {
    slotType: string;
    item?: Item;
    itemsById: Record<string, Item>;
    equippedItems: Partial<Record<SlotType, string>>;

    setEquippedItems: React.Dispatch<
        React.SetStateAction<
            Partial<Record<SlotType, string>>
        >
    >;

    draggedItem: {
        itemId: string;
    } | null;

    setDraggedItem: React.Dispatch<
        React.SetStateAction<{
            itemId: string;
        } | null>
    >;

    setInventoryItems: React.Dispatch<
        React.SetStateAction<InventoryItem[]
        >
    >;
}

export function ItemSlot({slotType, item, itemsById, equippedItems, setEquippedItems, 
    setInventoryItems, draggedItem, setDraggedItem}: ItemSlotProps) {
    
    const handleMouseUp = () => {
        
        // console.log(draggedItem?.itemId);

        if (!draggedItem) return;

        const movedItem = itemsById[draggedItem?.itemId];

        if (!movedItem) return;

        // check if the slot is already occupied
        if (equippedItems[slotType as SlotType] != null) {
            // console.log("Occupied");
            return;
        }

        console.log(movedItem.type, slotType);

        // check if it's correct type for slot
        if (movedItem.type === slotType.toUpperCase()) {
            // console.log("Type matches");
        } else if (movedItem.type === ITEM_TYPES.RING && (slotType === SLOT_TYPES.RING_LEFT || slotType === SLOT_TYPES.RING_RIGHT)) {
            // console.log("Type matches");
        } else if (slotType === SLOT_TYPES.DELETE_ITEM) {
            // console.log("Type matches");
        } else {
            // console.log("Type doesn't match");
            return;
        }

        setEquippedItems(prevEquipped => {

            const updated = { ...prevEquipped };

            // case 1 - check if it's already equipped - slot to slot
            for (const slot in updated) {
                if (updated[slot as SlotType] === movedItem.id) {
                    // console.log("Currently equipped");
                    delete updated[slot as SlotType];
                }
            }

            // case 2 - check if it's in the inventory - inv to slot
            setInventoryItems(prev => {

                return prev.filter(
                    item => item.id !== movedItem.id
                );
            
            });

            updated[slotType as SlotType] = movedItem.id;
            
            return updated;

        });
        
    
    }
    
    
    return (
        
        <div className={`item-slot item-slot--${slotType} ${item ? "slot--occupied" : ""}`} onMouseUp={handleMouseUp}>
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