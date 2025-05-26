public class CharacterStat extends Stat {

    Character character;

    public CharacterStat() {

    }

    public CharacterStat(Character character, String type, double value) {
        this.character = character;
        this.type = type;
        this.value = value;
    }


    // Character Stats that share the same updateLogic will use this function instead of using a subClass
    void updateValue() {

        // this will work for resistence and maybe a few others
        this.value = character.getLevel();


        System.out.println("We have " + this.character.getItemList().size() + " items");
        // go through the list of items
        for (int i = 0; i < this.character.getItemList().size(); i++) {
            
            System.out.println("Item " + this.character.getItemList().get(i).getItemName() + " has " + 
            this.character.getItemList().get(i).getStatList().size() + " stats");


            // go through the list of stats
            for (int j = 0; j < this.character.getItemList().get(i).getStatList().size(); j++) {
                
                // see if its the same type
                if (this.type == this.character.getItemList().get(i).getStatList().get(j).getStatType()) {
                    this.value += this.character.getItemList().get(i).getStatList().get(j).getValue();
                } 
        
            }
        
        }

    }

}
