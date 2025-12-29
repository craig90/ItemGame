import { useEffect, useState } from "react"
import { ItemSlot } from "./ItemSlot";
import type { Character } from "../models/Character";
import "./CharacterPage.css";
import { getCharacter } from "../service/api";

export function CharacterPage() {
    
    const [character, setCharacter] = useState<Character | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function loadCharacter() {
            const data = await getCharacter();
            setCharacter(data);
            setLoading(false);
        }
        loadCharacter();
    }, []);
    
    if (loading) {
        return <div>Loading character...</div>;
    }

    if (!character) {
        return <div>No character found</div>;
    }

    // map items to slot type
    // const equippedItems = mapItemsToSlots(character.items);

    return (
        <div className="characterPage">
            <h2>Character</h2>
            <div className="gear-grid">
                <ItemSlot slotType="helmet" item={character.items.helmet}/>
                <ItemSlot slotType="amulet" item={character.items.amulet} />
                <ItemSlot slotType="weapon" item={character.items.weapon} />
                <ItemSlot slotType="chest" item={character.items.chest} />
                <ItemSlot slotType="shield" item={character.items.shield} />
                <ItemSlot slotType="gloves" item={character.items.gloves} />
                <ItemSlot slotType="ring-left" item={character.items["ring-left"]} />
                <ItemSlot slotType="belt" item={character.items.belt} />
                <ItemSlot slotType="ring-right" item={character.items["ring-right"]} />
                <ItemSlot slotType="boots" item={character.items.boots}/>
                <ItemSlot slotType="pants" item={character.items.pants}/>
            </div>
            <div className="item-manipulation-grid">
                <ItemSlot slotType ="new-item" item={character.items["new-item"]}></ItemSlot>
                <ItemSlot slotType ="delete-item" item={character.items["delete-item"]}></ItemSlot>
                <button>Generate Item</button>
                <button>Delete Item</button>
            </div> 
        </div>
    )
}
