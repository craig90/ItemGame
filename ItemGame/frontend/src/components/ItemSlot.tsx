import type { Item } from "../models/Item";

type ItemSlotProps = {
    slotType: string;
    item?: Item;
}

export function ItemSlot({slotType, item}: ItemSlotProps) {
    return (
        <div className="item-slot">
            {slotType}
            {item ? (
                <>
                    <div className="item-name">{item.name}</div>
                    <ul>
                        {item.stats.map((stat) => (
                            <li key={stat.type}>
                                {stat.type}: {stat.value}
                            </li>
                        ))}
                    </ul>
                </>
            ) : (
                <div>Not Equipped</div>
            )
            
            }
            
            
        </div>
    );
}