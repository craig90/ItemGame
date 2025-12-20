import type { Character } from "../models/Character";

// // get Character
// export const getCharacter = async (id: string): Promise<Character> => {
//     const response = await fetch(`api/character/${id}`); // might need to be modified
//     if (!response.ok) {
//         throw new Error(`HTTP error! Status: ${response.status}`);
//     }
//     return await response.json();

// }

export const getCharacter = async (): Promise<Character> => {
    
    await new Promise((res) => setTimeout(res, 300));

    return {
        id: "char-1",
        name: "Craig the Test Character",
        level: 69,
        experience: 35000,
        stats: [
            { type: "Fire Resistance", value: 55 },
            { type: "Cold Resistance", value: 45 },
            { type: "Lightning Resistance", value: 69},
            { type: "Health", value: 600}
        ],
        items: [
            {
                id: "asdfasdf",
                name: "Cool Sword",
                type: "WEAPON",
                stats: [
                    { type: "Damage", value: 22 },
                    { type: "All Resistance", value: 5 }
                ] 
            },

            {
                id: "asdfiopi",
                name: "Green Shield",
                type: "SHIELD",
                stats: [
                    { type: "Defense", value: 75 },
                    { type: "All Resistance", value: 10 }
                ],
            },

            {
                id: "aeeredfdaf",
                name: "Silver Pants",
                type: "PANTS",
                stats: [
                    { type: "Defense", value: 40 },
                    { type: "Fire Resistance", value: 5 }
                ],
            }
        ]
    }

}


// get Item

// update Item

// delete Item