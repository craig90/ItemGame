public enum ItemType {

    AMULET(EquipmentSlot.AMULET.getDisplayName(), EquipmentSlot.AMULET),
    BELT(EquipmentSlot.BELT.getDisplayName(), EquipmentSlot.BELT),
    BOOTS(EquipmentSlot.BOOTS.getDisplayName(), EquipmentSlot.BOOTS),
    CHEST(EquipmentSlot.CHEST.getDisplayName(), EquipmentSlot.CHEST),
    GLOVES(EquipmentSlot.GLOVES.getDisplayName(), EquipmentSlot.GLOVES),
    HELM(EquipmentSlot.HELM.getDisplayName(), EquipmentSlot.HELM),
    PANTS(EquipmentSlot.PANTS.getDisplayName(), EquipmentSlot.PANTS),
    SHIELD(EquipmentSlot.SHIELD.getDisplayName(), EquipmentSlot.SHIELD),
    WEAPON(EquipmentSlot.WEAPON.getDisplayName(), EquipmentSlot.WEAPON),
    RING(EquipmentSlot.RIGHT_RING.getDisplayName(), null); // Rings are special: no specific EquipmentSlot - also
    // both ring slots have the name Ring so either can be used here

    private final String name;
    private final EquipmentSlot equipmentSlot;

    ItemType(String name, EquipmentSlot slot) {
        this.name = name;
        this.equipmentSlot = slot;
    }

    public String getDisplayName() {
        return this.name;
    }

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public boolean isRing() {
        return this == RING;
    }

    public boolean isEquippableInEquipmentSlot() {
        return equipmentSlot != null;
    }

}
