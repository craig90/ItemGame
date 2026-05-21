import type { Character } from "../models/Character";
import type { CharacterSummary } from "../models/CharacterSummary";
import type { Item } from "../models/Item";
// import { ITEM_TYPES } from "../models/Item";
// import { SLOT_TYPES } from "../models/SlotType";

// // get Character
// export const getCharacter = async (id: string): Promise<Character> => {
//     const response = await fetch(`api/character/${id}`); // might need to be modified
//     if (!response.ok) {
//         throw new Error(`HTTP error! Status: ${response.status}`);
//     }
//     return await response.json();

// }

const API_URL = "http://localhost:8080";

export const getCharacter = async (characterId:string): Promise<Character> => {
    
    const response = await fetch(`${API_URL}/character/${characterId}`);
    
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }
    
    return await response.json();

}

export const getCharacters = async(): Promise<CharacterSummary[]> => {

    const response = await fetch(`${API_URL}/characters`);

    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    return await response.json();

    
}

// new item
export const generateItem = async(): Promise<Item> => {

    const response = await fetch(`${API_URL}/items/generate`, {
        method: 'POST',
    });

    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    return await response.json();

}

// delete Item
export const deleteItem = async(characterId:string, itemId:string): Promise<Character> => {

    const response = await fetch(`${API_URL}/character/${characterId}/items/${itemId}`, {
        method: 'DELETE',
    });

    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    return await response.json();

}


// equip Item
export const equipItem = async(characterId:string, itemId:string, itemSlot:string): Promise<Character> => {

    const response = await fetch(`${API_URL}/character/${characterId}/equip/${itemId}/${itemSlot}`, {
        method: 'POST',
    });

    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    return await response.json();

}

// move Item
export const moveItem = async(characterId:string, itemId:string, x:number, y:number): Promise<Character> => {
    
    const response = await fetch(`${API_URL}/character/${characterId}/equip/${itemId},${x},${y}`, {
        method: 'POST',
    });

    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    return await response.json();

}


