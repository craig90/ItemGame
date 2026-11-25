import type { Character } from "../models/Character";
import type { CharacterSummary } from "../models/CharacterSummary";
import type { Item } from "../models/Item";

const API_URL = "http://localhost:8080";

export const createCharacter = async(characterName:string): Promise<Character> => {

    const response = await fetch(`${API_URL}/create-character/${characterName}`, {
        method: 'POST',
    });

    return handleResponse(response);

}

export const getCharacter = async (characterId:string): Promise<Character> => {
    
    const response = await fetch(`${API_URL}/character/${characterId}`);
    
    return handleResponse(response);
    
}

export const getCharacters = async(): Promise<CharacterSummary[]> => {

    const response = await fetch(`${API_URL}/characters`);

    return handleResponse(response);
    
}

// new item
export const generateItem = async(characterId: string): Promise<Item> => {

    const response = await fetch(`${API_URL}/character/${characterId}/items/generate`, {
        method: 'POST',
    });

    return handleResponse(response);

}

// delete Item
export const deleteItem = async(characterId:string, itemId:string): Promise<Character> => {

    const response = await fetch(`${API_URL}/character/${characterId}/items/${itemId}`, {
        method: 'DELETE',
    });

    return handleResponse(response);

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

    let data;

    try {
        data = await response.json();
    } catch {
        
        throw {
            errorCode: "UNKOWN_ERROR",
            message: "Unknown server error"
        };

    }

    if (!response.ok) {
        throw data;
    }
    
    return data;

}
