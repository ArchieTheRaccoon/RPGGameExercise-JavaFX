package engine;

import javafx.beans.property.SimpleStringProperty;

public class InventoryTablePlayer {
    private SimpleStringProperty itemName, itemAmount, price;

    public InventoryTablePlayer(String itemName, String itemAmount, String price) {
        this.itemName = new SimpleStringProperty(itemName);
        this.itemAmount = new SimpleStringProperty(itemAmount);
        this.price = new SimpleStringProperty(price);
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

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price = new SimpleStringProperty(price);
    }
}
