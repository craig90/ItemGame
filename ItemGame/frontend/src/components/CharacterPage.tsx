import { useEffect, useState } from "react"
import { ItemSlot } from "./ItemSlot";
import type { Character } from "../models/Character";
import type { InventoryItem } from "../models/InventoryItem";
import type { SlotType } from "../models/SlotType";
import "./CharacterPage.css";
import { getCharacter } from "../service/api";
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
                        <ItemSlot slotType="helm" item={helmId !== undefined ? character.itemsById[helmId]: undefined} 
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="amulet" item={amuletId !== undefined ? character.itemsById[amuletId]: undefined}
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="weapon" item={weaponId !== undefined ? character.itemsById[weaponId]: undefined}
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="chest" item={chestId !== undefined ? character.itemsById[chestId]: undefined}
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="shield" item={shieldId !== undefined ? character.itemsById[shieldId]: undefined}
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="gloves" item={glovesId !== undefined ? character.itemsById[glovesId]: undefined}
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="ring-right" item={ringRightId !== undefined ? character.itemsById[ringRightId]: undefined}
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="belt" item={beltId !== undefined ? character.itemsById[beltId]: undefined}
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="ring-left" item={ringLeftId !== undefined ? character.itemsById[ringLeftId]: undefined}
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        <ItemSlot slotType="boots"  item={bootsId !== undefined ? character.itemsById[bootsId]: undefined} 
                        itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                        setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}/>
                        {/* <ItemSlot slotType="pants" item={pantsId !== undefined ? character.itemsById[pantsId]: undefined}/> */}
                    </div>
                        <Inventory cols={10} rows={5} itemsById={character.itemsById} 
                        inventoryItems={inventoryItems} setInventoryItems={setInventoryItems}
                        draggedItem={draggedItem} setEquippedItems={setEquippedItems} setDraggedItem={setDraggedItem}></Inventory>
                </div>

                <div className="item-manipulation-grid">
                    <ItemSlot slotType ="new-item" item={newItemId !== undefined ? character.itemsById[newItemId]: undefined}
                    itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                    setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}
                    ></ItemSlot>
                    <ItemSlot slotType ="delete-item" item={deleteItemId !== undefined ? character.itemsById[deleteItemId]: undefined}
                    itemsById={character.itemsById} equippedItems={equippedItems} setEquippedItems={setEquippedItems}
                    setInventoryItems={setInventoryItems} draggedItem={draggedItem} setDraggedItem={setDraggedItem}
                    ></ItemSlot>
                    <button>Generate Item</button>
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
