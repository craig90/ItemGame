import { useState } from "react"

export default function Counter() {
    const [count, setCount] = useState(0)
    return (
        <div>
            <p>Count: {count}</p>
            <button onClick={() => setCount(count +1)}>Increment</button>
            <button onClick={() => setCount(count -1)}>Decrement</button>
            <button onClick={() => setCount(count +5)}>Increment by 5</button>
        </div>
    );
}