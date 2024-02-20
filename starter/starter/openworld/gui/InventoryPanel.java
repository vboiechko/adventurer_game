package openworld.gui;

import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import openworld.adventurer.Inventory;
import openworld.item.ConsumableItem;
import openworld.item.EquippableItem;
import openworld.item.InventorySlotType;

public class InventoryPanel extends JPanel implements ActionListener {

    private Map<InventorySlotType, JButton> equipButtons;
    private Map<InventorySlotType, JList<EquippableItem>> equipLists;
    private JButton consumeButton;
    private JList<ConsumableItem> consumeList;
    private Inventory inventory;
    private ControlPanel controlPanel;

    public InventoryPanel(Inventory inventory, ControlPanel controlPanel) {
        this.inventory = inventory;
        this.controlPanel = controlPanel;

        setBorder(new TitledBorder("Inventory management"));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        equipButtons = new HashMap<>();
        equipLists = new HashMap<>();

        for (InventorySlotType type : InventorySlotType.values()) {
            JList<EquippableItem> equipList = createList(new EquippableListModel(inventory, type));
            equipLists.put(type, equipList);
            add(addTitleAndScroll(equipList, type.toString()));
            JButton equipButton = new JButton("Equip " + type);
            equipButton.addActionListener(this);
            equipButtons.put(type, equipButton);
            add(equipButton);
            add(Box.createVerticalGlue());
        }

        consumeList = createList(new ConsumableListModel(inventory));
        add(addTitleAndScroll(consumeList, "Consumables"));
        consumeButton = new JButton("Consume");
        consumeButton.addActionListener(this);
        add(consumeButton);
    }

    private <T> JList<T> createList(ListModel<T> listModel) {
        JList<T> list = new JList<T>(listModel);
        list.setVisibleRowCount(3);
        return list;
    }

    private JScrollPane addTitleAndScroll(JList<?> list, String title) {
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setBorder(new TitledBorder(title));
        return listScroll;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == consumeButton){
            if (consumeList.isSelectionEmpty()){
                System.out.println("No selection");
            } else {
                inventory.useItem(consumeList.getSelectedIndex());
            }
            
        } 
        else {
            for ( InventorySlotType equip : equipLists.keySet()){
                if (e.getSource() == equipButtons.get(equip)){
                    if (equipLists.get(equip).isSelectionEmpty()){
                        System.out.println("No selection");
                    } else {
                        inventory.equipItem(equipLists.get(equip).getSelectedValue());
                    }
                }
            }
        }
        updateLists();
        controlPanel.getAdventurerPanel().update();
    }

    public void updateLists() {
        ((ConsumableListModel) consumeList.getModel()).update();
        for (InventorySlotType type : InventorySlotType.values()) {
            ((EquippableListModel) equipLists.get(type).getModel()).update();
        }
    }

    public void disableAll() {
        consumeButton.setEnabled(false);
        consumeList.setEnabled(false);
        for (InventorySlotType type : InventorySlotType.values()) {
            equipButtons.get(type).setEnabled(false);
            equipLists.get(type).setEnabled(false);
        }
    }

}
