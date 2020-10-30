package ui;

import engine.*;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public Label lblHitPoints;
    public Label lblGold;
    public Label lblExperience;
    public Label lblLevel;

    public ComboBox<String> cboWeapons;
    public ComboBox<String> cboPotions;
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

    private Player player = new Player(10,10,20,0,1);
    private Monster currentMonster;
    private World world;


    public void initialize() {
        initializeComponents();
    }

    public void clickButtonNorth() {
        moveTo(player.getCurrentLocation().getLocationToNorth());
    }

    public void clickButtonEast() {
        moveTo(player.getCurrentLocation().getLocationToEast());
    }

    public void clickButtonSouth() {
        moveTo(player.getCurrentLocation().getLocationToSouth());
    }

    public void clickButtonWest() {
        moveTo(player.getCurrentLocation().getLocationToWest());
    }

    private void initializeComponents() {
        world = new World();
        moveTo(World.locationByID(World.LOCATION_ID_HOME));
        player.getInventory().add(new InventoryItem(World.itemByID(World.ITEM_ID_RUSTY_SWORD), 1));


        lblHitPoints.setText(String.valueOf(player.getCurrentHitPoints()));
        lblGold.setText(String.valueOf(player.getGold()));
        lblExperience.setText(String.valueOf(player.getExperiencePoints()));
        lblLevel.setText(String.valueOf(player.getLevel()));
    }

    private void moveTo(Location newLocation) {

        if (!player.hasRequiredItemToEnterThisLocation(newLocation)) {
            txtMessages.appendText("You must have a " + newLocation.getItemRequiredToEnter().getName() + "");
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
            boolean playerAlreadyHasQuest = player.hasThisQuest(newLocation.getQuestAvailableHere());
            boolean playerAlreadyCompletedQuest = player.completedThisQuest(newLocation.getQuestAvailableHere());

            if (playerAlreadyHasQuest) {
                if (!playerAlreadyCompletedQuest) {
                    boolean playerHasAllItemsToCompleteQuest = player.hasAllQuestCompletionItems(newLocation.getQuestAvailableHere());

                    if (playerHasAllItemsToCompleteQuest) {
                        txtMessages.appendText("\nYou completed the " + newLocation.getQuestAvailableHere().getName() + "quest.\n");

                        player.removeQuestCompletionItems(newLocation.getQuestAvailableHere());

                        txtMessages.appendText("You receive \n");
                        txtMessages.appendText(newLocation.getQuestAvailableHere().getRewardExperiencePoints() + " experience points\n");
                        txtMessages.appendText(newLocation.getQuestAvailableHere().getRewardItem().getName() + " gold\n");
                        txtMessages.appendText(newLocation.getQuestAvailableHere().getRewardItem().getName() + "\n");
                        txtMessages.appendText("\n");

                        player.setExperiencePoints(player.getExperiencePoints() + newLocation.getQuestAvailableHere().getRewardExperiencePoints());
                        player.setGold(player.getGold() + newLocation.getQuestAvailableHere().getRewardGold());

                        player.addItemToInventory(newLocation.getQuestAvailableHere().getRewardItem());

                        player.markQuestCompleted(newLocation.getQuestAvailableHere());
                    }
                }
            } else {
                txtMessages.appendText("You receive the " + newLocation.getQuestAvailableHere().getName() + " quest.\n");
                txtMessages.appendText(newLocation.getDescription() + "\n");
                txtMessages.appendText("To complete it, return with:\n");
                for (QuestCompletionItem qci : newLocation.getQuestAvailableHere().getQuestCompletionItems()) {
                    if (qci.getQuantity() == 1) {
                        txtMessages.appendText(qci.getQuantity() + " " + qci.getDetails().getName() + "\n");
                    } else {
                        txtMessages.appendText(qci.getQuantity() + " " + qci.getDetails().getNamePlural() + "\n");
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

        updateInventoryListUI();
        updateQuestListUI();
        updateWeaponListUI();
        updatePotionListUI();
    }

    private void updateInventoryListUI() {
        tblInventory.getItems().clear();

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getQuantity() > 0) {
                tblInventory.getItems().add(new InventoryTable(ii.getDetails().getName(), String.valueOf(ii.getQuantity())));
            }
        }
    }

    private void updateQuestListUI() {
        tblQuests.getItems().clear();

        for (PlayerQuest pq : player.getQuests()) {
            tblQuests.getItems().add(new QuestTable(pq.getDetails().getName(), String.valueOf(pq.isCompleted())));
        }
    }

    private void updateWeaponListUI() {
        List<Weapon> weapons = new ArrayList<>();

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
    }

    private void updatePotionListUI() {
        List<HealingPotion> healingPotions = new ArrayList<>();

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
}
