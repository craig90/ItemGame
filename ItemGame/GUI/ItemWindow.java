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

public class ItemWindow extends JLayeredPane {

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

    CharacterScreen characterScreen;
    ItemGenerator itemGenerator;
    ItemManager itemManager;

    MyMouseListener mouseListener;

    JSeparator separator;

    ItemLayeredPane sourcePanel;
    Item draggedItem;

    public ItemWindow(CharacterScreen characterScreen) {

        this.itemGenerator = new ItemGenerator();

        // this.layeredPane = new JLayeredPane();

        this.equippedItemPanel = new JPanel();
        this.equippedItemPanel.setLayout(new GridBagLayout());

        this.characterScreen = characterScreen;

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

        // these are the transfer handlers that recieve an item
        this.helmPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.weaponPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.amuletPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.shieldPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.rightHandRingPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.leftHandRingPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.beltPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.pantsPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.glovesPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.chestPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.bootsPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.inventorySlotOnePanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.inventorySlotTwoPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.inventorySlotThreePanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.inventorySlotFourPanel.setTransferHandler(new MyPanelTransferHandler(this));

    }


    public void setSourceTransferHandler(Item item) {

        item.itemImageLabel.setTransferHandler(new MyLabelTransferHandler(this));
        // item.itemImageLabel.addMouseListener(new MouseAdapter() {
        //     @Override
        //     public void mousePressed(MouseEvent e) {
        //         JComponent c = (JComponent) e.getSource();
        //         TransferHandler handler = c.getTransferHandler();
        //         handler.exportAsDrag(c, e, DnDConstants.ACTION_MOVE);
        //     }
        // });


        DragSource dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(item.itemImageLabel, DnDConstants.ACTION_MOVE, new DragGestureListener() {
            @Override
            public void dragGestureRecognized(DragGestureEvent dge) {
                
                
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

            // public void dragDropEnd(DragSourceDropEvent dsde) {
            //     if (dsde.getDropSuccess() == true) {
            //         System.out.println("Successful drop");
            //     }
            // }
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


    public void setSourcePanel(ItemLayeredPane panel) {
        this.sourcePanel = panel;
    }

    public void initializeDrag(JLabel label) {
        System.out.println("Initializing drag");

        setDraggedItem(label.getName());
        setSourcePanel((ItemLayeredPane)label.getParent());
        hoverPanel.hideHoverPanel();
    }

    /**
     * gets the item that is being dragged
     * @return
     */
    public Item getDraggedItem() {
        return this.draggedItem;
    }


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
    public void setDraggedItem(String name) {
        
        System.out.println("Dragged item " + name);

        EquipmentSlot slot;

        try {
            slot = EquipmentSlot.valueOf(name);
        } catch (EnumConstantNotPresentException ex) {
            System.out.println("Failed to set drag element");
            return;
        }

        // if (slot == EquipmentSlot.NEW_ITEM_SLOT) {
        //     this.draggedItem = this.itemManager.getGeneratedItem();
        // } else if (ItemManager.isInventorySlot(slot) == true) {
        //     System.out.println("Setting dragged item");
        //     try {
        //         this.draggedItem = this.itemManager.getInventoryItem(slot);
        //     } catch (Exception e) {
        //         System.out.println(e.getMessage()); 
        //     }
        //     System.out.println("Dragged item: " + this.draggedItem.getItemName());
        // }
       
        // else {
        //     this.draggedItem = this.itemManager.getUniqueItem(slot);
        // }

        // TODO: if this returns null, the program will crash
        this.draggedItem = this.itemManager.getItem(slot);

    }

    /**
     * Group these method calls together because the item must be removed from both the itemManager and the gui panel
     */
    public void removeItemSequence() {
        
        this.itemManager.removeItem(this.sourcePanel.equipmentSlot);
        this.sourcePanel.removeItemFromSlot();
        
    }
    
    /**
     * group these method calls together since the item MUST be added to both itemManager and gui panel
     * @param targetPanel
     * @param draggedItem
     */
    public void addItemSequence(ItemLayeredPane targetPanel, Item draggedItem) {

        this.itemManager.addItem(targetPanel.equipmentSlot, draggedItem);
        targetPanel.addItemToSlot(draggedItem);

    }

    /**
     * This class subclasses TransferHandler and is used by labels which have the ability to be dragable
     */
    static class MyLabelTransferHandler extends TransferHandler {
        
        ItemWindow itemWindow;

        public MyLabelTransferHandler(ItemWindow itemWindow) {
            this.itemWindow = itemWindow;
        }
        
        @Override
        protected Transferable createTransferable(JComponent c) {
            JLabel label = (JLabel) c;
            this.itemWindow.initializeDrag(label);
            return new ImageTransferable((ImageIcon)label.getIcon());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return DnDConstants.ACTION_MOVE;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            
            // when the export is done we're going to remove the item from its old location then update stats
            if (action == DnDConstants.ACTION_MOVE) {
                this.itemWindow.removeItemSequence();
                this.itemWindow.characterScreen.update();
            }
        }

        public Transferable getTransferable(JComponent c) {
            return createTransferable(c);
        }
    }

    /**
     * This class sublcasses Transfer Handler and is used by any panel that may import an item via item drag
     */
    static class MyPanelTransferHandler extends TransferHandler {
        
        ItemWindow itemWindow;

        public MyPanelTransferHandler(ItemWindow itemWindow) {
            this.itemWindow = itemWindow;
        }

        @Override
        public boolean canImport(TransferSupport support) {

            ItemLayeredPane targetPanel;

            if (support.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.imageFlavor) == false) {
                return false;
            }
            
            try {
                targetPanel = (ItemLayeredPane) support.getComponent();
            } catch (Exception e) {
                return false;
            }
            
            // make sure the item being dragged matches the itemslot type - rings are a special case because there
            // are two potential drop sites
            // TODO: this needs to be cleaned up
            if (this.itemWindow.draggedItem.getItemType() == ItemType.RING) {
                if ((targetPanel.equipmentSlot != EquipmentSlot.RIGHT_RING) 
                && (targetPanel.equipmentSlot != EquipmentSlot.LEFT_RING)
                && (targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_ONE)
                && (targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_TWO)
                && (targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_THREE)
                && (targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_ONE)) {
                    return false;
                }
            // not a ring
            } else { 

                if (this.itemWindow.draggedItem.getItemType().getEquipmentSlot() != targetPanel.equipmentSlot
                    && targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_ONE
                    && targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_TWO
                    && targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_THREE
                    && targetPanel.equipmentSlot != EquipmentSlot.INVENTORY_SLOT_FOUR) {
                    return false;
                }
            }

            // make sure the target panel is empty
            if (targetPanel.isEmpty() == false) {
                return false;
            }

            return true;
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }
            try {
                ItemLayeredPane targetPanel = (ItemLayeredPane) support.getComponent();
                // System.out.println("Supports drop");
                Item draggedItem = this.itemWindow.getDraggedItem();

                this.itemWindow.addItemSequence(targetPanel, draggedItem);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    static class ImageTransferable implements Transferable {

        private ImageIcon imageIcon;

        public ImageTransferable(ImageIcon imageIcon) {
            this.imageIcon = imageIcon;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(DataFlavor.imageFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (flavor.equals(DataFlavor.imageFlavor)) {
                return imageIcon.getImage();
            }
            throw new UnsupportedFlavorException(flavor);
        }
    }
}

