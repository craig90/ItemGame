import "./Inventory.css";
import "./hover.css";
import type { InventoryItem } from "../models/InventoryItem";
import { ITEM_TYPES, type Item, type ItemType } from "../models/Item";
import type { SlotType } from "../models/SlotType";
import { itemTypeToImage } from "../utils/ItemTypeToImage";
import { statCompare } from "../utils/StatCompare";
import { useRef } from "react";

type InventoryProps = {
    itemsById: Record<string, Item>
    equippedItems: Partial<Record<SlotType, string>>
    inventoryItems: InventoryItem[];
    cols: number;
    rows: number;

    draggedItem: {
        itemId: string;
    } | null;

    setDraggedItem: React.Dispatch<
        React.SetStateAction<{
            itemId: string;
        } | null>
    >;

    inventoryDrop: (item: InventoryItem, x:number, y:number, itemId: string) => void;
}




export function Inventory ({itemsById, inventoryItems, equippedItems, cols, rows, draggedItem, setDraggedItem, inventoryDrop}: InventoryProps) {

    const occupiedCells = new Set();

    // window.addEventListener("mouseup", (e) => {
    //     // console.log("GLOBAL UP:", e.target);
    // });

    inventoryItems.forEach(item=> {
        for (let dx = 0; dx < item.width; dx++) {
            for (let dy = 0; dy < item.height; dy++) {
                occupiedCells.add(`${item.x + dx},${item.y + dy}`)
            }
        }
    })


    const gridRef = useRef<HTMLDivElement>(null);
    const CELL_SIZE = 52; // 50 + 2 for border


    function canOccupy(x: number, y: number, invItem: InventoryItem) {
        
        console.log(x, y, invItem.height, invItem.width);

        // item detection
        const occupyingItem = inventoryItems.find(
            (item) => (
                    item.id !== invItem.id && (
                    (x >= item.x && x <= (item.x+item.width-1)) && 
                      (y >= item.y && y <= (item.y+item.height-1))
                    || ((x <= item.x && x + (invItem.width-1) >= item.x) && 
                      (y >= item.y && y <= (item.y+item.height-1)))
                   || ((x >= item.x && x <= (item.x+item.width-1)) && 
                      (y <= item.y && y + (invItem.height-1) >= item.y))
                   || ((x <= item.x && x + (invItem.width-1) >= item.x) && 
                      (y <= item.y && y + (invItem.height-1) >= item.y))
                    ))
        );

        if (occupyingItem) {
            // if item id is different don't allow move - if it's the same allow move
            if (occupyingItem.id !== invItem.id) {
                console.log("Occupied by item", itemsById[occupyingItem.id].name);
                return false;
            }
            // this shouldn't happen since it's excluded in the .find above
            console.log("Same item", itemsById[occupyingItem.id].name);
        }

        return true;

    }

    function invDimensions(type: ItemType, invItem: InventoryItem) {

        console.log(type);

        switch (type) {
            case ITEM_TYPES.AMULET:
                invItem.height = 1;
                invItem.width = 1;
                break;
            case ITEM_TYPES.BELT:
                invItem.height = 1;
                invItem.width = 2;
                break;
            case ITEM_TYPES.BOOTS:
                invItem.height = 2;
                invItem.width = 2;
                break;
            case ITEM_TYPES.CHEST:
                invItem.height = 3;
                invItem.width = 2;
                break;
            case ITEM_TYPES.GLOVES:
                invItem.height = 2;
                invItem.width = 2;
                break;
            case ITEM_TYPES.HELM:
                invItem.height = 2;
                invItem.width = 2;
                break;
            case ITEM_TYPES.PANTS:
                invItem.height = 3;
                invItem.width = 2;
                break;
            case ITEM_TYPES.RING:
                invItem.height = 1;
                invItem.width = 1;
                break;
            case ITEM_TYPES.SHIELD:
                invItem.height = 3;
                invItem.width = 2;
                break;
            case ITEM_TYPES.WEAPON:
                invItem.height = 3;
                invItem.width = 2;
                break;
        
        }

    }

    function inBounds(x: number, y: number, invItem: InventoryItem) {

        // bounds detection - right
        if (x + invItem.width > cols) {
            console.log("Out of bounds.. too far right");
            return false;
        }

        // bounds detection - left
        if (y + invItem.height > rows) {
            console.log("Out of bounds.. too low");
            return false;
        }

        return true;

    }


    const handleMouseUp = (e: React.MouseEvent) => {
        
        if (!gridRef.current || !draggedItem) return;

        console.log(draggedItem.itemId);

        // itemsById[draggedItem.itemId]

        let item: InventoryItem;

        const invItem = inventoryItems.find(
            (item) => item.id === draggedItem?.itemId
        );

        const equippedItem = itemsById[draggedItem.itemId];

        if (invItem) {
            item = invItem;
        } else if (equippedItem) {
            
            item = {
                id: equippedItem.id,
                width: 0,
                height: 0,
                x: 0,
                y: 0,
            }
            
            invDimensions(equippedItem.type, item);
            console.log(item);
        } else {
            return;
        }

        const rect = gridRef.current.getBoundingClientRect();
        // console.log(e.clientX, rect.left);
        // console.log(e.clientY, rect.top);

        const x = Math.floor((e.clientX - rect.left) / CELL_SIZE);
        const y = Math.floor((e.clientY - rect.top) / CELL_SIZE);

        // const key = (y * cols) + x;
        if (!canOccupy(x, y, item)) return;
        if (!inBounds(x, y, item)) return;

        // if (!draggedItem) return;

        inventoryDrop(item, x, y, draggedItem.itemId);

        // setInventoryItems(prev => {

        //     const existingItem = prev.find(
        //         item => item.id === draggedItem.itemId
        //     );

        //     if (existingItem) {
                
        //         return prev.map(item => {

        //             if (item.id !== draggedItem.itemId) {
        //                 return item;
        //             }

        //             return {
        //                 ...item,
        //                 x: x,
        //                 y: y
        //             };

        //         });
        //     }

        //     setEquippedItems(prevEquipped => {

        //         const updated = { ...prevEquipped };

        //         for (const slot in updated) {
        //             // console.log("Looping through slots");
        //             if (updated[slot as SlotType] === draggedItem.itemId) {
        //                 delete updated[slot as SlotType];
        //                 // console.log("Found dragged item with slot", slot);
        //                 break;
        //             }
        //         }
                
        //         // console.log(updated.size);

        //         return updated;

        //     });

        //     return [
        //         ...prev,
        //         {
        //             id: draggedItem.itemId,
        //             x,
        //             y,
        //             width:item.width,
        //             height:item.height
        //         }
        //     ]

        // });


    }


    return (

        <div className="inventory" onMouseUp={handleMouseUp}>
            <div ref={gridRef}
                className="grid"
                style={{ 
                    gridTemplateColumns: `repeat(${cols}, 50px)`,
                    gridTemplateRows: `repeat(${rows}, 50px)`,
                }}
            >
            {Array.from({ length: cols*rows }).map((_, i) => {

                const x = i % cols;
                const y = Math.floor(i/cols);

                // console.log(x, y);
                const occupied = occupiedCells.has(`${x},${y}`);
                // console.log(occupied);

                return (
                    <div key={i} className={`cell ${occupied ? "cell--occupied" : ""}`}/>
                );
            })}
            </div>
            
            <div className="items">
                {inventoryItems.map((invItem) => {
                    const item = itemsById[invItem.id];
                    if (!item) return null;

                    return (
                        <div className="item-container" key={invItem.id}>
                            <div
                                className="item"
                                style={
                                    {
                                        "--x": invItem.x,
                                        "--y": invItem.y,
                                        "--w": invItem.width,
                                        "--h": invItem.height,
                                    } as React.CSSProperties
                                }
                                onMouseDown={() => {
                                    setDraggedItem({itemId: invItem.id});
                                }}
                            >
                                <img className="item-image" src={`${itemTypeToImage[item.type]}`} alt="" draggable={false} onDragStart={(e) => e.preventDefault()}/>
                                <span className={`item-hover item--${item.type}`}><div className="item-name">{item.name}</div>
                                    {item.stats.map((stat) => (
                                    <p key={stat.type} className="stat">
                                        {stat.type}: {stat.value}
                                        {statCompare(equippedItems, itemsById, item, stat)}
                                    </p>
                                    ))}
                                </span>
                            </div>
                        </div>
                    );

                })}
            
            </div>

        </div>

    )

}