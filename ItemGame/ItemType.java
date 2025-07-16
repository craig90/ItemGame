public enum ItemType {

    AMULET(EquipmentSlot.AMULET),
    BELT(EquipmentSlot.BELT),
    BOOTS(EquipmentSlot.BOOTS),
    CHEST(EquipmentSlot.CHEST),
    GLOVES(EquipmentSlot.GLOVES),
    HELM(EquipmentSlot.HELM),
    PANTS(EquipmentSlot.PANTS),
    SHIELD(EquipmentSlot.SHIELD),
    WEAPON(EquipmentSlot.WEAPON),
    RING(null); // Rings are special: no EquipmentSlot

    private final EquipmentSlot equipmentSlot;

    ItemType(EquipmentSlot slot) {
        this.equipmentSlot = slot;
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
