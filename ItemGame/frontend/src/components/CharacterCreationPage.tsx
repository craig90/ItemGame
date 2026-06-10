// import "./CharacterCreationPage.css";
import "./General.css";
import "./CharacterCreationPage.css";
import { createCharacter } from "../service/api";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export function CharacterCreationPage() {

    const [characterName, setCharacterName] = useState("");

    const navigate = useNavigate();

    const handleCharacterCreation = async () => {

        console.log(characterName);

        try {
            const character = await createCharacter(characterName);
            navigate(`/character/${character.id}`);
        } catch (errData: any) {
            console.log(errData);
        }



    }
    
    return (
        <div className="standard-background">
            <div>
                <h2>Create Character</h2>
                <p>Enter Character Name</p>
                <input 
                    className="character-input"
                    type="text"
                    name="charactername"
                    value={characterName}
                    onChange={(e)=> setCharacterName(e.target.value)}
                    ></input>
            </div>
            <div>
                <button onClick={handleCharacterCreation}>Create</button>
            </div>
        </div>
    );
}