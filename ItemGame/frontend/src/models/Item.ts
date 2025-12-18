import type { Stat } from "./Stat";

export interface Item {
  id: string;
  name: string;
  type: string;  // TODO: make enum
  stats: Stat[];
  //image ?
}