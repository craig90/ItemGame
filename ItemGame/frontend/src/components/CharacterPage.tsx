import { useEffect, useState } from "react"
import { ItemSlot } from "./ItemSlot";
import type { Character } from "../models/Character";
import type { InventoryItem } from "../models/InventoryItem";
import type { Item } from "../models/Item";
import { SLOT_TYPES, type SlotType } from "../models/SlotType";
import "./CharacterPage.css";
import { getCharacter, generateItem } from "../service/api";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { Inventory } from "./Inventory";
// import { EquippedItems } from "../utils/SlotMapper";


export function CharacterPage() {
    
    const { id } = useParams();
    const navigate = useNavigate();
    const [character, setCharacter] = useState<Character | null>(null);
    const [loading, setLoading] = useState(true);

    const [inventoryItems, setInventoryItems] = useState<InventoryItem[]>([]);
    const [equippedItems, setEquippedItems] = useState<Partial<Record<SlotType, string>>>({});
    const [itemsById, setItemsById] = useState<Record<string, Item>>({});
    
    useEffect(() => {

        if (character) {
            setEquippedItems(character.equippedItems);
        }
    }, [character]);

    useEffect(() => {

        if (character) {
            setInventoryItems(character.inventoryItems);
        }
    }, [character]);

    useEffect(() => {

        if (character) {
            setItemsById(character.itemsById);
        }

    }, [character]);

    // resets dragged item to null after mouseup
    useEffect(() => {
        
        const handleMouseUp = () => {
            setDraggedItem(null);
        };

        window.addEventListener("mouseup", handleMouseUp);

        return () => {
            window.removeEventListener("mouseup", handleMouseUp);
        }

    }, [])
    
    const [draggedItem, setDraggedItem] = useState<{
        itemId: string;
    } | null>(null);


    useEffect(() => {
        async function loadCharacter() {
            const data = await getCharacter(String(id));
            console.log(data);
            setCharacter(data);
            setLoading(false);
        }
        loadCharacter();
    }, [id]);
    
    if (loading) {
        return <div>Loading character...</div>;
    }

    if (!character) {
        return <div>No character found</div>;
    }

    const helmId = equippedItems.helm;
    const amuletId = equippedItems.amulet;
    const weaponId = equippedItems.weapon;
    const chestId = equippedItems.chest;
    const shieldId = equippedItems.shield;
    const glovesId = equippedItems.gloves;
    const ringRightId = equippedItems["ring-right"];
    const beltId = equippedItems.belt;
    const ringLeftId = equippedItems["ring-left"];
    const bootsId = equippedItems.boots;
    // const pantsId = equippedItems.pants;
    const newItemId = equippedItems["new-item"];
    const deleteItemId = equippedItems["delete-item"];

    const handleGenerateItem = async () => {

        try {
            
            const newItem = await generateItem();
            console.log(newItem);
            const oldGeneratedItemId = equippedItems[SLOT_TYPES.NEW_ITEM];

            // update the items by id
            setItemsById(prevItemsById => {
                
                const updated = {...prevItemsById}
                
                // remove the old generated item if it exists
                if (oldGeneratedItemId) {
                    // console.log("Deleted", updated[oldGeneratedItemId].name);
                    delete updated[oldGeneratedItemId];
                    
                }
                
                updated[newItem.id] = newItem;
                // console.log(updated);

                return updated;
            });
            
            // update the equipped items
            setEquippedItems(prevEquipped => ({
                ...prevEquipped,
                [SLOT_TYPES.NEW_ITEM]: newItem.id
            }));

            console.log(equippedItems[SLOT_TYPES.NEW_ITEM]);
            console.log(equippedItems);
            console.log(itemsById);

        } catch (err) {
            console.error(err);
        }

    };

    return (
        
        <div className="character-page">
            <div className="character-layout">
                <div className="character-stats">
                    <div className="stat-row">{character.name}</div>
                    <div className="stat-row">Level: {character.level}</div>
                    <div className="stat-row">Experience: {character.experience}</div>
                    <div className="stat-row">Damage: {character.damage}</div>
                    <div className="stat-row">Health: {character.health}</div>
                    <div className="stat-row">Defense: {character.defence}</div>
                    <div className="stat-row">Cold Resistence: {character.coldResistance}</div>
                    <div className="stat-row">Fire Resistence: {character.fireResistance}</div>
                    <div className="stat-row">Lightning Resistence: {character.lightningResistance}</div>
                    <div className="stat-row">Magic Resistence: {character.magicResistance}</div>
                    <div className="stat-row">Poison Resistence: {character.poisonResistance}</div>
                </div>
                
                <div>
                    <div className="gear-grid">
                        {/* <h3>Equipment</h3> */}
                        <ItemSlot slotType="helm" item={helmId !== undefined ? itemsById[helmId]: undefined} 
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="amulet" item={amuletId !== undefined ? itemsById[amuletId]: undefined}
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="weapon" item={weaponId !== undefined ? itemsById[weaponId]: undefined}
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="chest" item={chestId !== undefined ? itemsById[chestId]: undefined}
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="shield" item={shieldId !== undefined ? itemsById[shieldId]: undefined}
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="gloves" item={glovesId !== undefined ? itemsById[glovesId]: undefined}
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="ring-right" item={ringRightId !== undefined ? itemsById[ringRightId]: undefined}
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="belt" item={beltId !== undefined ? itemsById[beltId]: undefined}
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="ring-left" item={ringLeftId !== undefined ? itemsById[ringLeftId]: undefined}
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="boots"  item={bootsId !== undefined ? itemsById[bootsId]: undefined} 
                        itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        {/* <ItemSlot slotType="pants" item={pantsId !== undefined ? character.itemsById[pantsId]: undefined}/> */}
                    </div>
                        <Inventory cols={10} rows={5} itemsById={itemsById} 
                        inventoryItems={inventoryItems} setInventoryItems={setInventoryItems}
                        draggedItem={draggedItem} setEquippedItems={setEquippedItems} setDraggedItem={setDraggedItem}></Inventory>
                </div>

                <div className="item-manipulation-grid">
                    <ItemSlot slotType ="new-item" item={newItemId !== undefined ? itemsById[newItemId]: undefined}
                    itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                    setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}
                    ></ItemSlot>
                    <ItemSlot slotType ="delete-item" item={deleteItemId !== undefined ? itemsById[deleteItemId]: undefined}
                    itemsById={itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                    setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}
                    ></ItemSlot>
                    <button onClick={handleGenerateItem}>Generate Item</button>
                    <button>Delete Item</button>
                </div>
            
                
            </div>
            <hr></hr>
            <div>
                <button onClick={() => navigate(`/`)}>
                    Character Selection Screen
                </button>
            </div>
            
        </div>
            
    )

}
