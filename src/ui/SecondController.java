package ui;

import engine.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ui.Controller;

import java.net.URL;
import java.util.ResourceBundle;

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

    private ObservableList<Button> buttons = FXCollections.observableArrayList();

    public void initialize() {
        tblclmnItemMyInventory.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblclmnQuantityMyInventory.setCellValueFactory(new PropertyValueFactory<>("ItemAmount"));
        tblclmnPriceMyInventory.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tblclmnSellMyInventory.setCellValueFactory(new PropertyValueFactory<>("button"));

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

            buttons.add(sellButton);

            if (ii.getQuantity() > 0) {
                shortInventoryList.add(new InventoryTablePlayer(ii.getDetails().getName(), String.valueOf(ii.getQuantity()), String.valueOf(ii.getDetails().getPrice()), sellButton));
            }
        }

        tblMyItems.setItems(shortInventoryList);

        tblVendorsItems.getItems().clear();
        
    }

    public void clickSellButton(InventoryItem ii) {
        if (ii.getDetails().getPrice() == World.UNSELLABLE_ITEM_PRICE) {
            //The Selected item can't be selled
//            DialogPane box = new DialogPane();
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
