package ui;

import com.sun.javafx.css.converters.StringConverter;
import engine.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;
import java.util.List;

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
    public TableView<InventoryTable> tblInventory;
    public TableView<QuestTable> tblQuests;

    public ObservableList<InventoryTable> listOfItemsTable = FXCollections.observableArrayList();

    private Player player;
    private Monster currentMonster;
    private World world;

    public void initialize() {
        Player player = new Player(10, 10, 20, 0, 1);

        moveTo(World.locationByID(World.LOCATION_ID_HOME));
        player.getInventory().add(new InventoryItem(World.itemByID(World.ITEM_ID_RUSTY_SWORD), 1));

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

                        txtMessages.appendText("You receive \n");
                        txtMessages.appendText(newLocation.getQuestAvailableHere().getRewardExperiencePoints() + " experience points\n");
                        txtMessages.appendText(newLocation.getQuestAvailableHere().getRewardItem().getName() + " gold\n");
                        txtMessages.appendText(newLocation.getQuestAvailableHere().getRewardItem().getName() + "\n");
                        txtMessages.appendText("\n");

                        player.setExperiencePoints(player.getExperiencePoints() + newLocation.getQuestAvailableHere().getRewardExperiencePoints());
                        player.setGold(player.getGold() + newLocation.getQuestAvailableHere().getRewardGold());

                        boolean addedItemToPlayerInventory = false;
                        for (InventoryItem ii : player.getInventory()) {
                            if (ii.getDetails().getId() == newLocation.getQuestAvailableHere().getRewardItem().getId()) {
                                ii.setQuantity(ii.getQuantity() + 1);
                                addedItemToPlayerInventory = true;
                                break;
                            }
                        }

                        if (!addedItemToPlayerInventory) {
                            player.getInventory().add(new InventoryItem(newLocation.getQuestAvailableHere().getRewardItem(), 1));
                        }

                        for (PlayerQuest pq : player.getQuests()) {
                            if (pq.getDetails().getId() == newLocation.getQuestAvailableHere().getId()) {
                                pq.setCompleted(true);
                                break;
                            }
                        }
                    }
                }
            } else {
                txtMessages.appendText("You receive the " + newLocation.getQuestAvailableHere().getName() + " quest.\n");
                txtMessages.appendText(newLocation.getDescription() + "\n");
                txtMessages.appendText("To complete it, return with:\n");
                for (QuestCompletionItem qci : newLocation.getQuestAvailableHere().getQuestCompletionItems()) {
                    if (qci.getQuantity() == 1) {
                        txtMessages.appendText(Integer.toString(qci.getQuantity()) + " " + qci.getDetails().getName() + "\n");
                    } else {
                        txtMessages.appendText(Integer.toString(qci.getQuantity()) + " " + qci.getDetails().getNamePlural() + "\n");
                    }
                }
                txtMessages.appendText("\n");

                player.getQuests().add(new PlayerQuest(newLocation.getQuestAvailableHere()));
            }
        }

        if (newLocation.getMonsterLivingHere() != null) {
            txtMessages.appendText("You see a " + newLocation.getMonsterLivingHere().getName() + "\n");

            Monster standardMonster = World.monsterByID(newLocation.getMonsterLivingHere().getId());
            currentMonster = new Monster(standardMonster.getId(), standardMonster.getName(), standardMonster.getMaximumDamage(),
                    standardMonster.getCurrentHitPoints(), standardMonster.getMaximumHitPoints(),
                    standardMonster.getRewardExperiencePoints(), standardMonster.getRewardGold());

            for (LootItem lootItem : standardMonster.getLootTable()) {
                currentMonster.getLootTable().add(lootItem);
            }

            cboWeapons.setVisible(true);
            cboPotions.setVisible(true);
            btnUseWeapon.setVisible(true);
            btnUsePotion.setVisible(true);
        }

        tblInventory.getItems().clear();

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getQuantity() > 0) {
                InventoryTable itemsTable = new InventoryTable(ii.getDetails().getName(), ii.getQuantity());
                tblInventory.getItems().add(itemsTable);
            }
        }


        tblQuests.getItems().clear();

        for (PlayerQuest pq : player.getQuests()) {
            QuestTable questTable = new QuestTable(pq.getDetails().getName(), String.valueOf(pq.isCompleted()));
            tblQuests.getItems().add(questTable);
        }

        List<Weapon> weapons = new ArrayList<Weapon>();

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getDetails() instanceof Weapon) {
                if (ii.getQuantity() > 0) {
                    weapons.add((Weapon) ii.getDetails());
                }
            }
        }

        cboWeapons.getItems().clear();
        if (weapons.size() == 0) {
            cboWeapons.setVisible(false);
            btnUseWeapon.setVisible(false);
        } else {
            for (Weapon we : weapons) {
                cboWeapons.getItems().add(we.getName());
            }
        }

        List<HealingPotion> healingPotions = new ArrayList<HealingPotion>();

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getDetails() instanceof HealingPotion) {
                if (ii.getQuantity() > 0) {
                    healingPotions.add((HealingPotion) ii.getDetails());
                }
            }
        }

        cboPotions.getItems().clear();

        if (healingPotions.size() == 0) {
            cboPotions.setVisible(false);
            btnUsePotion.setVisible(false);
        } else {
            for (HealingPotion hp : healingPotions) {
                cboPotions.getItems().add(hp.getName());
            }
        }
    }

    public void updateTableItems() {
        String newl
    }
}
