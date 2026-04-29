import { useEffect, useState } from "react"
import type { CharacterSummary } from "../models/CharacterSummary";
// import "./CharacterPage.css";
import { getCharacters } from "../service/api";

export function CharacterSelectionPage() {
    
    const [characters, setCharacters] = useState<CharacterSummary[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function loadCharacters() {
            const data = await getCharacters();
            console.log(data);
            setCharacters(data);
            setLoading(false);
        }
        loadCharacters();
    
    }, []);
    
    if (loading) {
        return <div>Loading characters...</div>;
    }

    if (!characters) {
        return <div>No characters found</div>;
    }

    return (
        <div>
            {characters.map((char) => (
                <div key={char.id}>
                    <p>{char.name} (Level {char.level})</p>
                    <button>Select</button>
                </div>
            ))}
        </div>
    )
}
