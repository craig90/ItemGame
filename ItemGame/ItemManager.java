import java.util.ArrayList;

public class ItemManager {

    Item weapon;
    Item helm;
    Item chest;
    Item shield;
    Item amulet;
    Item ringOne;
    Item ringTwo;
    Item gloves;
    Item belt;
    Item pants;
    Item boots;

    ArrayList<Item> itemList;
    // ArrayList<Item> spareItems;
    
    public ItemManager() {
        this.itemList = new ArrayList<Item>();
    }


    // set methods
    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public void setHelm(Item helm) {
        this.helm = helm;
    }
    
    public void setChest(Item chest) {
        this.chest = chest;
    }

    public void setShield(Item shield) {
        this.shield = shield;
    }

    public void setAmulet(Item amulet) {
        this.amulet = amulet;
    }

    public void setRingOne(Item ringOne) {
        this.ringOne = ringOne;
    }

    public void setRingTwo(Item ringTwo) {
        this.ringTwo = ringTwo;
    }

    public void setGloves(Item gloves) {
        this.gloves = gloves;
    }
    
    public void setBelt(Item belt) {
        this.belt = belt;
    }

    public void setPants(Item pants) {
        this.pants = pants;
    }

    public void setBoots(Item boots) {
        this.boots = boots;
    }


    // get methods
    public Item getWeapon() {
        return getItem(Item.WEAPON);
    }

    public Item getHelm() {
        return getItem(Item.HELM);
    }
    
    public Item getChest() {
        return getItem(Item.CHEST);
    }

    public Item getShield() {
        return getItem(Item.SHIELD);
    }

    public Item getAmulet() {
        return getItem(Item.AMULET);
    }

    public Item getRingOne() {
        return getItem(Item.RING);
    }

    public Item getRingTwo() {
        return getItem(Item.RING);
    }

    public Item getGloves() {
        return getItem(Item.GLOVES);
    }
    
    public Item getBelt() {
        return getItem(Item.BELT);
    }

    public Item getPants() {
        return getItem(Item.PANTS);
    }
    
    public Item getBoots() {
        return getItem(Item.BOOTS);
    }


    /**
     * method to be called when the caller doesn't want to look up item type
     */
    /*public void addItem(Item item) {

        // TODO: do we want to check if it's alreay equipped?
        
        if (item.getItemType() == Item.WEAPON) {
            setWeapon(item);
        } else if (item.getItemType() == Item.HELM) {
            setHelm(item);
        } else if (item.getItemType() == Item.CHEST) {
            setChest(item);
        } else if (item.getItemType() == Item.SHIELD) {
            setShield(item);    
        } else if (item.getItemType() == Item.AMULET) {
            setAmulet(item);
        } else if (item.getItemType() == Item.RING) {
            setRingOne(item);
        // } else if (item.getItemType() == Item.RING) {  // TODO: how to handle this
            // setRingTwo(item);
        } else if (item.getItemType() == Item.GLOVES) {
            setGloves(item);
        } else if (item.getItemType() == Item.BELT) {
            setBelt(item);
        } else if (item.getItemType() == Item.PANTS) {
            setPants(item);
        } else if (item.getItemType() == Item.BOOTS) {
            setBoots(item);
        }

    }*/

    /**
     * return an item at the specified index
     * @param index
     * @return
     */
    public Item getItem(int index) {
        return this.itemList.get(index);
    }

    /**
     * Returns the item specified by itemType
     * @param itemType
     * @return will return an item if it exists, otherwise returns null
     */
    public Item getItem(String itemType) {

        for (int i = 0; i < this.itemList.size(); i++) {
            if (this.itemList.get(i).getItemType().equals(itemType)) {
                return this.itemList.get(i);
            }
        }

        return null;
    }

    /**
     * returns the number of items on the item list
     * @return
     */
    public int getItemListSize() {
        return this.itemList.size();
    }

    /**
     * this will add the item to the list and remove any existing item of the same item type
     * @param item
     */
    public void addItem(Item item) {

        // see if we have to remove the item first
        if (isEquipped(item.getItemType())) {
            removeItem(item.getItemType());
        }

        this.itemList.add(item);
    }

    /**
     * this will remove an item from the item list with type itemType
     * @param itemType
     */
    public void removeItem(String itemType) {

        for (int i = 0; i < this.itemList.size(); i++) {
            if (this.itemList.get(i).getItemType().equals(itemType)) {
                System.out.println("Removing item " + this.itemList.get(i).getItemName());
                this.itemList.remove(i);
            }
        }

    }

    /**
     * This will return wheather a specific item type is already equipped
     * @return boolean
     */
    public boolean isEquipped(String itemType) {

        if (this.getItem(itemType) != null) {
            return true;
        }
        return false;
    }


    /**
     * Displays the character's items
     */
    public void displayItems() {
        
        if (this.itemList.size() == 0) {
            System.out.println("No items equipped");
            return;
        }

        System.out.println("Items:");

        for (int i = 0; i < this.itemList.size(); i++) {
            this.itemList.get(i).displayItem();
        }
    }

}
