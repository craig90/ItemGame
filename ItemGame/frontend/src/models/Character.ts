import type { Item } from "./Item";
import type { Stat } from "./Stat";
import type { SlotType } from "./SlotType";

export interface Character {
  // TODO: come back to this - currently not being used in the backend but
  // will be needed if I plan on storying character's in the database / 
  // makes more sense to send an id back than character name
  id: string  
  name: string;
  level: number;
  experience: number;
  stats: Stat[];  // TODO: might need to be changed due to back-end subclasses
  items: Partial<Record<SlotType, Item>>;
}