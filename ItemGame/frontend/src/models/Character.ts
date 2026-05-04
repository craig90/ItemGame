import type { Item } from "./Item";
// import type { Stat } from "./Stat";
import type { SlotType } from "./SlotType";

export interface Character {
  // TODO: come back to this - currently not being used in the backend but
  // will be needed if I plan on storying character's in the database / 
  // makes more sense to send an id back than character name
  id: string  
  name: string;
  level: number;
  experience: number;
  damage: number;
  health: number;
  defence: number;
  coldResistance: number;
  fireResistance: number;
  lightningResistance: number;
  magicResistance: number;
  poisonResistance: number;
  equippedItems: Partial<Record<SlotType, string>>;
  itemsById: Record<string, Item>;
}