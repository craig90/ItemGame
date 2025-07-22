import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLayeredPane;

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

public class ItemWindow extends JLayeredPane {

    final static int ITEM_WINDOW_WIDTH = 700;
    final static int ITEM_WINDOW_HEIGHT = 700;

    // JLayeredPane layeredPane;

    JPanel itemPanel;
    // JPanel hoverPanel;
    HoverPanel hoverPanel;

    JLabel helm, weapon, amulet, ring, ringTwo, belt, pants, gloves, shieldLabel, chest, boots;

    ItemLayeredPane helmPanel, weaponPanel, amuletPanel, shieldPanel, rightHandRingPanel, leftHandRingPanel;
    ItemLayeredPane beltPanel, pantsPanel, glovesPanel, chestPanel, bootsPanel;

    ImageIcon helmIcon, shieldIcon;

    JButton getNewItemButton, deleteItemButton;
    ItemLayeredPane spareItemPanel, deleteItemPanel;

    CharacterScreen characterScreen;
    ItemGenerator itemGenerator;
    ItemManager itemManager;

    MyMouseListener mouseListener;

    ItemLayeredPane sourcePanel;
    Item draggedItem;

    public ItemWindow(CharacterScreen characterScreen) {

        this.itemGenerator = new ItemGenerator();

        // this.layeredPane = new JLayeredPane();

        this.itemPanel = new JPanel();
        this.itemPanel.setLayout(new GridBagLayout());

        this.characterScreen = characterScreen;

        // creates a constraints object
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        
        // helm
        this.helmPanel = new ItemLayeredPane(EquipmentSlot.HELM);
        c.gridx = 2;
        c.gridy = 0;
        this.itemPanel.add(this.helmPanel, c);

        // weapon
        this.weaponPanel = new ItemLayeredPane(EquipmentSlot.WEAPON);
        c.gridx = 0;
        c.gridy = 1;
        this.itemPanel.add(this.weaponPanel, c);

        // amulet
        this.amuletPanel = new ItemLayeredPane(EquipmentSlot.AMULET);
        c.gridx = 3;
        c.gridy = 0;
        this.itemPanel.add(this.amuletPanel, c);
        
        // ring
        this.rightHandRingPanel = new ItemLayeredPane(EquipmentSlot.RIGHT_RING);
        c.gridx = 1;
        c.gridy = 2;
        this.itemPanel.add(this.rightHandRingPanel, c);
        
        this.leftHandRingPanel = new ItemLayeredPane(EquipmentSlot.LEFT_RING);
        c.gridx = 3;
        c.gridy = 2;
        this.itemPanel.add(this.leftHandRingPanel, c);

        // chest panel
        this.chestPanel = new ItemLayeredPane(EquipmentSlot.CHEST);
        c.gridx = 2;
        c.gridy = 1;
        this.itemPanel.add(this.chestPanel, c);

        // gloves
        this.glovesPanel = new ItemLayeredPane(EquipmentSlot.GLOVES);
        c.gridx = 0;
        c.gridy = 2;
        this.itemPanel.add(this.glovesPanel, c);

        // shield
        this.shieldPanel = new ItemLayeredPane(EquipmentSlot.SHIELD);
        c.gridx = 5;
        c.gridy = 1;
        this.itemPanel.add(this.shieldPanel, c);

        // belt
        this.beltPanel = new ItemLayeredPane(EquipmentSlot.BELT);
        c.gridx = 2;
        c.gridy = 2;
        this.itemPanel.add(this.beltPanel, c);

        // pants
        this.pantsPanel = new ItemLayeredPane(EquipmentSlot.PANTS);
        c.gridx = 4;
        c.gridy = 1;
        this.itemPanel.add(this.pantsPanel, c);

        // boots
        this.bootsPanel = new ItemLayeredPane(EquipmentSlot.BOOTS);
        c.gridx = 4;
        c.gridy = 2;
        this.itemPanel.add(this.bootsPanel, c);

        // generate new item button
        this.getNewItemButton = new JButton("Get New Item");
        c.gridx = 0;
        c.gridy = 5;
        this.itemPanel.add(this.getNewItemButton, c);

        // holder for extra item
        this.spareItemPanel = new ItemLayeredPane(EquipmentSlot.NEW_ITEM_SLOT);
        c.gridx = 0;
        c.gridy = 4;
        this.itemPanel.add(this.spareItemPanel, c);

        this.deleteItemButton = new JButton("Delete Item");
        c.gridx = 2;
        c.gridy = 5;
        this.itemPanel.add(this.deleteItemButton, c);

        this.deleteItemPanel = new ItemLayeredPane(EquipmentSlot.DELETE_ITEM_SLOT);
        c.gridx = 2;
        c.gridy = 4;
        this.itemPanel.add(this.deleteItemPanel, c);

        // the size has to be set for layered Panes 
        this.itemPanel.setBounds(0, 0, ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);
        this.setSize(ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);

        this.hoverPanel = new HoverPanel();

        this.add(this.itemPanel, 1);
        this.itemPanel.setBackground(Color.GREEN);

        this.add(this.hoverPanel, 0);

        this.setVisible(true);

        this.mouseListener = new MyMouseListener(this);

        this.getNewItemButton.addActionListener(e -> {
            Item newItem;
            try {
                newItem = this.itemGenerator.createNewItem();
                this.itemManager.setGeneratedItem(newItem);
            } catch (Exception ex) {
                System.out.println("Error creating new item");
                return;
            }
            
            this.itemManager.getGeneratedItem().getItemImageLabel().addMouseListener(this.mouseListener);
            this.spareItemPanel.addItemToSlot(this.itemManager.getGeneratedItem());
            setSourceTransferHandler(this.itemManager.getGeneratedItem());
            
        });

        // delete the item
        this.deleteItemButton.addActionListener(e -> {
            this.itemManager.setRemovedItem(null);
            this.deleteItemPanel.removeItemFromSlot();
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
        this.deleteItemPanel.setTransferHandler(new MyPanelTransferHandler(this));

    }


    public void setSourceTransferHandler(Item item) {

        item.itemImageLabel.setTransferHandler(new MyLabelTransferHandler(this));
        item.itemImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, DnDConstants.ACTION_MOVE);
            }
        });
        
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

        this.helmPanel.removeItemFromSlot();
        this.weaponPanel.removeItemFromSlot();
        this.amuletPanel.removeItemFromSlot();
        this.chestPanel.removeItemFromSlot();
        this.shieldPanel.removeItemFromSlot();
        this.beltPanel.removeItemFromSlot();
        this.glovesPanel.removeItemFromSlot();
        this.pantsPanel.removeItemFromSlot();
        this.rightHandRingPanel.removeItemFromSlot();
        this.leftHandRingPanel.removeItemFromSlot();
        this.spareItemPanel.removeItemFromSlot();
        this.deleteItemPanel.removeItemFromSlot();
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

        if (slot == EquipmentSlot.NEW_ITEM_SLOT) {
            this.draggedItem = this.itemManager.getGeneratedItem();
        } else if (slot == EquipmentSlot.DELETE_ITEM_SLOT) {
            System.out.println("Setting dragged item");
            this.draggedItem = this.itemManager.getRemovedItem();
            System.out.println("Dragged item: " + this.draggedItem.getItemName());
        }
       
        else {
            this.draggedItem = this.itemManager.getUniqueItem(slot);
        }

    }


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
            if (action == DnDConstants.ACTION_MOVE) {
                this.itemWindow.sourcePanel.removeItemFromSlot();
            }
        }
    }

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
            if (this.itemWindow.draggedItem.getItemType() == ItemType.RING) {
                if ((targetPanel.equipmentSlot != EquipmentSlot.RIGHT_RING) 
                && (targetPanel.equipmentSlot != EquipmentSlot.LEFT_RING)
                && (targetPanel.equipmentSlot != EquipmentSlot.DELETE_ITEM_SLOT)) {
                    return false;
                }
            } else {
                if (this.itemWindow.draggedItem.getItemType().getEquipmentSlot() != targetPanel.equipmentSlot
                    && targetPanel.equipmentSlot != EquipmentSlot.DELETE_ITEM_SLOT) {
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
                System.out.println("Supports drop");
                Item draggedItem = this.itemWindow.getDraggedItem();

                // see if we're moving an item to the deletion slot
                if (targetPanel.equipmentSlot == EquipmentSlot.DELETE_ITEM_SLOT) {
                    
                    if (draggedItem.getItemType().getEquipmentSlot() == EquipmentSlot.NEW_ITEM_SLOT) {
                        this.itemWindow.itemManager.setGeneratedItem(null);
                    } else {
                        // logic is slightly different for rings
                        if (draggedItem instanceof Ring == true) {
                            Ring ring = (Ring) draggedItem;
                            this.itemWindow.itemManager.removeItem(ring.getRingSlot());
                        } else {
                            this.itemWindow.itemManager.removeItem(draggedItem.getItemType().getEquipmentSlot());
                        }
                    }
                    this.itemWindow.itemManager.setRemovedItem(draggedItem);

                } else {
                    System.out.println("Target Panel: " + targetPanel.equipmentSlot.toString());
                    this.itemWindow.itemManager.equipItem(draggedItem, targetPanel.equipmentSlot);
                }
                
                this.itemWindow.characterScreen.update();
                targetPanel.addItemToSlot(draggedItem);
                
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

