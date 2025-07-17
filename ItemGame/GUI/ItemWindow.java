import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLayeredPane;

import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.awt.event.MouseEvent;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

public class ItemWindow extends JFrame {

    final static int ITEM_WINDOW_WIDTH = 700;
    final static int ITEM_WINDOW_HEIGHT = 700;

    JLayeredPane layeredPane;

    JPanel itemPanel;
    // JPanel hoverPanel;
    HoverPanel hoverPanel;

    JLabel helm, weapon, amulet, ring, ringTwo, belt, pants, gloves, shieldLabel, chest, boots;

    ItemLayeredPane helmPanel, weaponPanel, amuletPanel, shieldPanel, rightHandRingPanel, leftHandRingPanel;
    ItemLayeredPane beltPanel, pantsPanel, glovesPanel, chestPanel, bootsPanel;

    ImageIcon helmIcon, shieldIcon;

    JButton getNewItemButton;
    ItemLayeredPane spareItemPanel;

    CharacterScreen characterScreen;
    // Character character;
    ItemGenerator itemGenerator;
    ItemManager itemManager;


    ItemLayeredPane sourcePanel;
    Item draggedItem;
    Item newItem; // TODO: this needs to be moved to another class, but we'll put it here for now

    public ItemWindow(CharacterScreen characterScreen, ItemManager itemManager) {

        this.characterScreen = characterScreen;
        // this.character = character;
        this.itemManager = itemManager;
        this.itemGenerator = new ItemGenerator();

        this.setTitle("Items");
        this.setSize(ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);

        this.layeredPane = new JLayeredPane();

        this.itemPanel = new JPanel();
        this.itemPanel.setLayout(new GridBagLayout());

        // creates a constraints object
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        
        // helm
        this.helmPanel = new ItemLayeredPane(EquipmentSlot.HELM, this.itemManager.getHelm());
        c.gridx = 2;
        c.gridy = 0;
        this.itemPanel.add(this.helmPanel, c);

        // weapon
        this.weaponPanel = new ItemLayeredPane(EquipmentSlot.WEAPON, this.itemManager.getWeapon());
        c.gridx = 0;
        c.gridy = 1;
        this.itemPanel.add(this.weaponPanel, c);

        // amulet
        this.amuletPanel = new ItemLayeredPane(EquipmentSlot.AMULET, this.itemManager.getAmulet());
        c.gridx = 3;
        c.gridy = 0;
        this.itemPanel.add(this.amuletPanel, c);
        
        // ring
        this.rightHandRingPanel = new ItemLayeredPane(EquipmentSlot.RIGHT_RING, this.itemManager.getRightRing());
        c.gridx = 1;
        c.gridy = 2;
        this.itemPanel.add(this.rightHandRingPanel, c);
        
        this.leftHandRingPanel = new ItemLayeredPane(EquipmentSlot.LEFT_RING, this.itemManager.getLeftRing());
        c.gridx = 3;
        c.gridy = 2;
        this.itemPanel.add(this.leftHandRingPanel, c);

        // chest panel
        this.chestPanel = new ItemLayeredPane(EquipmentSlot.CHEST, this.itemManager.getChest());
        c.gridx = 2;
        c.gridy = 1;
        this.itemPanel.add(this.chestPanel, c);

        // gloves
        this.glovesPanel = new ItemLayeredPane(EquipmentSlot.GLOVES, this.itemManager.getGloves());
        c.gridx = 0;
        c.gridy = 2;
        this.itemPanel.add(this.glovesPanel, c);

        // shield
        this.shieldPanel = new ItemLayeredPane(EquipmentSlot.SHIELD, this.itemManager.getShield());
        c.gridx = 4;
        c.gridy = 1;
        this.itemPanel.add(this.shieldPanel, c);

        // belt
        this.beltPanel = new ItemLayeredPane(EquipmentSlot.BELT, this.itemManager.getBelt());
        c.gridx = 2;
        c.gridy = 2;
        this.itemPanel.add(this.beltPanel, c);

        // pants
        this.pantsPanel = new ItemLayeredPane(EquipmentSlot.PANTS, this.itemManager.getPants());
        c.gridx = 2;
        c.gridy = 3;
        this.itemPanel.add(this.pantsPanel, c);

        // boots
        this.bootsPanel = new ItemLayeredPane(EquipmentSlot.BOOTS, this.itemManager.getBoots());
        c.gridx = 4;
        c.gridy = 2;
        this.itemPanel.add(this.bootsPanel, c);

        // generate new item button
        this.getNewItemButton = new JButton("Get New Item");
        c.gridx = 5;
        c.gridy = 2;
        this.itemPanel.add(this.getNewItemButton, c);

        // holder for extra item
        this.spareItemPanel = new ItemLayeredPane(EquipmentSlot.NEW_ITEM_SLOT, null);
        c.gridx = 5;
        c.gridy = 1;
        this.itemPanel.add(this.spareItemPanel, c);

        generateItemIcons();
        setItemsInPanes();

        this.itemPanel.setBounds(0, 0, ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);

        this.add(this.layeredPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        this.setLocationRelativeTo(this);

        this.hoverPanel = new HoverPanel(this.itemManager, this.spareItemPanel);

        this.layeredPane.add(this.itemPanel, 1);
        this.layeredPane.add(this.hoverPanel, 0);

        this.setVisible(true);

        MyMouseListener mouseListener = new MyMouseListener(this);

        // add mouse listeners to the current item image icons
        for (Item item : this.itemManager.getAllItems()) {
            item.getItemImageLabel().addMouseListener(mouseListener);
        }

        this.getNewItemButton.addActionListener(e -> {
            try {
                this.newItem = this.itemGenerator.createNewItem();
            } catch (Exception ex) {
                System.out.println("Error creating new item");
                return;
            }
            this.newItem.setImageLabel(); // TODO: this should be called in a better place - should be called at item creation
            this.newItem.getItemImageLabel().addMouseListener(mouseListener);
            this.spareItemPanel.addItemToSlot(this.newItem);
            this.spareItemPanel.itemIconLabel.setTransferHandler(new MyLabelTransferHandler(this));
            this.spareItemPanel.itemIconLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JComponent c = (JComponent) e.getSource();
                    TransferHandler handler = c.getTransferHandler();
                    handler.exportAsDrag(c, e, DnDConstants.ACTION_MOVE);
                }
            });
            
        });


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

    }


    /**
     * TODO: I'm not sure if this should be done here, but this will generate the Image Icons for currently 
     * equippied items
     * this should probably be done when the game initializes
     * 
     */
    private void generateItemIcons() {

        for (Item item : this.itemManager.getAllItems() ) {
            item.setImageLabel();
        }

    }

    /**
     * used to track the item that is currently being dragged
     */
    public void setDraggedItem(String name) {
        
        
        EquipmentSlot slot;

        try {
            slot = EquipmentSlot.valueOf(name);
        } catch (EnumConstantNotPresentException ex) {
            System.out.println("Failed to set drag element");
            return;
        }

        if (slot == EquipmentSlot.NEW_ITEM_SLOT) {
            this.draggedItem = this.newItem;
        } 
       
        // TODO: add something for rings and blings        
        else {
            this.draggedItem = this.itemManager.getUniqueItem(slot);
        }

    }

    public void setSourcePanel(ItemLayeredPane panel) {
        this.sourcePanel = panel;
    }

    public void initializeDrag(JLabel label) {
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
     * sets whichever items the character has in their respective item panes
     */
    private void setItemsInPanes() {

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
                && (targetPanel.equipmentSlot != EquipmentSlot.LEFT_RING)) {
                    return false;
                }
            } else {
                if (this.itemWindow.draggedItem.getItemType().getEquipmentSlot() != targetPanel.equipmentSlot) {
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
                //Image data = (Image) support.getTransferable().getTransferData(java.awt.datatransfer.DataFlavor.imageFlavor);
                ItemLayeredPane targetPanel = (ItemLayeredPane) support.getComponent();
                System.out.println("Supports drop");
                //targetPanel.add(new JLabel(data + " (dropped)"));
                Item draggedItem = this.itemWindow.getDraggedItem();

                // logic is a bit more complicated for rings
                // TODO: I wonder if this is really necessary
                if (draggedItem instanceof Ring) {
                    if (targetPanel.equipmentSlot == EquipmentSlot.RIGHT_RING) {
                        this.itemWindow.itemManager.equipRing((Ring)draggedItem, RingSlot.RIGHT_HAND);    
                    } else {
                        this.itemWindow.itemManager.equipRing((Ring)draggedItem, RingSlot.LEFT_HAND);    
                    }
                } else {
                    this.itemWindow.itemManager.equipItem(draggedItem, draggedItem.getItemType().getEquipmentSlot());
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

