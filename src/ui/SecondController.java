package ui;

import engine.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class SecondController {
    public TableView<InventoryTablePlayer> tblVendorsItems;
    public TableView<InventoryTablePlayer> tblMyItems;
    public Button btnClose;
    public Label lblVendorInventory;
    public Label lblMyInventory;
    public TableColumn<InventoryTablePlayer, String> tblclmnItemMyInventory;
    public TableColumn<InventoryTablePlayer, String> tblclmnQuantityMyInventory;
    public TableColumn<InventoryTablePlayer, String> tblclmnPriceMyInventory;
    public TableColumn<InventoryTablePlayer, Button> tblclmnSellMyInventory;
    public TableColumn<InventoryTablePlayer, String> tblclmnItemVendor;
    public TableColumn<InventoryTablePlayer, String> tblclmnQuantityVendor;
    public TableColumn<InventoryTablePlayer, String> tblclmnPriceVendor;
    public TableColumn<InventoryTablePlayer, String> tblclmnBuyVendor;

    public Player currentPlayer;

//    private ObservableList<Button> buttons = FXCollections.observableArrayList();

    public void initialize() {
        tblclmnItemMyInventory.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblclmnQuantityMyInventory.setCellValueFactory(new PropertyValueFactory<>("ItemAmount"));
        tblclmnPriceMyInventory.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tblclmnSellMyInventory.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblclmnItemVendor.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblclmnQuantityVendor.setCellValueFactory(new PropertyValueFactory<>("ItemAmount"));
        tblclmnPriceVendor.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tblclmnBuyVendor.setCellValueFactory(new PropertyValueFactory<>("button"));

        currentPlayer = SavePlayer.getSavedPlayer();
        updateTables();
    }

    private void updateTables() {
        tblMyItems.getItems().clear();

        ObservableList<InventoryTablePlayer> shortInventoryList = FXCollections.observableArrayList();
        for (InventoryItem ii : currentPlayer.getInventory()) {
            Button sellButton = new Button("Sell");

            sellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    clickSellButton(ii);
                }
            });
            sellButton.setPrefWidth(tblclmnSellMyInventory.getPrefWidth());

            if (ii.getQuantity() > 0) {
                shortInventoryList.add(new InventoryTablePlayer(ii.getDetails().getName(), String.valueOf(ii.getQuantity()), String.valueOf(ii.getDetails().getPrice()), sellButton));
            }
        }

        tblMyItems.setItems(shortInventoryList);

        tblVendorsItems.getItems().clear();

        ObservableList<InventoryTablePlayer> shortVendorInventoryList = FXCollections.observableArrayList();

        for (InventoryItem ii : currentPlayer.getCurrentLocation().getVendorWorkingHere().getInventory()) {
            Button buyButton = new Button("Buy");

            buyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    clickBuyButton(ii);
                }
            });
            buyButton.setPrefWidth(tblclmnBuyVendor.getPrefWidth());

            if (ii.getQuantity() > 0) {
                shortVendorInventoryList.add(new InventoryTablePlayer(ii.getDetails().getName(), String.valueOf(ii.getQuantity()), String.valueOf(ii.getDetails().getPrice()), buyButton));
            }
        }

        tblVendorsItems.setItems(shortVendorInventoryList);
    }

    public void clickBuyButton(InventoryItem ii) {
        if (currentPlayer.getGold() < ii.getDetails().getPrice()) {
            new Alert(Alert.AlertType.INFORMATION, "You don't have enough gold to buy " + ii.getDetails().getNamePlural() + ".").showAndWait();
        } else {
            currentPlayer.setGold(currentPlayer.getGold() - ii.getDetails().getPrice());
            currentPlayer.addItemToInventory(ii.getDetails(), 1);
            currentPlayer.getCurrentLocation().getVendorWorkingHere().removeItemFromInventory(ii.getDetails(), 1);

            updateTables();
        }
    }

    public void clickSellButton(InventoryItem ii) {
        if (ii.getDetails().getPrice() == World.UNSELLABLE_ITEM_PRICE) {
            new Alert(Alert.AlertType.INFORMATION, "You can't sell " + ii.getDetails().getNamePlural() + ".").showAndWait();
        } else {
            currentPlayer.setGold(currentPlayer.getGold() + ii.getDetails().getPrice());
            currentPlayer.removeItemFromInventory(ii.getDetails(), 1);

            updateTables();
        }
    }

    @FXML
    private void closeWindow() {
        SavePlayer.setSavedPlayer(currentPlayer);
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
