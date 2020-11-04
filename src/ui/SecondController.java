package ui;

import engine.InventoryTable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class SecondController {
    public ListView<InventoryTable> tblVendorsItems;
    public ListView<InventoryTable> tblMyItems;
    public Button btnClose;
    public Label lblVendorInventory;
    public Label lblMyInventory;

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
