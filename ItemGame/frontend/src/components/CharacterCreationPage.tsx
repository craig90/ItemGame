import "./CharacterCreationPage.css";
import "./General.css";

export function CharacterCreationPage() {
    return (
        <div className="standard-background">
            <div>
                <h2>Create Character</h2>
                <p>Enter Character Name</p>
                <input type="text" name="charactername"></input>
            </div>
            <div>
                <button>Create</button>
            </div>
        </div>
    );
}