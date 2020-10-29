package ui;

import engine.*;
import javafx.scene.control.*;

public class Controller {
    public Label lblHitPoints;
    public Label lblGold;
    public Label lblExperience;
    public Label lblLevel;

    public ComboBox cboWeapons;
    public ComboBox cboPotions;
    public Button btnUseWeapon;
    public Button btnUsePotion;

    public Button btnNorth;
    public Button btnEast;
    public Button btnSouth;
    public Button btnWest;

    public TextField txtLocation;
    public TextField txtMessages;
    public TableView tblInventory;
    public TableView tblQuests;

    private Player player;
    private Monster currentMonster;
    private World world;

    public void initialize() {
        Player player = new Player(10, 10, 20, 0, 1);

        lblHitPoints.setText(String.valueOf(player.getCurrentHitPoints()));
        lblGold.setText(String.valueOf(player.getGold()));
        lblExperience.setText(String.valueOf(player.getExperiencePoints()));
        lblLevel.setText(String.valueOf(player.getLevel()));
    }

    public void clickButtonNorth() {
        System.out.println("Boi");
    }

    public void clickButtonEast() {
        System.out.println("Boi");
    }

    public void clickButtonSouth() {
        System.out.println("Boi");
    }

    public void clickButtonWest() {
        System.out.println("Boi");
    }

    private void moveTo(Location newLocation) {
        if (newLocation.getItemRequiredToEnter() != null) {
            boolean playerHasRequiredItem = false;

            for (InventoryItem ii : player.getInventory()) {
                if (ii.getDetails().getId() == newLocation.getItemRequiredToEnter().getId()) {
                    playerHasRequiredItem = true;
                    break;
                }
            }

            if (!playerHasRequiredItem) {
                txtMessages.appendText("You must have a " + newLocation.getItemRequiredToEnter().getName() + " to enter this location.\n");
                return;
            }
        }
        player.setCurrentLocation(newLocation);

        btnNorth.setVisible(newLocation.getLocationToNorth() != null);
        btnEast.setVisible(newLocation.getLocationToEast() != null);
    }
}