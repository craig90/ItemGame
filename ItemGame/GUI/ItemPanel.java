import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.TransferHandler;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JLayeredPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceAdapter;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

public class ItemPanel extends JLayeredPane {

    final static int ITEM_WINDOW_WIDTH = 700;
    final static int ITEM_WINDOW_HEIGHT = 475;

    JPanel equippedItemPanel, inventoryPanel;
    
    HoverPanel hoverPanel;

    JLabel helm, weapon, amulet, ring, ringTwo, belt, pants, gloves, shieldLabel, chest, boots;

    ItemLayeredPane helmPanel, weaponPanel, amuletPanel, shieldPanel, rightHandRingPanel, leftHandRingPanel;
    ItemLayeredPane beltPanel, pantsPanel, glovesPanel, chestPanel, bootsPanel;

    ImageIcon helmIcon, shieldIcon;

    JButton getNewItemButton, deleteItemButton;
    ItemLayeredPane generatedItemPanel, inventorySlotOnePanel, inventorySlotTwoPanel, inventorySlotThreePanel;
    ItemLayeredPane inventorySlotFourPanel;

    CharacterScreen characterScreen;  // TODO: I don't like that its getting this either
    ItemGenerator itemGenerator; // TODO: probably should not have direct access to this either
    ItemManager itemManager; // TODO: should not have access to this

    MyMouseListener mouseListener;

    // JSeparator separator;

    ItemController itemController;

    private final Map <EquipmentSlot, ItemLayeredPane> slotToPanel = new EnumMap<>(EquipmentSlot.class);
    // ItemLayeredPane sourcePanel;
    // Item draggedItem;

    public ItemPanel(CharacterScreen characterScreen, ItemController itemController) {

        this.itemGenerator = new ItemGenerator();

        // this.layeredPane = new JLayeredPane();

        this.equippedItemPanel = new JPanel();
        this.equippedItemPanel.setLayout(new GridBagLayout());

        this.characterScreen = characterScreen;
        this.itemController = itemController;

        // creates a constraints object
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        
        // helm
        this.helmPanel = new ItemLayeredPane(EquipmentSlot.HELM);
        c.gridx = 2;
        c.gridy = 0;
        this.equippedItemPanel.add(this.helmPanel, c);

        // weapon
        this.weaponPanel = new ItemLayeredPane(EquipmentSlot.WEAPON);
        c.gridx = 0;
        c.gridy = 1;
        this.equippedItemPanel.add(this.weaponPanel, c);

        // amulet
        this.amuletPanel = new ItemLayeredPane(EquipmentSlot.AMULET);
        c.gridx = 3;
        c.gridy = 0;
        this.equippedItemPanel.add(this.amuletPanel, c);
        
        // ring
        this.rightHandRingPanel = new ItemLayeredPane(EquipmentSlot.RIGHT_RING);
        c.gridx = 1;
        c.gridy = 2;
        this.equippedItemPanel.add(this.rightHandRingPanel, c);
        
        this.leftHandRingPanel = new ItemLayeredPane(EquipmentSlot.LEFT_RING);
        c.gridx = 3;
        c.gridy = 2;
        this.equippedItemPanel.add(this.leftHandRingPanel, c);

        // chest panel
        this.chestPanel = new ItemLayeredPane(EquipmentSlot.CHEST);
        c.gridx = 2;
        c.gridy = 1;
        this.equippedItemPanel.add(this.chestPanel, c);

        // gloves
        this.glovesPanel = new ItemLayeredPane(EquipmentSlot.GLOVES);
        c.gridx = 0;
        c.gridy = 2;
        this.equippedItemPanel.add(this.glovesPanel, c);

        // shield
        this.shieldPanel = new ItemLayeredPane(EquipmentSlot.SHIELD);
        c.gridx = 5;
        c.gridy = 1;
        this.equippedItemPanel.add(this.shieldPanel, c);

        // belt
        this.beltPanel = new ItemLayeredPane(EquipmentSlot.BELT);
        c.gridx = 2;
        c.gridy = 2;
        this.equippedItemPanel.add(this.beltPanel, c);

        // pants
        this.pantsPanel = new ItemLayeredPane(EquipmentSlot.PANTS);
        c.gridx = 4;
        c.gridy = 1;
        this.equippedItemPanel.add(this.pantsPanel, c);

        // boots
        this.bootsPanel = new ItemLayeredPane(EquipmentSlot.BOOTS);
        c.gridx = 4;
        c.gridy = 2;
        this.equippedItemPanel.add(this.bootsPanel, c);

        // this.separator = new JSeparator(SwingConstants.HORIZONTAL);
        // this.separator.setMaximumSize(new Dimension(this.equippedItemPanel.getMaximumSize().width, 10));
        // this.separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        // this.equippedItemPanel.add(this.separator);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(4, 4, 4, 4);

        this.inventoryPanel = new JPanel();
        this.inventoryPanel.setLayout(new GridBagLayout());

        this.inventorySlotOnePanel = new ItemLayeredPane(EquipmentSlot.INVENTORY_SLOT_ONE);
        c1.gridx = 0;
        c1.gridy = 0;
        this.inventoryPanel.add(this.inventorySlotOnePanel, c1);

        this.inventorySlotTwoPanel = new ItemLayeredPane(EquipmentSlot.INVENTORY_SLOT_TWO);
        c1.gridx = 1;
        c1.gridy = 0;
        this.inventoryPanel.add(this.inventorySlotTwoPanel, c1);

        this.inventorySlotThreePanel = new ItemLayeredPane(EquipmentSlot.INVENTORY_SLOT_THREE);
        c1.gridx = 2;
        c1.gridy = 0;
        this.inventoryPanel.add(this.inventorySlotThreePanel, c1);

        this.inventorySlotFourPanel = new ItemLayeredPane(EquipmentSlot.INVENTORY_SLOT_FOUR);
        c1.gridx = 3;
        c1.gridy = 0;
        this.inventoryPanel.add(this.inventorySlotFourPanel, c1);

        this.deleteItemButton = new JButton("Delete Item");
        c1.gridx = 3;
        c1.gridy = 1;
        this.inventoryPanel.add(this.deleteItemButton, c1);


        // this panel is a little different because it's for generating new items not for deleting items
        this.generatedItemPanel = new ItemLayeredPane(EquipmentSlot.NEW_ITEM_SLOT);
        c1.gridx = 4;
        c1.gridy = 0;
        this.inventoryPanel.add(this.generatedItemPanel, c1);

        // generate new item button
        this.getNewItemButton = new JButton("Get New Item");
        c1.gridx = 4;
        c1.gridy = 1;
        this.inventoryPanel.add(this.getNewItemButton, c1);
        

        // the size has to be set for layered Panes 
        this.equippedItemPanel.setBounds(0, 0, ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);
        this.inventoryPanel.setBounds(0, ITEM_WINDOW_HEIGHT, ITEM_WINDOW_WIDTH, 250);
        this.setSize(ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);

        this.hoverPanel = new HoverPanel();

        this.add(this.equippedItemPanel, 1);
        this.add(this.inventoryPanel, 1);
        this.inventoryPanel.setBackground(new Color(180, 180, 180));
        this.equippedItemPanel.setBackground(new Color(169, 169, 169));

        this.add(this.hoverPanel, 0);

        this.setVisible(true);

        this.mouseListener = new MyMouseListener(this);

        this.getNewItemButton.addActionListener(e -> {
            
            Item newItem;
            try {
                newItem = this.itemGenerator.createNewItem();
            } catch (Exception ex) {
                System.out.println("Error creating new item");
                return;
            }
            
            this.itemManager.setGeneratedItem(newItem);
            this.generatedItemPanel.addItemToSlot(newItem);
            setSourceTransferHandler(newItem);
            newItem.getItemImageLabel().addMouseListener(this.mouseListener);

        });

        // delete the item
        this.deleteItemButton.addActionListener(e -> {
            // TODO: fix up try/catch and exception throwing
            try {
                this.itemManager.removeItem(EquipmentSlot.INVENTORY_SLOT_FOUR);
                this.inventorySlotFourPanel.removeItemFromSlot();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            
        });

        slotToPanel.put(EquipmentSlot.AMULET, this.amuletPanel);
        slotToPanel.put(EquipmentSlot.BELT, this.beltPanel);
        slotToPanel.put(EquipmentSlot.BOOTS, this.bootsPanel);
        slotToPanel.put(EquipmentSlot.CHEST, this.chestPanel);
        slotToPanel.put(EquipmentSlot.GLOVES, this.glovesPanel);
        slotToPanel.put(EquipmentSlot.HELM, this.helmPanel);
        slotToPanel.put(EquipmentSlot.LEFT_RING, this.leftHandRingPanel);
        slotToPanel.put(EquipmentSlot.PANTS, this.pantsPanel);
        slotToPanel.put(EquipmentSlot.RIGHT_RING, this.rightHandRingPanel);
        slotToPanel.put(EquipmentSlot.SHIELD, this.shieldPanel);
        slotToPanel.put(EquipmentSlot.WEAPON, this.weaponPanel);
        slotToPanel.put(EquipmentSlot.NEW_ITEM_SLOT, this.generatedItemPanel);

        // these are the transfer handlers that recieve an item
        this.amuletPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.bootsPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.beltPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.chestPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.glovesPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.helmPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.leftHandRingPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.pantsPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.rightHandRingPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.shieldPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        this.weaponPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        
        // this.inventorySlotOnePanel.setTransferHandler(new ItemPanelTransferHandler(this));
        // this.inventorySlotTwoPanel.setTransferHandler(new ItemPanelTransferHandler(this));
        // this.inventorySlotThreePanel.setTransferHandler(new ItemPanelTransferHandler(this));
        // this.inventorySlotFourPanel.setTransferHandler(new ItemPanelTransferHandler(this));

    }


    public void setSourceTransferHandler(Item item) {

        item.itemImageLabel.setTransferHandler(new MyLabelTransferHandler());

        HoverPanel hoverPanel = this.hoverPanel;

        DragSource dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(item.itemImageLabel, DnDConstants.ACTION_MOVE, new DragGestureListener() {
            @Override
            public void dragGestureRecognized(DragGestureEvent dge) {
                
                hoverPanel.hideHoverPanel(); // hide the hover panel when the drag starts

                TransferHandler th = item.itemImageLabel.getTransferHandler();
                if (!(th instanceof MyLabelTransferHandler)) return;

                Transferable transferable = ((MyLabelTransferHandler) th).getTransferable(item.itemImageLabel);
                
                if (transferable == null) return;

                // Create custom drag image
                Image dragImage = createDragImage(item.itemImageLabel);

                // Start drag with image
                dragSource.startDrag(dge, DragSource.DefaultMoveDrop, dragImage, new Point(0, 0), transferable,
                    new DragSourceAdapter() {
                        @Override
                        public void dragDropEnd(DragSourceDropEvent dsde) {
                            ((MyLabelTransferHandler) th).exportDone((JComponent)item.itemImageLabel, transferable, 
                            dsde.getDropSuccess() ? DnDConstants.ACTION_MOVE : DnDConstants.ACTION_NONE);
                        }

                    } // optional: override if needed
                );
            }

        });

    }

    public Image createDragImage(JComponent comp) {
        Dimension size = comp.getSize();
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        comp.paint(g2);
        g2.dispose();
        return image;
    }

    public void setSourceTransferHandlers() {

        ArrayList<Item> items = (ArrayList<Item>) this.itemManager.getAllItems();

        for (Item item : items) {
            setSourceTransferHandler(item);
        }

    }


    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }


    // public void setSourcePanel(ItemLayeredPane panel) {
    //     this.sourcePanel = panel;
    // }

    // public void initializeDrag(JLabel label) {
    //     System.out.println("Initializing drag");

    //     setDraggedItem(label.getName());
    //     setSourcePanel((ItemLayeredPane)label.getParent());
    //     hoverPanel.hideHoverPanel();
    // }

    /**
     * gets the item that is being dragged
     * @return
     */
    // public Item getDraggedItem() {
    //     return this.draggedItem;
    // }


    /**
     * removes the items from the panes - used when reloading a character
     */
    public void clearItemPanes() {

        // TODO: potentially throw these on array list to make things more dynamic
        this.helmPanel.removeItemFromSlot();
        this.weaponPanel.removeItemFromSlot();
        this.amuletPanel.removeItemFromSlot();
        this.chestPanel.removeItemFromSlot();
        this.shieldPanel.removeItemFromSlot();
        this.beltPanel.removeItemFromSlot();
        this.bootsPanel.removeItemFromSlot();
        this.glovesPanel.removeItemFromSlot();
        this.pantsPanel.removeItemFromSlot();
        this.rightHandRingPanel.removeItemFromSlot();
        this.leftHandRingPanel.removeItemFromSlot();
        this.generatedItemPanel.removeItemFromSlot();
        this.inventorySlotOnePanel.removeItemFromSlot();
        this.inventorySlotTwoPanel.removeItemFromSlot();
        this.inventorySlotThreePanel.removeItemFromSlot();
        this.inventorySlotFourPanel.removeItemFromSlot();
    }
    
    
    /**
     * sets whichever items the character has in their respective item panes
     */
    public void setItemsInPanes() {

        if (this.itemManager == null) {
            System.out.println("Error");
            return;
        }

        // TODO: there's got to be a way to do this dynmacally

        if (this.itemManager.getHelm() != null) {
            this.helmPanel.addItemToSlot(this.itemManager.getHelm());
        }

        if (this.itemManager.getWeapon() != null) {
            this.weaponPanel.addItemToSlot(this.itemManager.getWeapon());
        }

        if (this.itemManager.getAmulet() != null) {
            this.amuletPanel.addItemToSlot(this.itemManager.getAmulet());
        }
        
        if (this.itemManager.getChest() != null) {
            this.chestPanel.addItemToSlot(this.itemManager.getChest());
        }

        if (this.itemManager.getShield() != null) {
            this.shieldPanel.addItemToSlot(this.itemManager.getShield());
        }
        
        if (this.itemManager.getBoots() != null) {
            this.bootsPanel.addItemToSlot(this.itemManager.getBoots());
        }

        if (this.itemManager.getBelt() != null) {
            this.beltPanel.addItemToSlot(this.itemManager.getBelt());
        }

        if (this.itemManager.getGloves() != null) {
            this.glovesPanel.addItemToSlot(this.itemManager.getGloves());
        }

        if (this.itemManager.getPants() != null) {
            this.pantsPanel.addItemToSlot(this.itemManager.getPants());
        }

        if (this.itemManager.getRightRing() != null) {
            this.rightHandRingPanel.addItemToSlot(this.itemManager.getRightRing());
        }

        if (this.itemManager.getLeftRing() != null) {
            this.leftHandRingPanel.addItemToSlot(this.itemManager.getLeftRing());
        }

    }

    public void setupHoverPanel() {
        
        if (this.itemManager == null){
            System.out.println("Error");
            return;
        }

        for (Item item : this.itemManager.getAllItems()) {
            item.getItemImageLabel().addMouseListener(this.mouseListener);
        }
        
        this.hoverPanel.setItemManager(this.itemManager);
    }


    /**
     * used to track the item that is currently being dragged
     */
    // public void setDraggedItem(String name) {
        
    //     System.out.println("Dragged item " + name);

    //     EquipmentSlot slot;

    //     try {
    //         slot = EquipmentSlot.valueOf(name);
    //     } catch (EnumConstantNotPresentException ex) {
    //         System.out.println("Failed to set drag element");
    //         return;
    //     }

    //     // if (slot == EquipmentSlot.NEW_ITEM_SLOT) {
    //     //     this.draggedItem = this.itemManager.getGeneratedItem();
    //     // } else if (ItemManager.isInventorySlot(slot) == true) {
    //     //     System.out.println("Setting dragged item");
    //     //     try {
    //     //         this.draggedItem = this.itemManager.getInventoryItem(slot);
    //     //     } catch (Exception e) {
    //     //         System.out.println(e.getMessage()); 
    //     //     }
    //     //     System.out.println("Dragged item: " + this.draggedItem.getItemName());
    //     // }
       
    //     // else {
    //     //     this.draggedItem = this.itemManager.getUniqueItem(slot);
    //     // }

    //     // TODO: if this returns null, the program will crash
    //     this.draggedItem = this.itemManager.getItem(slot);

    // }

    /**
     * Group these method calls together because the item must be removed from both the itemManager and the gui panel
     */
    // public void removeItemSequence(ItemLayeredPane sourcePanel) {
        
    //     this.itemManager.removeItem(sourcePanel.equipmentSlot);
    //     sourcePanel.removeItemFromSlot();
        
    // }
    
    /**
     * group these method calls together since the item MUST be added to both itemManager and gui panel
     * @param targetPanel
     * @param draggedItem
     */
    // public void addItemSequence(ItemLayeredPane targetPanel, Item draggedItem) {

    //     this.itemManager.addItem(targetPanel.equipmentSlot, draggedItem);
    //     targetPanel.addItemToSlot(draggedItem);

    // }

    public void addItemToSlot(EquipmentSlot targetSlot, Item item) {

        ItemLayeredPane targetPanel = slotToPanel.get(targetSlot);
        targetPanel.addItemToSlot(item);

    }

    public void removeItemFromSlot(EquipmentSlot sourceSlot) {

        ItemLayeredPane sourcePanel = slotToPanel.get(sourceSlot);
        sourcePanel.removeItemFromSlot();

    }


    /**
     * This class subclasses TransferHandler and is used by labels which have the ability to be dragable
     */
    static class MyLabelTransferHandler extends TransferHandler {
        
        static ItemLayeredPane sourcePanel = null;

        public MyLabelTransferHandler() {}
        
        @Override
        protected Transferable createTransferable(JComponent c) {
            // ItemLayeredPane sourcePanel = null;
            Item item = null;
            
            if (c.getParent() instanceof ItemLayeredPane) {
                sourcePanel = (ItemLayeredPane) c.getParent();
                System.out.println(sourcePanel.slotLabel.getText());
                item = sourcePanel.getItem();
            }

            // else if () // grid

            return new ItemTransferable(item);
        }

        @Override
        public int getSourceActions(JComponent c) {
            return DnDConstants.ACTION_MOVE;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            
            // when the export is done we're going to remove the item from its old location then update stats
            // if (action == DnDConstants.ACTION_MOVE) {
                // this.itemPanel.removeItemSequence();
                // this.itemPanel.characterScreen.update();
            // }
        }

        public Transferable getTransferable(JComponent c) {
            return createTransferable(c);
        }

        public static ItemLayeredPane getSourcePanel() {
            return sourcePanel;
        }
    }

    /**
     * This class sublcasses Transfer Handler and is used by any panel that may import an item via item drag
     */
    static class ItemPanelTransferHandler extends TransferHandler {
        
        ItemPanel itemPanel;
        ItemController itemController;

        public ItemPanelTransferHandler(ItemPanel itemPanel) {
            this.itemPanel = itemPanel;
            this.itemController = itemPanel.itemController;  // TODO: fix this
        }

        @Override
        public boolean canImport(TransferSupport support) {

            ItemLayeredPane targetPanel;
            Item item;

            // System.out.println("Can Import?");
            if (support.isDataFlavorSupported(ItemTransferable.ITEM_FLAVOR) == false) {
                System.out.println("Wrong data flavor");
                return false;
            }
            
            try {
                targetPanel = (ItemLayeredPane) support.getComponent();
                item = (Item) support.getTransferable().getTransferData(ItemTransferable.ITEM_FLAVOR);
            } catch (Exception e) {
                System.out.println("Failed to cast objects");
                return false;
            }

            return this.itemPanel.itemManager.canEquipItem(targetPanel.getEquipmentSlot(), item);
            
            // make sure the item being dragged matches the itemslot type - rings are a special case because there
            // are two potential drop sites
            // if (this.itemPanel.draggedItem.getItemType() == ItemType.RING) {
            //     if ((targetPanel.equipmentSlot != EquipmentSlot.RIGHT_RING) 
            //     && (targetPanel.equipmentSlot != EquipmentSlot.LEFT_RING)
            //     && (targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_ONE)
            //     && (targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_TWO)
            //     && (targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_THREE)
            //     && (targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_FOUR)) {
            //         return false;
            //     }
            // // not a ring
            // } else { 

            //     if (this.itemPanel.draggedItem.getItemType().getEquipmentSlot() != targetPanel.equipmentSlot
            //         && targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_ONE
            //         && targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_TWO
            //         && targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_THREE
            //         && targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_FOUR) {
            //         return false;
            //     }
            // }

            // make sure the target panel is empty
            // if (targetPanel.isEmpty() == false) {
            //     return false;
            // }

            // return true;
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }
            try {
                ItemLayeredPane targetPanel = (ItemLayeredPane) support.getComponent();
                // System.out.println("Supports drop");
                Item draggedItem = (Item) support.getTransferable().getTransferData(ItemTransferable.ITEM_FLAVOR);

                // this.itemPanel.addItemSequence(targetPanel, draggedItem);
                // this.itemPanel.removeItemSequence(MyLabelTransferHandler.getSourcePanel());
                
                // we're equiping the item from another panel
                if (MyLabelTransferHandler.getSourcePanel() != null) {
                    this.itemController.moveItemToSlot(targetPanel.getEquipmentSlot(), 
                    MyLabelTransferHandler.getSourcePanel().getEquipmentSlot(), draggedItem);
                }

                this.itemPanel.characterScreen.update();
                this.itemPanel.hoverPanel.updateHoverWindow(draggedItem.getItemImageLabel());
                this.itemPanel.hoverPanel.showHoverPanel();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    static class ItemTransferable implements Transferable {

        public static final DataFlavor ITEM_FLAVOR = new DataFlavor(Item.class, "Item");
        private final Item item;

        public ItemTransferable(Item item) {
            this.item = item;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{ITEM_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(ITEM_FLAVOR);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (flavor.equals(ITEM_FLAVOR)) {
                return item;
            }
            throw new UnsupportedFlavorException(flavor);
        }

    }


    // static class ImageTransferable implements Transferable {

    //     private ImageIcon imageIcon;

    //     public ImageTransferable(ImageIcon imageIcon) {
    //         this.imageIcon = imageIcon;
    //     }

    //     @Override
    //     public DataFlavor[] getTransferDataFlavors() {
    //         return new DataFlavor[]{DataFlavor.imageFlavor};
    //     }

    //     @Override
    //     public boolean isDataFlavorSupported(DataFlavor flavor) {
    //         return flavor.equals(DataFlavor.imageFlavor);
    //     }

    //     @Override
    //     public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    //         if (flavor.equals(DataFlavor.imageFlavor)) {
    //             return imageIcon.getImage();
    //         }
    //         throw new UnsupportedFlavorException(flavor);
    //     }
    // }
}

