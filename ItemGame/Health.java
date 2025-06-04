public class Health extends CharacterStat {

    private int maxValue;
    private final int minValue = 0;

    // we want to initiatlize this with full health
    public Health(Character character) {
        this.type = CharacterStatNames.HEALTH;
        this.character = character;

        // find the max value which will be determined by the items and the level
        // this.maxValue = character.level + increasefromItems 
        
        

    }

    /**
     * this method will update the health value taking into account character level and and items which
     * affect the health value
     */
    public void updateValue() {

        System.out.println("Updooting the health stat");
        for (int i = 0; i < this.character.getItemListSize(); i++ ) {
            
            System.out.println("Checking item list for any health modifiers");
            // dummy update for now

            this.value = this.value + 5.0;

            // gives me an item
            // Item item = this.character.getItemList().get(i);

            // for (int j = 0; j < item.statList.size(); j ++) {
            //     Stat stat = item.statList.get(j);
                
            //     if (stat.type == StatNames.HEALTH) {
            //         this.value = this.value + stat.getValue();
            //     } 
            // }
        } 

    }


}
