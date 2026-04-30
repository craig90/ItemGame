import { useEffect, useState } from "react"
import type { CharacterSummary } from "../models/CharacterSummary";
// import "./CharacterPage.css";
import { getCharacters } from "../service/api";

type Props = {
    onSelect: (id: string) => void;
}


export function CharacterSelectionPage({ onSelect }: Props) {
    
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
                    <button onClick={() => onSelect(char.id)}>Select</button>
                </div>
            ))}
        </div>
    )
}
