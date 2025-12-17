import { useState } from "react"

export default function TodoList() {
    
    const [todoList, setTodoList] = useState([
        { id: 1, task: "Buy groceries", completed: false},
        { id: 2, task: "Wash car", completed: true },
        { id: 3, task: "Clean Bedroom", completed: false }
    ]);

    const [input, setInput] = useState("");


    function addToList() {
        if (input !== "") {
            const newTask = {id: Date.now(), task: input, completed: false}
            setTodoList([...todoList, newTask]);
        }
        setInput('');
    }

    function removeFromList(id) {
        setTodoList(todoList.filter(item => item.id !== id));
    }

    function updateStatus(id) {
        
        const updatedTodoList = todoList.map((item) => {
            if (item.id === id) {
                return {...item, completed: true}
            }
            return item;
        });

        setTodoList(updatedTodoList);

    };


    return (
        <div>
            {todoList.length === 0 ? (
                <p>No tasks yet</p>
            ) : (
                <ul>
                    {todoList.map((item) => (
                        <li key={item.id}>
                            {item.task}
                            {item.completed ? " ✅ " : <button onClick={() => updateStatus(item.id)}>Mark as Complete</button>}
                            <button onClick={() => removeFromList(item.id)}>Delete Task</button>
                        </li>
                    ))}
                </ul>
            )}

            <label>Enter a new todo item:</label>
            <input type="text" value={input} onChange={(e) => setInput(e.target.value)}></input>
            <button onClick={addToList}>Add Task</button>

        </div>
    );

}