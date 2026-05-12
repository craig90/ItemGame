import "./Inventory.css";
import "./hover.css";
import type { InventoryItem } from "../models/InventoryItem";
import type { Item } from "../models/Item";
import { itemTypeToImage } from "../utils/ItemTypeToImage";
import { useRef } from "react";

type InventoryProps = {
    itemsById: Record<string, Item>
    inventoryItems: InventoryItem[];
    cols: number;
    rows: number;

    draggedItem: {
        itemId: string;
        x: number;
        y: number;
    } | null;

    setDraggedItem: React.Dispatch<
        React.SetStateAction<{
            itemId: string;
            x: number;
            y: number;
        } | null>
    >;

    setInventoryItems: React.Dispatch<
        React.SetStateAction<InventoryItem[]
        >
    >;
}




export function Inventory ({itemsById, inventoryItems, cols, rows, draggedItem, setDraggedItem, setInventoryItems}: InventoryProps) {

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
    const CELL_SIZE = 50;


    const handleMouseUp = (e: React.MouseEvent) => {
        
        if (!gridRef.current || !draggedItem) return;

        console.log(draggedItem.itemId);
        
        itemsById[draggedItem.itemId]

        const item = inventoryItems.find(
            (item) => item.id === draggedItem?.itemId
        );
        
        if (!item) return;

        const rect = gridRef.current.getBoundingClientRect();
        console.log(e.clientX, rect.left);
        console.log(e.clientY, rect.top);

        const x = Math.floor((e.clientX - rect.left) / CELL_SIZE);
        const y = Math.floor((e.clientY - rect.top) / CELL_SIZE);

        const key = (y * cols) + x;

        // item detection
        const occupyingItem = inventoryItems.find(
            (item) => (x >= item.x && x <= (item.x+item.width-1)) && 
                      (y >= item.y && y <= (item.y+item.height-1))
        );

        if (occupyingItem) {
            // if item id is different don't allow move - if it's the same allow move
            if (occupyingItem.id !== item.id) {
                console.log("Occupied by item", itemsById[occupyingItem.id].name);
                return;
            }
            console.log("Same item", itemsById[occupyingItem.id].name);
        }

        // bounds detection - right
        if (x + item.width > cols) {
            console.log("Out of bounds.. too far right");
            return;
        }

        // bounds detection - left
        if (y + item.height > rows) {
            console.log("Out of bounds.. too low");
            return;
        }

        if (!draggedItem) return;

        setInventoryItems(prev => 
            prev.map(item => {

                if (item.id !== draggedItem.itemId) {
                    return item;
                }

                return {
                    ...item,
                    x: x,
                    y: y
                };

            })
        );

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
                        <div className="item-container">
                            <div
                                key={invItem.id}
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
                                    setDraggedItem({itemId: invItem.id, x: invItem.x, y: invItem.y});
                                }}
                            >
                                <img className="item-image" src={`${itemTypeToImage[item.type]}`} alt="" />
                                <span className={`item-hover item--${item.type}`}><div className="item-name">{item.name}</div>
                                    {item.stats.map((stat) => (
                                    <p key={stat.type} className="stat">{stat.type}: {stat.value}
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