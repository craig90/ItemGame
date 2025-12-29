export const SLOT_TYPES = {
    AMULET: "amulet",
    BELT: "belt",
    BOOTS: "boots",
    CHEST: "chest",
    GLOVES: "gloves",
    HELMET: "helmet",
    PANTS: "pants",
    SHIELD: "shield",
    RING_RIGHT: "ring-right",
    RING_LEFT: "ring-left",
    WEAPON: "weapon",
    NEW_ITEM: "new-item",
    DELETE_ITEM: "delete-item",
} as const;

export type SlotType = typeof SLOT_TYPES[keyof typeof SLOT_TYPES];