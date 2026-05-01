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

    // map items to slot type
    // const equippedItems = mapItemsToSlots(character.items);

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
                    <ItemSlot slotType="helm" item={character.items.helm}/>
                    <ItemSlot slotType="amulet" item={character.items.amulet} />
                    <ItemSlot slotType="weapon" item={character.items.weapon} />
                    <ItemSlot slotType="chest" item={character.items.chest} />
                    <ItemSlot slotType="shield" item={character.items.shield} />
                    <ItemSlot slotType="gloves" item={character.items.gloves} />
                    <ItemSlot slotType="ring-right" item={character.items["ring-right"]} />
                    <ItemSlot slotType="belt" item={character.items.belt} />
                    <ItemSlot slotType="ring-left" item={character.items["ring-left"]} />
                    <ItemSlot slotType="boots" item={character.items.boots}/>
                    {/* <ItemSlot slotType="pants" item={character.items.pants}/> */}
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
