import type { Item } from "../models/Item";
import "./ItemSlot.css";
import "./hover.css";
import { itemTypeToImage } from "../utils/ItemTypeToImage";
import type { SlotType } from "../models/SlotType";
import { hoverTip } from "./HoverTip";
import type { HoveredDropTarget } from "../models/HoveredDropTarget";
import { itemTypeMatchesSlotType } from "../utils/SlotMapper";
// import type { InventoryItem } from "../models/InventoryItem";

type ItemSlotProps = {
    slotType: string;
    item?: Item;
    itemSlotDrop: (itemId: string, slotType: SlotType) => void;
    itemsById: Record<string, Item>
    equippedItems: Partial<Record<SlotType, string>>

    draggedItem: {
        itemId: string;
    } | null;

    setDraggedItem: React.Dispatch<
        React.SetStateAction<{
            itemId: string;
        } | null>
    >;

    hoveredDropTarget: HoveredDropTarget|null;
    setHoveredDropTarget: React.Dispatch<HoveredDropTarget|null>

}

export function ItemSlot({slotType, item, draggedItem, equippedItems, itemsById, 
        setDraggedItem, itemSlotDrop, hoveredDropTarget, setHoveredDropTarget
    }: ItemSlotProps) {
    
    const handleMouseUp = () => {
        
        // console.log(draggedItem?.itemId);
        if (!draggedItem) return;
        itemSlotDrop(draggedItem.itemId, slotType as SlotType);

    }
    
    
    return (
        
        <div className={`item-slot item-slot--${slotType} ${item ? "slot--occupied" : ""}
            ${hoveredDropTarget?.slot===slotType && draggedItem && !item 
                && itemTypeMatchesSlotType(itemsById[draggedItem.itemId].type, slotType)? 
                "slot-highlight" : ""}`} onMouseUp={handleMouseUp}
            onMouseEnter={()=>
                setHoveredDropTarget({
                    type: "equipment",
                    slot: slotType as SlotType
                })
            }
            onMouseLeave={()=>
                setHoveredDropTarget(null)
            }
            >
            <div className="slot-type">{slotType.startsWith("ring") ? "ring" : slotType}</div>
            {item && (
                <div 
                    className="item-container" 
                    style={{opacity: draggedItem?.itemId === item.id ? 0.3 : 1}}
                >
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

                    {hoverTip(item, equippedItems, itemsById, slotType as SlotType)}
                </div>

                )
            }
            
        </div>
    );
}