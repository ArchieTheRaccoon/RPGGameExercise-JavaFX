package ui;

import engine.InventoryItem;
import engine.InventoryTablePlayer;
import engine.Player;
import engine.World;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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

    private ObservableList<Button> buttons = FXCollections.observableArrayList();

    private Player currentPlayer = new Player(10,10,20,0);

    public void initialize() {
        tblclmnItemMyInventory.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblclmnQuantityMyInventory.setCellValueFactory(new PropertyValueFactory<>("ItemAmount"));
        tblclmnPriceMyInventory.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tblclmnSellMyInventory.setCellValueFactory(new PropertyValueFactory<>("Sellable"));

        updateTables();
    }

    private void updateTables() {
        tblMyItems.getItems().clear();

        Player player = getCurrentPlayer();

        ObservableList<InventoryTablePlayer> shortInventoryList = FXCollections.observableArrayList();
        shortInventoryList.add(new InventoryTablePlayer("Bitch", "5", "7"));

        for (InventoryItem ii : player.getInventory()) {
            shortInventoryList.add(new InventoryTablePlayer(ii.getDetails().getName(), String.valueOf(ii.getQuantity()), String.valueOf(ii.getDetails().getPrice())));
        }

        tblMyItems.setItems(shortInventoryList);

//        for (InventoryItem ii : currentPlayer.getInventory()) {
//            System.out.println(ii.getDetails().getName());
//            String nameOfItem = ii.getDetails().getName();
//            String amountOfItem = String.valueOf(ii.getQuantity());
//            String priceOfItem = String.valueOf(ii.getDetails().getPrice());
//        }

//
//        for (InventoryItem ii : currentPlayer.getInventory()) {
//            System.out.println("Bitch");
//            shortInventoryList.add(new InventoryTablePlayer(ii.getDetails().getName(), String.valueOf(ii.getQuantity()), String.valueOf(ii.getDetails().getPrice())));
//
//            Button sellButton = new Button(tblMyItems.getItems().toString());
//
//            sellButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                    (event -> clickSellButton(ii)));
//
//            sellButton.setText("Sell");
//            buttons.add(sellButton);
//        }
//
//        tblMyItems.setItems(shortInventoryList);

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

    public void clickTradeAction(Player player) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tradeUI.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 544, 349);
            Stage secondStage = new Stage();
            secondStage.setTitle("Trade");
            secondStage.setScene(scene);
            secondStage.show();

            setCurrentPlayer(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
