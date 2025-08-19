import javax.swing.JPanel;
import javax.swing.TransferHandler;

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
import java.util.Collection;
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

    final static int ITEM_WINDOW_WIDTH = 775;
    final static int ITEM_WINDOW_HEIGHT = 475;

    private JPanel equippedItemPanel;
    private InventoryPanel inventoryPanel;
    private JPanel generateDeleteItemPanel;

    private HoverPanel hoverPanel;

    private ItemLayeredPane helmPanel, weaponPanel, amuletPanel, shieldPanel, rightHandRingPanel, leftHandRingPanel;
    private ItemLayeredPane beltPanel, pantsPanel, glovesPanel, chestPanel, bootsPanel;
    private ItemLayeredPane generatedItemPanel, deleteItemPanel;
    
    private JButton getNewItemButton, deleteItemButton;
    
    private CharacterScreen characterScreen;
    
    private ItemController itemController;

    private MyMouseListener mouseListener;

    private final Map <EquipmentSlot, ItemLayeredPane> slotToPanel = new EnumMap<>(EquipmentSlot.class);

    public ItemPanel(CharacterScreen characterScreen, ItemController itemController) {

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




        this.inventoryPanel = new InventoryPanel(this.itemController, InventoryPanel.GRID_SIZE, Inventory.INVENTORY_ROWS, Inventory.INVENTORY_COLUMNS);
        
        this.generateDeleteItemPanel = new JPanel();
        this.generateDeleteItemPanel.setLayout(new GridBagLayout());
        
        this.generatedItemPanel = new ItemLayeredPane(EquipmentSlot.NEW_ITEM_SLOT);
        c.gridx = 0;
        c.gridy = 0;
        this.generateDeleteItemPanel.add(this.generatedItemPanel, c);
        
        this.getNewItemButton = new JButton("Get New Item");
        c.gridx = 0;
        c.gridy = 1;
        this.generateDeleteItemPanel.add(this.getNewItemButton, c);
        
        this.deleteItemPanel = new ItemLayeredPane(EquipmentSlot.DELETE_ITEM_SLOT);
        c.gridx = 1;
        c.gridy = 0;
        this.generateDeleteItemPanel.add(this.deleteItemPanel, c);

        this.deleteItemButton = new JButton("Delete Item");
        c.gridx = 1;
        c.gridy = 1;
        this.generateDeleteItemPanel.add(this.deleteItemButton, c);
        

        // the size has to be set for layered Panes 
        this.equippedItemPanel.setBounds(0, 0, ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);
        this.inventoryPanel.setBounds(0, ITEM_WINDOW_HEIGHT, InventoryPanel.GRID_SIZE*Inventory.INVENTORY_COLUMNS, InventoryPanel.GRID_SIZE*Inventory.INVENTORY_ROWS);
        this.generateDeleteItemPanel.setBounds(InventoryPanel.GRID_SIZE*Inventory.INVENTORY_COLUMNS, ITEM_WINDOW_HEIGHT,ITEM_WINDOW_WIDTH-(InventoryPanel.GRID_SIZE*Inventory.INVENTORY_COLUMNS),InventoryPanel.GRID_SIZE*Inventory.INVENTORY_ROWS);
        this.setSize(ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);

        this.hoverPanel = new HoverPanel(this.itemController);

        this.add(this.equippedItemPanel, 1);
        this.add(this.inventoryPanel, 1);
        this.add(this.generateDeleteItemPanel, 1);
        this.generateDeleteItemPanel.setBackground(new Color(190, 190, 190));
        this.inventoryPanel.setBackground(new Color(180, 180, 180));
        this.equippedItemPanel.setBackground(new Color(169, 169, 169));

        this.add(this.hoverPanel, 0);

        this.setVisible(true);

        this.mouseListener = new MyMouseListener(this);

        this.getNewItemButton.addActionListener(e -> {
            
            Item newItem;
            try {
                newItem = this.itemController.generateNewItem();
            } catch (Exception ex) {
                System.out.println("Error creating new item");
                return;
            }
            
            // TODO: this should probably be moved
            this.generatedItemPanel.addItemToSlot(newItem);
            setSourceTransferHandler(newItem);
            newItem.getItemImageLabel().addMouseListener(this.mouseListener);

        });

        // delete the item
        this.deleteItemButton.addActionListener(e -> {
            this.itemController.deleteItem();
        });

        // put all the ItemLayeredPanes (slots) into a map for dynmaic access and manipulation
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
        slotToPanel.put(EquipmentSlot.DELETE_ITEM_SLOT, this.deleteItemPanel);

        // add the transferhandlers for the panels
        Collection<ItemLayeredPane> slots = this.slotToPanel.values();
        for (ItemLayeredPane slot : slots) {
            slot.setTransferHandler(new ItemPanelTransferHandler(this));
        }

        inventoryPanel.setTransferHandler(new InventoryTransferHandler(this));

    }


    /**
     * Sets the transfer handler on the Item image label so it can be redrawn while being dragged
     * @param item
     */
    public void setSourceTransferHandler(Item item) {

        item.itemImageLabel.setTransferHandler(new ItemTransferHandler());

        HoverPanel hoverPanel = this.hoverPanel;

        DragSource dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(item.itemImageLabel, DnDConstants.ACTION_MOVE, new DragGestureListener() {
            @Override
            public void dragGestureRecognized(DragGestureEvent dge) {
                
                hoverPanel.hideHoverPanel(); // hide the hover panel when the drag starts

                TransferHandler th = item.itemImageLabel.getTransferHandler();
                if (!(th instanceof ItemTransferHandler)) return;

                Transferable transferable = ((ItemTransferHandler) th).getTransferable(item.itemImageLabel);
                
                if (transferable == null) return;

                // Create custom drag image
                Image dragImage = createDragImage(item.itemImageLabel);

                // Start drag with image
                dragSource.startDrag(dge, DragSource.DefaultMoveDrop, dragImage, new Point(0, 0), transferable,
                    new DragSourceAdapter() {
                        @Override
                        public void dragDropEnd(DragSourceDropEvent dsde) {
                            ((ItemTransferHandler) th).exportDone((JComponent)item.itemImageLabel, transferable, 
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

        ArrayList<Item> items = (ArrayList<Item>) this.itemController.getAllItems();

        for (Item item : items) {
            setSourceTransferHandler(item);
        }

    }


    /**
     * Calls the vairous helper methods, clears out the panes, set the new items, set of transfer handlers,
     * sets up hover panel
     * @param itemManager
     */
    public void loadItems() {
        
        // panels
        clearItemPanes();
        setItemsInPanes();
        
        // inventory - first remove all of the items from the previous Character
        // then load the items from the current Character
        this.inventoryPanel.removeAllItems();
        this.inventoryPanel.addItems(); 

        // make these calls once everything has been set up in the right spot
        setSourceTransferHandlers();
        setupHoverPanel();


    }
    
    // public void setItemManager(ItemManager itemManager) {
    //     this.itemManager = itemManager;
    // }

    public void deleteItem() {
        this.deleteItemPanel.removeItemFromSlot();
    }


    public InventoryPanel getInventoryPanel() {
        return this.inventoryPanel;
    }

    public ItemController getItemController() {
        return this.itemController;
    }

    public HoverPanel getHoverPanel() {
        return this.hoverPanel;
    }

    /**
     * removes the items from the panes - used when reloading a character
     */
    public void clearItemPanes() {

        Collection<ItemLayeredPane> slots = this.slotToPanel.values();
        
        for (ItemLayeredPane slot : slots) {
            slot.removeItemFromSlot();
        }

    }
    
    
    /**
     * sets whichever items the character has in their respective item panes
     */
    public void setItemsInPanes() {

        for (Map.Entry<EquipmentSlot, ItemLayeredPane> entry : slotToPanel.entrySet()) {
            if (this.itemController.getItem(entry.getKey()) != null) {
                entry.getValue().addItemToSlot(this.itemController.getItem(entry.getKey()));
            }
        }

    }

    public void setupHoverPanel() {
        
        for (Item item : this.itemController.getAllItems()) {
            item.getItemImageLabel().addMouseListener(this.mouseListener);
        }
        
    }

    /**
     * After the item has been sucessfully dragged, Character stats need to be updated and the hover panel should
     * be updated and shown
     * @param item
     */
    public void dragFinishUpdate(Item item) {
        this.characterScreen.update();
        this.hoverPanel.updateHoverWindow(item.getItemImageLabel());
        this.hoverPanel.showHoverPanel();
    }


    /**
     * Adds an item to the target equipment slot
     * @param targetSlot
     * @param item
     */
    public void addItemToSlot(EquipmentSlot targetSlot, Item item) {

        ItemLayeredPane targetPanel = slotToPanel.get(targetSlot);
        targetPanel.addItemToSlot(item);

    }

    /**
     * removes an item from the source equipment slot
     * @param sourceSlot
     */
    public void removeItemFromSlot(EquipmentSlot sourceSlot) {

        ItemLayeredPane sourcePanel = slotToPanel.get(sourceSlot);
        sourcePanel.removeItemFromSlot();

    }


    /**
     * This class subclasses TransferHandler and is used by labels which have the ability to be dragable
     */
    static class ItemTransferHandler extends TransferHandler {
        
        public enum SourceTypes {
            ItemPanel,
            InventoryPanel
        }

        static SourceTypes sourceType;

        static ItemLayeredPane itemPanelSource = null;
        static InventoryPanel inventoryPanelSource = null;

        public ItemTransferHandler() {}
        
        @Override
        protected Transferable createTransferable(JComponent c) {
            // ItemLayeredPane sourcePanel = null;
            Item item = null;
            
            if (c.getParent() instanceof ItemLayeredPane) {
                itemPanelSource = (ItemLayeredPane) c.getParent();
                System.out.println(itemPanelSource.slotLabel.getText());
                item = itemPanelSource.getItem();
                sourceType = SourceTypes.ItemPanel;
            }
            
            else if (c.getParent() instanceof InventoryPanel) {
                // System.out.println("X: " + c.getX()/InventoryPanel.GRID_SIZE + " Y " + c.getY()/InventoryPanel.GRID_SIZE);
                inventoryPanelSource = (InventoryPanel) c.getParent();
                item = inventoryPanelSource.getItem(c.getX()/InventoryPanel.GRID_SIZE, c.getY()/InventoryPanel.GRID_SIZE);
                sourceType = SourceTypes.InventoryPanel;
            }

            // System.out.println("Does it create a new transferablle?");
            // else if () // grid

            return new ItemTransferable(item);
        }

        @Override
        public int getSourceActions(JComponent c) {
            return DnDConstants.ACTION_MOVE;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {}

        public Transferable getTransferable(JComponent c) {
            return createTransferable(c);
        }

        public static ItemLayeredPane getItemSourcePanel() {
            return itemPanelSource;
        }

        public static InventoryPanel getInventorySourcePanel() {
            return inventoryPanelSource;
        }

        public static SourceTypes getSourceType() {
            return sourceType;
        }
    }

    static class InventoryTransferHandler extends TransferHandler {

        ItemController itemController;
        ItemPanel itemPanel;

        public InventoryTransferHandler(ItemPanel itemPanel) {
            this.itemController = itemPanel.itemController;
            this.itemPanel = itemPanel;
        }
    
        /**
         * calulates the X axis from the cursor location
         * @param support
         * @return
         */
        private int calcX(TransferSupport support) {

            System.out.println("X: " + support.getDropLocation().getDropPoint().getX());
            int x = (int)support.getDropLocation().getDropPoint().getX();
            int gridx = x / InventoryPanel.GRID_SIZE;

            return gridx;

        }

        /**
         * caluclates the y access cursor location
         * @param support
         * @return
         */
        private int calcY(TransferSupport support) {

            System.out.println("Y: " + support.getDropLocation().getDropPoint().getY());
            int y = (int)support.getDropLocation().getDropPoint().getY();
            int gridy = y / InventoryPanel.GRID_SIZE;

            return gridy;

        }

        @Override
        public boolean canImport(TransferSupport support) {

            System.out.println("Can import?");

            Item item = null;

            if (support.isDataFlavorSupported(ItemTransferable.ITEM_FLAVOR) == false) {
                System.out.println("Wrong data flavor");
                return false;
            }

            try {
                item = (Item) support.getTransferable().getTransferData(ItemTransferable.ITEM_FLAVOR);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }

            if (item == null) {
                System.out.println("Item is null here");
                return false;
            }

            if (!this.itemController.canAddItem(item, calcX(support), calcY(support))) {
                return false;
            }

            System.out.println("Can add item");

            return true;

        }

        @Override
        public boolean importData(TransferSupport support) {
            
            System.out.println("Attempting import.. need to see where it's failing");

            Item item = null;

            if (!canImport(support)) {
                return false;
            }

            try {
                item = (Item) support.getTransferable().getTransferData(ItemTransferable.ITEM_FLAVOR);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }

            if (ItemTransferHandler.getSourceType() == ItemPanel.ItemTransferHandler.SourceTypes.ItemPanel) {
                this.itemController.moveItemToInventory(item, calcX(support), calcY(support), 
                ItemTransferHandler.getItemSourcePanel().getEquipmentSlot());
            } else if (ItemTransferHandler.getSourceType() == ItemPanel.ItemTransferHandler.SourceTypes.InventoryPanel) {
                this.itemController.moveItemToInventoryFromInventory(item, calcX(support), calcY(support));    
            } else {
                return false; // Transfer failed since we don't have a correct source type
            }

            this.itemPanel.dragFinishUpdate(item);

            return true;
            
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
            this.itemController = itemPanel.getItemController();
        }

        @Override
        public boolean canImport(TransferSupport support) {

            ItemLayeredPane targetPanel;
            Item item;

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

            return this.itemController.canEquipItem(targetPanel.getEquipmentSlot(), item);
            
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }
            try {
                ItemLayeredPane targetPanel = (ItemLayeredPane) support.getComponent();
                // System.out.println("Supports drop");
                Item item = (Item) support.getTransferable().getTransferData(ItemTransferable.ITEM_FLAVOR);

                if (ItemTransferHandler.getSourceType() == ItemPanel.ItemTransferHandler.SourceTypes.ItemPanel) {
                    this.itemController.moveItemToSlot(targetPanel.getEquipmentSlot(), 
                    ItemTransferHandler.getItemSourcePanel().getEquipmentSlot(), item);
                } else if (ItemTransferHandler.getSourceType() == ItemPanel.ItemTransferHandler.SourceTypes.InventoryPanel) {
                    this.itemController.moveItemToSlotFromInventory(targetPanel.getEquipmentSlot(), item);
                } else {
                    return false; // there are no other Panel types... transfer can't be completed
                }

                this.itemPanel.dragFinishUpdate(item);
                                
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

}

