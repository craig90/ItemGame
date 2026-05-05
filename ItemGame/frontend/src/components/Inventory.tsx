import "./Inventory.css";
import type { InventoryItem } from "../models/InventoryItem";
import type { Item } from "../models/Item";
import { itemTypeToImage } from "../utils/ItemTypeToImage";

type InventoryProps = {
    itemsById: Record<string, Item>
    inventoryItems: InventoryItem[];
    cols: number;
    rows: number;
}

export function Inventory ({itemsById, inventoryItems, cols, rows}: InventoryProps) {

    // const cols = 10;
    // const rows = 5;

    return (

        <div className="inventory">
            <div
                className="grid"
                style={{ 
                    gridTemplateColumns: `repeat(${cols}, 50px)`,
                    gridTemplateRows: `repeat(${rows}), 50px)`,
                }}
            >
            {Array.from({ length: cols*rows }).map((_, i) => (
                <div key={i} className="cell" />
            ))}
            </div>
            
            <div className="items">
                {inventoryItems.map((invItem) => {
                    const item = itemsById[invItem.id];
                    if (!item) return null;

                    return (
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
                        >
                            <img src={`${itemTypeToImage[item.type]}`} alt="" />
                        </div> 
                    );

                })}
            
            </div>

        </div>

    )

}