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

    ItemLayeredPane helmPanel, weaponPanel, amuletPanel, shieldPanel, ringOnePanel, ringTwoPanel;
    ItemLayeredPane beltPanel, pantsPanel, glovesPanel, chestPanel, bootsPanel;

    ImageIcon helmIcon, shieldIcon;

    JButton getNewItemButton;
    ItemLayeredPane spareItemPanel;

    CharacterScreen characterScreen;
    Character character;
    ItemGenerator itemGenerator;

    ItemLayeredPane sourcePanel;
    Item draggedItem;
    Item newItem; // TODO: this needs to be moved to another class, but we'll put it here for now

    public ItemWindow(CharacterScreen characterScreen, Character character) {

        this.characterScreen = characterScreen;
        this.character = character;
        this.itemGenerator = new ItemGenerator();

        this.setTitle("Items");
        this.setSize(ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);

        this.layeredPane = new JLayeredPane();

        this.itemPanel = new JPanel();
        this.itemPanel.setLayout(new GridBagLayout());

        // creates a constraints object
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        
        
        // TODO: wondering if the get method and how the character stores items should be rethought a bit
        // helm
        this.helmPanel = new ItemLayeredPane(Item.HELM, character.getItem(Item.HELM));
        c.gridx = 2;
        c.gridy = 0;
        this.itemPanel.add(this.helmPanel, c);

        // weapon
        this.weaponPanel = new ItemLayeredPane(Item.WEAPON, this.character.getItem(Item.WEAPON));
        c.gridx = 0;
        c.gridy = 1;
        this.itemPanel.add(this.weaponPanel, c);

        // amulet
        this.amuletPanel = new ItemLayeredPane(Item.AMULET, this.character.getItem(Item.AMULET));
        c.gridx = 3;
        c.gridy = 0;
        this.itemPanel.add(this.amuletPanel, c);
        
        // ring
        this.ringOnePanel = new ItemLayeredPane(Item.RING, this.character.getItem(Item.RING));
        c.gridx = 1;
        c.gridy = 2;
        this.itemPanel.add(this.ringOnePanel, c);
        
        //TODO: how do we implement this? - It's currently just a placeholder
        this.ringTwoPanel = new ItemLayeredPane(Item.RING, this.character.getItem(Item.RING));
        c.gridx = 3;
        c.gridy = 2;
        this.itemPanel.add(this.ringTwoPanel, c);

        // chest panel
        this.chestPanel = new ItemLayeredPane(Item.CHEST, this.character.getItem(Item.CHEST));
        c.gridx = 2;
        c.gridy = 1;
        this.itemPanel.add(this.chestPanel, c);

        // gloves
        this.glovesPanel = new ItemLayeredPane(Item.GLOVES, this.character.getItem(Item.GLOVES));
        c.gridx = 0;
        c.gridy = 2;
        this.itemPanel.add(this.glovesPanel, c);

        // shield
        this.shieldPanel = new ItemLayeredPane(Item.SHIELD, this.character.getItem(Item.SHIELD));
        c.gridx = 4;
        c.gridy = 1;
        this.itemPanel.add(this.shieldPanel, c);

        // belt
        this.beltPanel = new ItemLayeredPane(Item.BELT, this.character.getItem(Item.BELT));
        c.gridx = 2;
        c.gridy = 2;
        this.itemPanel.add(this.beltPanel, c);

        // pants
        this.pantsPanel = new ItemLayeredPane(Item.PANTS, this.character.getItem(Item.PANTS));
        c.gridx = 2;
        c.gridy = 3;
        this.itemPanel.add(this.pantsPanel, c);

        // boots
        this.bootsPanel = new ItemLayeredPane(Item.BOOTS, this.character.getItem(Item.BOOTS));
        c.gridx = 4;
        c.gridy = 2;
        this.itemPanel.add(this.bootsPanel, c);

        // generate new item button
        this.getNewItemButton = new JButton("Get New Item");
        c.gridx = 5;
        c.gridy = 2;
        this.itemPanel.add(this.getNewItemButton, c);

        // holder for extra item
        this.spareItemPanel = new ItemLayeredPane(ItemLayeredPane.NEW_ITEM_SLOT, null);
        c.gridx = 5;
        c.gridy = 1;
        this.itemPanel.add(this.spareItemPanel, c);

        generateItemIcons();
        setItemsInPanes();

        this.itemPanel.setBounds(0, 0, ITEM_WINDOW_WIDTH, ITEM_WINDOW_HEIGHT);

        this.add(this.layeredPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        this.setLocationRelativeTo(this);

        this.hoverPanel = new HoverPanel(this.character, this.spareItemPanel);

        this.layeredPane.add(this.itemPanel, 1);
        this.layeredPane.add(this.hoverPanel, 0);

        this.setVisible(true);

        MyMouseListener mouseListener = new MyMouseListener(this);

        // add mouse listeners to the current item image icons
        for (int i = 0; i < this.character.getItemListSize(); i++) {
            this.character.getItem(i).getItemImageLabel().addMouseListener(mouseListener);
        }

        this.getNewItemButton.addActionListener(e -> {
            this.newItem = this.itemGenerator.createNewItem();
            this.newItem.setImageLabel(); // TODO: this should be called in a better place
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
        // this.ringOnePanel.setTransferHandler(new MyPanelTransferHandler(this));
        // this.ringTwoPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.beltPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.pantsPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.glovesPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.chestPanel.setTransferHandler(new MyPanelTransferHandler(this));
        this.bootsPanel.setTransferHandler(new MyPanelTransferHandler(this));

    }


    /**
     * TODO: I'm not sure if this should be done here, but this will generate the Image Icons for currently
     * this should probably be done when the game initializes
     * equippied items
     */
    private void generateItemIcons() {

        for (int i = 0; i < this.character.getItemListSize(); i++) {
            Item item = this.character.getItem(i);
            item.setImageLabel();
        }

    }

    /**
     * used to track the item that is currently being dragged
     */
    public void setDraggedItem(String name) {
        
        if (name.equals(ItemLayeredPane.NEW_ITEM_SLOT)) {
            this.draggedItem = this.newItem;
        } else {
            this.draggedItem = this.character.getItem(name);
        }

        // TODO: maybe throw exception if the item is null
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
     * TODO: add rings and blings
     * 
     */
    private void setItemsInPanes() {

        if (this.character.getItem(Item.HELM) != null) {
            this.helmPanel.addItemToSlot(this.character.getItem(Item.HELM));
        }

        if (this.character.getItem(Item.WEAPON) != null) {
            this.weaponPanel.addItemToSlot(this.character.getItem(Item.WEAPON));
        }

        if (this.character.getItem(Item.AMULET) != null) {
            this.amuletPanel.addItemToSlot(this.character.getItem(Item.AMULET));
        }
        
        if (this.character.getItem(Item.CHEST) != null) {
            this.chestPanel.addItemToSlot(this.character.getItem(Item.CHEST));
        }

        if (this.character.getItem(Item.SHIELD) != null) {
            this.shieldPanel.addItemToSlot(this.character.getItem(Item.SHIELD));
        }
        
        if (this.character.getItem(Item.BOOTS) != null) {
            this.bootsPanel.addItemToSlot(this.character.getItem(Item.BOOTS));
        }

        if (this.character.getItem(Item.BELT) != null) {
            this.beltPanel.addItemToSlot(this.character.getItem(Item.BELT));
        }

        if (this.character.getItem(Item.GLOVES) != null) {
            this.glovesPanel.addItemToSlot(this.character.getItem(Item.GLOVES));
        }

        if (this.character.getItem(Item.PANTS) != null) {
            this.pantsPanel.addItemToSlot(this.character.getItem(Item.PANTS));
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
            
            // make sure the item being dragged matches the itemslot type
            if (!this.itemWindow.draggedItem.getItemType().equals(targetPanel.itemSlotType)) {
                return false;
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
                // System.out.println(draggedItem.getItemName());
                
                // this needs to be moved to a function somewhere
                this.itemWindow.character.addItem(draggedItem);
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

