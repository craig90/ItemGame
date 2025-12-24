import { useEffect, useState } from "react"
import { ItemSlot } from "./ItemSlot";
import type { Character } from "../models/Character";
import "./CharacterPage.css";
import { getCharacter } from "../service/api";
import { mapItemsToSlots } from "../utils/SlotMapper";


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
    const equippedItems = mapItemsToSlots(character.items);

    return (
        <div className="characterPage">
            <h2>Character</h2>
            <div className="gear-grid">
                <ItemSlot slotType="helmet" item={equippedItems.helmet}/>
                <ItemSlot slotType="amulet" item={equippedItems.amulet} />
                <ItemSlot slotType="weapon" item={equippedItems.weapon} />
                <ItemSlot slotType="chest" item={equippedItems.chest} />
                <ItemSlot slotType="shield" item={equippedItems.shield} />
                <ItemSlot slotType="gloves" item={equippedItems.gloves} />
                <ItemSlot slotType="ring-left"/>
                <ItemSlot slotType="belt" item={equippedItems.belt} />
                <ItemSlot slotType="ring-right" />
                <ItemSlot slotType="boots" item={equippedItems.boots}/>
                <ItemSlot slotType="pants" item={equippedItems.pants}/>
            </div>    
        </div>
    )
}
