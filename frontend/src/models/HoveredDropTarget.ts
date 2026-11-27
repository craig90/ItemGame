import type { SlotType } from "./SlotType"

export type HoveredDropTarget = {
    type: "equipment" | "inventory";
    slot?: SlotType;
    x?: number;
    y?: number;
}
