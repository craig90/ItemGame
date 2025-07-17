import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.List;

public class ItemManager {

    // private Map<String, Item> uniqueItems;
    private Map<RingSlot, Ring> rings;
    private Map<EquipmentSlot, Item> uniqueItems;

    public ItemManager() {
        this.uniqueItems = new EnumMap<>(EquipmentSlot.class);
        this.rings = new EnumMap<>(RingSlot.class);
    }

    // get methods
    public Item getWeapon() {
        return getUniqueItem(ItemType.WEAPON.getEquipmentSlot());
    }

    public Item getHelm() {
        return getUniqueItem(ItemType.HELM.getEquipmentSlot());
    }
    
    public Item getChest() {
        return getUniqueItem(ItemType.CHEST.getEquipmentSlot());
    }

    public Item getShield() {
        return getUniqueItem(ItemType.SHIELD.getEquipmentSlot());
    }

    public Item getAmulet() {
        return getUniqueItem(ItemType.AMULET.getEquipmentSlot());
    }

    public Item getLeftRing() {
        return this.rings.get(RingSlot.LEFT_HAND);
    }

    public Item getRightRing() {
        return this.rings.get(RingSlot.RIGHT_HAND);
    }

    public Item getGloves() {
        return getUniqueItem(ItemType.GLOVES.getEquipmentSlot());
    }
    
    public Item getBelt() {
        return getUniqueItem(ItemType.BELT.getEquipmentSlot());
    }

    public Item getPants() {
        return getUniqueItem(ItemType.PANTS.getEquipmentSlot());
    }
    
    public Item getBoots() {
        return getUniqueItem(ItemType.BOOTS.getEquipmentSlot());
    }


    /**
     * return an item at the specified index
     * @param index
     * @return
     */
    /*public Item getItem(int index) {
        return this.itemList.get(index);
    }*/

    /**
     * Returns the item specified by item slot type
     * @param itemType
     * @return will return an item if it exists, otherwise returns null
     */
    public Item getUniqueItem(EquipmentSlot slotType) {
        if (slotType == EquipmentSlot.LEFT_RING) {
            return rings.get(RingSlot.LEFT_HAND);
        } else if (slotType == EquipmentSlot.RIGHT_RING) {
            return rings.get(RingSlot.RIGHT_HAND);
        } else {
            return uniqueItems.get(slotType);
        }
    }

    public Ring getRing(RingSlot ringType) {
        return rings.get(ringType);
    }

    /**
     * returns the number of items on the item list
     * @return
     */
    /*public int getItemListSize() {
        return this.itemList.size();
    }*/

    /**
     * this will add the item to the list and remove any existing item of the same item type
     * @param item
     */
    public void equipItem(Item item, EquipmentSlot slot) {
        uniqueItems.put(slot, item);
    }

    public void equipRing(Ring ring, RingSlot slot) {
        this.rings.put(slot, ring);
    }


    public void removeItem(EquipmentSlot slot) {
        this.uniqueItems.remove(slot);
    }

    public void removeRing(RingSlot slot) {
        this.rings.remove(slot);
    }


    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>(this.uniqueItems.values());
        allItems.addAll(rings.values());
        return allItems;
    }

    /**
     * This will return wheather a specific item type is already equipped
     * @return boolean
     */
    /*public boolean isEquipped(String itemType) {

        if (this.getItem(itemType) != null) {
            return true;
        }
        return false;
    }*/


    /**
     * Displays the character's items
     */
    public void displayItems() {
        
        List<Item> items = getAllItems();

        if (items.size() == 0) {
            System.out.println("No items equipped");
            return;
        }

        System.out.println("Items:");

        for (Item item : items)  {
            item.displayItem();
        }
    }

}
