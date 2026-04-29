import type { Character } from "../models/Character";
import type { CharacterSummary } from "../models/CharacterSummary";
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

export const getCharacter = async (): Promise<Character> => {
    
    const response = await fetch(`${API_URL}/character/0b6469fe-5950-469c-a397-e4ee4b4740c3`); // TODO: make dynamic
    
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }
    
    return await response.json();

}

export const getCharacters = async(): Promise<CharacterSummary[]> => {

    // const response = await fetch(`${API_URL}/characters`);

    // if (!response.ok) {
    //     throw new Error(`HTTP error! Status: ${response.status}`);
    // }

    // return await response.json();

    return [
            { 
                id: "herp-a-derp",
                name: "Test Character 1",
                level: 69,
            },
            
            {
                id: "herp-a-derp-a-derp",
                name: "Test Character 2",
                level: 420,
            }
        ]
}

// update Item

// delete Item