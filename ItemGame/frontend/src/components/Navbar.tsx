import { useState } from 'react';
import { Link } from "react-router-dom";

import "./Navbar.css";

export default function Navbar() {

    const [isOpen, setIsOpen] = useState(false);

    return (
        <nav className="navbar">
            <div className="nav-logo">Item Game</div>
            <ul className={`nav-links ${isOpen ? "open" : ""}`}>
                <li>
                    <Link to="/">Home</Link>
                </li>
                <li>
                    <Link to="/create-character">Create Character</Link>
                </li>
                <li>
                    <Link to="/select-character">Select Character</Link>
                </li>
            </ul>
        </nav>
    );

}