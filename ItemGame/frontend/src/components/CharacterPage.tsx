import { useEffect, useState } from "react"
import { ItemSlot } from "./ItemSlot";
import type { Character } from "../models/Character";
import "./CharacterPage.css";
import { getCharacter } from "../service/api";

// // Dummy data
// const weapon: Item = {
//     id: "asdfasdf",
//     name: "Cool Sword",
//     type: "WEAPON",
//     stats: [
//         { type: "Damage", value: 22 },
//         { type: "All Resistance", value: 5 }
//     ],
// }

// const shield: Item = {
//     id: "asdfiopi",
//     name: "Green Shield",
//     type: "SHIELD",
//     stats: [
//         { type: "Defense", value: 75 },
//         { type: "All Resistance", value: 10 }
//     ],
// }

// const pants: Item = {
//     id: "aeeredfdaf",
//     name: "Silver Pants",
//     type: "PANTS",
//     stats: [
//         { type: "Defense", value: 40 },
//         { type: "Fire Resistance", value: 5 }
//     ],
// }

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

    // TODO: add map or type or something for slot types
    
    return (
        <div className="characterPage">
            <h2>Character</h2>
            <div className="gear-grid">
                <ItemSlot slotType="helmet" />
                <ItemSlot slotType="amulet" />
                <ItemSlot slotType="weapon"/>
                <ItemSlot slotType="chest" />
                <ItemSlot slotType="shield"/>
                <ItemSlot slotType="gloves" />
                <ItemSlot slotType="ring-left" />
                <ItemSlot slotType="belt" />
                <ItemSlot slotType="ring-right" />
                <ItemSlot slotType="boots" />
                <ItemSlot slotType="pants"/>
            </div>    
        </div>
    )
}
