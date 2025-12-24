import type { Item } from "../models/Item";
import "./ItemSlot.css";
// import weaponLogo from "../assets/weapon.png"

type ItemSlotProps = {
    slotType: string;
    item?: Item;
}

export function ItemSlot({slotType, item}: ItemSlotProps) {
    return (
        // 
        
        <div className={`item-slot item-slot--${slotType}`}>
            <div className="slot-type">{slotType}</div>
            {item && (
                <>
                    {/* <div className="item-name">{item.name}</div>
                    {item.stats.map((stat) => (
                        <p className="stat">{stat.type}: {stat.value}
                        </p>
                    ))} */}
                    {/* <img src={weaponLogo}/> */}
                <img 
                    src="/src/assets/weapon.png" 
                    alt=""
                    className={`item-slot--${slotType} gear-image`}
                />
                </>
                )
            }
            
        </div>
    );
}