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
        btnSouth.setVisible(newLocation.getLocationToSouth() != null);
        btnWest.setVisible(newLocation.getLocationToWest() != null);

        txtLocation.setText(newLocation.getName() + "\n");
        txtLocation.appendText(newLocation.getDescription() + "\n");

        player.setCurrentHitPoints(player.getMaximumHitPoints());

        lblHitPoints.setText(Integer.toString(player.getCurrentHitPoints()));

        if (newLocation.getQuestAvailableHere() != null) {
            boolean playerAlreadyHasQuest = false;
            boolean playerAlreadyCompletedQuest = false;

            for (PlayerQuest playerQuest : player.getQuests()) {
                if (playerQuest.getDetails().getId() == newLocation.getQuestAvailableHere().getId()) {
                    playerAlreadyHasQuest = true;
                    if (playerQuest.isCompleted()) {
                        playerAlreadyCompletedQuest = true;
                    }
                }
            }

            if (playerAlreadyHasQuest) {
                if (!playerAlreadyCompletedQuest) {
                    boolean playerHasAllItemsToCompleteQuest = true;

                    for (QuestCompletionItem qci : newLocation.getQuestAvailableHere().getQuestCompletionItems()) {
                        boolean foundItemInPlayersInventory = false;

                        for (InventoryItem ii : player.getInventory()) {
                            if (ii.getDetails().getId() == qci.getDetails().getId()) {
                                foundItemInPlayersInventory = true;
                                if (ii.getQuantity() < qci.getQuantity()) {
                                    playerHasAllItemsToCompleteQuest = false;
                                    break;
                                }
                                break;
                            }
                        }

                        if (!foundItemInPlayersInventory) {
                            playerHasAllItemsToCompleteQuest = false;
                            break;
                        }
                    }

                    if (playerHasAllItemsToCompleteQuest) {
                        txtMessages.appendText("\nYou completed the " + newLocation.getQuestAvailableHere().getName() + "quest.\n");

                        for (QuestCompletionItem qci : newLocation.getQuestAvailableHere().getQuestCompletionItems()) {
                            for (InventoryItem ii : player.getInventory()) {
                                if (ii.getDetails().getId() == qci.getDetails().getId()) {
                                    ii.setQuantity(ii.getQuantity() - qci.getQuantity());
                                    break;
                                }
                            }
                        }

                        txtMessages.appendText("You receive \n")
                        txtMessages.appendText(newLocation.getQuestAvailableHere().getRewardExperiencePoints() + " experience points\n");
                        txtMessages.appendText(newLocation.getQuestAvailableHere().getRewardItem().getName() + " gold\n");
                        txtMessages.appendText(newLocation.getQuestAvailableHere().getRewardItem().getName() + "\n");
                        txtMessages.appendText("\n");

                        player.setExperiencePoints(player.getExperiencePoints() + newLocation.getQuestAvailableHere().getRewardExperiencePoints());
                        player.setGold(player.getGold() + newLocation.getQuestAvailableHere().getRewardGold());

                        
                    }
                }
            }
        }
    }
}
