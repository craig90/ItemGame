import java.util.Random;


public class ItemGenerator {

    public ItemGenerator () {

    }

    // TODO: we could add a lot more logic over different stat types
    // Do certain items have a smaller subset of stat types - it's possible that each item type has seperate logic
    // for stat creation
    public Item createNewItem() throws Exception {
    // public void createNewItem() {

        Random random = new Random();
        Item newItem;

        // decide the item type
        ItemType[] types = ItemType.values();
        ItemType itemType = types[random.nextInt(types.length)];

        if (itemType == ItemType.AMULET) {
            newItem = new Amulet("");
        } else if (itemType == ItemType.BOOTS) {
            newItem = new Boots("");
        } else if (itemType == ItemType.BELT) {
            newItem = new Belt("");
        } else if (itemType == ItemType.CHEST) {
            newItem = new Chest("");
        } else if (itemType == ItemType.GLOVES) {
            newItem = new Gloves("");
        } else if (itemType == ItemType.HELM) {
            newItem = new Helm("");
        } else if (itemType == ItemType.PANTS) {
            newItem = new Pants("");
        } else if (itemType == ItemType.SHIELD) {
            newItem = new Shield("");
        } else if (itemType == ItemType.WEAPON) {
            newItem = new Weapon("");
        } else if (itemType == ItemType.RING) {
            newItem = new Ring("");
        } else {
            throw new Exception("Error creating item, unrecognized item type");
        }

        // System.out.println("Generating New: " + ItecmConstants.ITEM_TYPES[itemNumber]);
        // System.out.println("with " + numberOfStats + " stats");
        
        //newItem.setItemType(Item.ITEM_TYPES[itemNumber]);

        // choose the item Name from list of adjectives
        int itemNameAdjective = random.nextInt(Item.ITEM_ADJECTIVES.length);
        newItem.setItemName(Item.ITEM_ADJECTIVES[itemNameAdjective] + " " + newItem.getItemType().getDisplayName());

        newItem.generateItem(random);

        return newItem;

    }

    /**
     * this main method is purely for resting the ItemGenerator
     * @param args
     */
    public static void main(String args[]){
        
        Item item;
        ItemGenerator itemGenerator = new ItemGenerator();
        try {
            item = itemGenerator.createNewItem();
            item.displayItem();
        } catch (Exception e) {
            System.out.println("Error creating item");
        }

    }    

}
