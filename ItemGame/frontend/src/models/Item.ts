import type { Stat } from "./Stat";

// Item could be updated with a sort of image id in the future to allow multiple image types
// per item type.  Currently the image is derived from the ItemType
export interface Item {
  id: string;
  name: string;
  type: ItemType;
  stats: Stat[];
}

export const ITEM_TYPES = {
  AMULET: "AMULET",
  BELT: "BELT",
  BOOTS: "BOOTS",
  CHEST: "CHEST",
  GLOVES: "GLOVES",
  HEMLET: "HELMET",
  PANTS: "PANTS",
  RING: "RING",
  SHIELD: "SHIELD",
  WEAPON: "WEAPON",
} as const;

export type ItemType = typeof ITEM_TYPES[keyof typeof ITEM_TYPES];


