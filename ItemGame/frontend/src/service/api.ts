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
export const generateItem = async(characterId: string): Promise<Item> => {

    const response = await fetch(`${API_URL}/character/${characterId}/items/generate`, {
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

    return handleResponse(response);

}

// move Item
export const inventoryMove = async(characterId:string, itemId:string, x:number, y:number): Promise<Character> => {
    
    const response = await fetch(`${API_URL}/character/${characterId}/inventory/${itemId}/${x}/${y}`, {
        method: 'POST',
    });

    return handleResponse(response);

}

/**
 * helper function for error response handling
 * @param response 
 */
export const handleResponse = async(response: Response) => {

    let errorData;

    try {
        errorData = await response.json();
    } catch {
        
        throw {
            errorCode: "UNKOWN_ERROR",
            message: "Unknown server error"
        };

    }
    throw errorData;

}
