package engine;

import java.util.ArrayList;
import ui.GameObserver.Event;

public class Vendor extends ObservableGamePart {
    private String name;
    private ArrayList<InventoryItem> inventory;

    public Vendor(String name) {
        this.name = name;
        inventory = new ArrayList<InventoryItem>();
    }

    public void addItemToInventory(Item itemToAdd, int quantity) {
        InventoryItem item = inventory.stream().filter(ii -> ii.getDetails().getId() == itemToAdd.getId()).findFirst().orElse(null);

        if (item == null) {
            inventory.add(new InventoryItem(itemToAdd, quantity));
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
    }

    public void removeItemFromInventory(Item itemToRemove, int quantity) {
        InventoryItem item = inventory.stream().filter(ii -> ii.getDetails().getId() == itemToRemove.getId()).findFirst().orElse(null);

        if (item == null) {
        //ignore
        } else {
            item.setQuantity(item.getQuantity() - quantity);

            if (item.getQuantity() < 0) {
                item.setQuantity(0);
            }

            if (item.getQuantity() == 0) {
                inventory.remove(item);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<InventoryItem> inventory) {
        this.inventory = inventory;
    }
}
