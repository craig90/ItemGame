import { useEffect, useState } from "react"
import { ItemSlot } from "./ItemSlot";
import type { Character } from "../models/Character";
import "./CharacterPage.css";
import { getCharacter } from "../service/api";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";

export function CharacterPage() {
    
    const { id } = useParams();
    const navigate = useNavigate();
    const [character, setCharacter] = useState<Character | null>(null);
    const [loading, setLoading] = useState(true);

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

    const helmId = character.equippedItems.helm;
    const amuletId = character.equippedItems.amulet;
    const weaponId = character.equippedItems.weapon;
    const chestId = character.equippedItems.chest;
    const shieldId = character.equippedItems.shield;
    const glovesId = character.equippedItems.gloves;
    const ringRightId = character.equippedItems["ring-right"];
    const beltId = character.equippedItems.belt;
    const ringLeftId = character.equippedItems["ring-left"];
    const bootsId = character.equippedItems.boots;
    // const pantsId = character.equippedItems.pants;

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

                <div className="gear-grid">
                    {/* <h3>Equipment</h3> */}
                    <ItemSlot slotType="helm" item={helmId !== undefined ? character.itemsById[helmId]: undefined}/>
                    <ItemSlot slotType="amulet" item={amuletId !== undefined ? character.itemsById[amuletId]: undefined}/>
                    <ItemSlot slotType="weapon" item={weaponId !== undefined ? character.itemsById[weaponId]: undefined}/>
                    <ItemSlot slotType="chest" item={chestId !== undefined ? character.itemsById[chestId]: undefined}/>
                    <ItemSlot slotType="shield" item={shieldId !== undefined ? character.itemsById[shieldId]: undefined}/>
                    <ItemSlot slotType="gloves" item={glovesId !== undefined ? character.itemsById[glovesId]: undefined}/>
                    <ItemSlot slotType="ring-right" item={ringRightId !== undefined ? character.itemsById[ringRightId]: undefined}/>
                    <ItemSlot slotType="belt" item={beltId !== undefined ? character.itemsById[beltId]: undefined}/>
                    <ItemSlot slotType="ring-left" item={ringLeftId !== undefined ? character.itemsById[ringLeftId]: undefined}/>
                    <ItemSlot slotType="boots" item={bootsId !== undefined ? character.itemsById[bootsId]: undefined}/>
                    {/* <ItemSlot slotType="pants" item={pantsId !== undefined ? character.itemsById[pantsId]: undefined}/> */}
                </div>

                {/* <div className="item-manipulation-grid">
                    <ItemSlot slotType ="new-item" item={character.items["new-item"]}></ItemSlot>
                    <ItemSlot slotType ="delete-item" item={character.items["delete-item"]}></ItemSlot>
                    <button>Generate Item</button>
                    <button>Delete Item</button>
                </div> */}
            
                
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
