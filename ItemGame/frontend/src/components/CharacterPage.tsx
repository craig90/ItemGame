import { ItemSlot } from "./ItemSlot";
import type { Item } from "../models/Item";

// Dummy data
const weapon: Item = {
    id: "asdfasdf",
    name: "Cool Sword",
    type: "WEAPON",
    stats: [
        { type: "Damage", value: 22 },
        { type: "All Resistance", value: 5 }
    ],
}

const shield: Item = {
    id: "asdfiopi",
    name: "Green Shield",
    type: "SHIELD",
    stats: [
        { type: "Defense", value: 75 },
        { type: "All Resistance", value: 10 }
    ],
}

const pants: Item = {
    id: "aeeredfdaf",
    name: "Silver Pants",
    type: "PANTS",
    stats: [
        { type: "Defense", value: 40 },
        { type: "Fire Resistance", value: 5 }
    ],
}

export function CharacterPage() {
    return (
        <div>
            <h2>Character</h2>
            <ItemSlot slotType="Helmet" />
            <ItemSlot slotType="Amulet" />
            <ItemSlot slotType="Weapon" item={weapon}/>
            <ItemSlot slotType="Armor" />
            <ItemSlot slotType="Shield" item={shield}/>
            <ItemSlot slotType="Gloves" />
            <ItemSlot slotType="Belt" />
            <ItemSlot slotType="Pants" item={pants} />
            <ItemSlot slotType="Boots" />
            <ItemSlot slotType="Left Ring" />
            <ItemSlot slotType="Right Ring" />
        </div>
    )
}
