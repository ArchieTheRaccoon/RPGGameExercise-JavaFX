package engine;

import javafx.beans.property.SimpleStringProperty;

public class InventoryTable {
    private SimpleStringProperty itemName, itemAmount;

    public InventoryTable(String itemName, String itemAmount) {
        this.itemName = new SimpleStringProperty(itemName);
        this.itemAmount = new SimpleStringProperty(itemAmount);
    }

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = new SimpleStringProperty(itemName);
    }

    public String getItemAmount() {
        return itemAmount.get();
    }

    public SimpleStringProperty itemAmountProperty() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = new SimpleStringProperty(itemAmount);
    }
}
