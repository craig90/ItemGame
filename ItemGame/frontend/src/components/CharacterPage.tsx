import { useEffect, useState } from "react"
import { ItemSlot } from "./ItemSlot";
import type { Character } from "../models/Character";
import type { InventoryItem } from "../models/InventoryItem";
import { ITEM_TYPES, type Item } from "../models/Item";
import { SLOT_TYPES, type SlotType } from "../models/SlotType";
import "./CharacterPage.css";
import { getCharacter, generateItem, deleteItem, equipItem } from "../service/api";
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

    const handleDeleteItem = async () => {

        if (deleteItemId) {
            const updatedCharacter = await deleteItem(character.id, deleteItemId);
            setItemsById(updatedCharacter.itemsById);
            setEquippedItems(updatedCharacter.equippedItems);
        }
    }

    const itemSlotDrop = async (itemId:string, slotType:string) => {
    
        const movedItem = itemsById[itemId];

        if (!movedItem) return;

        // check if the slot is already occupied
        if (equippedItems[slotType as SlotType] != null) {
            // console.log("Occupied");
            return;
        }

        // console.log(movedItem.type, slotType);

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

        // console.log(movedItem.id, slotType);
        const updatedCharacter = await equipItem(character.id, movedItem.id, slotType);
        // console.log(updatedCharacter);

        setItemsById(updatedCharacter.itemsById);
        setEquippedItems(updatedCharacter.equippedItems);
        setInventoryItems(updatedCharacter.inventoryItems);

    }

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
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        <ItemSlot slotType="amulet" item={amuletId !== undefined ? itemsById[amuletId]: undefined}
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        <ItemSlot slotType="weapon" item={weaponId !== undefined ? itemsById[weaponId]: undefined}
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        <ItemSlot slotType="chest" item={chestId !== undefined ? itemsById[chestId]: undefined}
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        <ItemSlot slotType="shield" item={shieldId !== undefined ? itemsById[shieldId]: undefined}
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        <ItemSlot slotType="gloves" item={glovesId !== undefined ? itemsById[glovesId]: undefined}
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        <ItemSlot slotType="ring-right" item={ringRightId !== undefined ? itemsById[ringRightId]: undefined}
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        <ItemSlot slotType="belt" item={beltId !== undefined ? itemsById[beltId]: undefined}
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        <ItemSlot slotType="ring-left" item={ringLeftId !== undefined ? itemsById[ringLeftId]: undefined}
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        <ItemSlot slotType="boots"  item={bootsId !== undefined ? itemsById[bootsId]: undefined} 
                        draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}/>
                        {/* <ItemSlot slotType="pants" item={pantsId !== undefined ? character.itemsById[pantsId]: undefined}/> */}
                    </div>
                        <Inventory cols={10} rows={5} itemsById={itemsById} 
                        inventoryItems={inventoryItems} setInventoryItems={setInventoryItems}
                        draggedItem={draggedItem} setEquippedItems={setEquippedItems} setDraggedItem={setDraggedItem}></Inventory>
                </div>

                <div className="item-manipulation-grid">
                    <ItemSlot slotType ="new-item" item={newItemId !== undefined ? itemsById[newItemId]: undefined}
                    draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}></ItemSlot>
                    <ItemSlot slotType ="delete-item" item={deleteItemId !== undefined ? itemsById[deleteItemId]: undefined}
                    draggedItem={draggedItem} setDraggedItem={setDraggedItem} itemSlotDrop={itemSlotDrop}></ItemSlot>
                    <button onClick={handleGenerateItem}>Generate Item</button>
                    <button onClick={handleDeleteItem}>Delete Item</button>
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

