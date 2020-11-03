package ui;

import engine.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
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
    public Button btnLoadData;
    public Button btnSaveData;

    public TextArea txtLocation;
    public TextArea txtMessages;
    public TableView<InventoryTable> tblInventory;
    public TableView<QuestTable> tblQuests;
    public TableColumn<InventoryTable, String> tblclmnItemName;
    public TableColumn<InventoryTable, String> tblclmnQuantity;
    public TableColumn<QuestTable, String> tblclmnQuestName;
    public TableColumn<QuestTable, String> tblclmnDone;

    private Monster currentMonster;
    private World world;
    public Player player = new Player(10,10,20,0);
    private final String PLAYER_DATA_FILE_NAME = "playerData.xml";


    public void initialize() {
        initializeComponents();
        updateInventoryListUI();
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

    public void clickButtonUseWeapon() {
        useWeapon();
    }

    public void clickButtonUsePotion() {
        usePotion();
    }

    private void initializeComponents() {
        world = new World();
        player = Player.createDefaultPlayer();
        moveTo(World.locationByID(World.LOCATION_ID_HOME));

        updateLists();

        tblclmnItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblclmnQuantity.setCellValueFactory(new PropertyValueFactory<>("ItemAmount"));
        tblclmnQuestName.setCellValueFactory(new PropertyValueFactory<>("NameQuest"));
        tblclmnDone.setCellValueFactory(new PropertyValueFactory<>("IsCompleted"));
    }

    private void moveTo(Location newLocation) {

        if (!player.hasRequiredItemToEnterThisLocation(newLocation)) {
            txtMessages.appendText("You must have a " + newLocation.getItemRequiredToEnter().getName() + " to enter this location.\n");
            return;
        }

        player.setCurrentLocation(newLocation);

        btnNorth.setVisible(newLocation.getLocationToNorth() != null);
        btnEast.setVisible(newLocation.getLocationToEast() != null);
        btnSouth.setVisible(newLocation.getLocationToSouth() != null);
        btnWest.setVisible(newLocation.getLocationToWest() != null);

        txtLocation.setText(newLocation.getName() + "\n");
        txtLocation.appendText(newLocation.getDescription() + "\n");

        player.setCurrentHitPoints(player.getMaximumHitPoints());

        updateLists();

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

    private void useWeapon() {
        Weapon currentWeapon = null;

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getDetails() instanceof Weapon) {
                if (ii.getQuantity() > 0) {
                    if (ii.getDetails().getName().equals(cboWeapons.getSelectionModel().getSelectedItem())) {
                        currentWeapon = (Weapon) ii.getDetails();
                    }
                }
            }
        }

        int damageToMonster = RandomNumberGenerator.numberBetween(currentWeapon.getMinimumDamage(), currentWeapon.getMaximumDamage());

        currentMonster.setCurrentHitPoints(currentMonster.getCurrentHitPoints() - damageToMonster);

        txtMessages.appendText("Your hit the " + currentMonster.getName() + " for " + damageToMonster + " points.\n");

        if (currentMonster.getCurrentHitPoints() <= 0) {
            txtMessages.appendText("\nYou defeated the " + currentMonster.getName() + ".\n");

            player.setExperiencePoints(player.getExperiencePoints() + currentMonster.getRewardExperiencePoints());
            txtMessages.appendText("You receive " + currentMonster.getRewardExperiencePoints() + " experience points.\n");

            player.setGold(player.getGold() + currentMonster.getRewardGold());
            txtMessages.appendText("You receive " + currentMonster.getRewardGold() + " gold.\n");

            List<InventoryItem> lootedItems = new ArrayList<InventoryItem>();

            for (LootItem lootItem : currentMonster.getLootTable()) {
                if (RandomNumberGenerator.numberBetween(1, 100) <= lootItem.getDropPercentage()) {
                    lootedItems.add(new InventoryItem(lootItem.getDetails(), 1));
                }
            }

            if (lootedItems.size() == 0) {
                for (LootItem lootItem : currentMonster.getLootTable()) {
                    if (lootItem.isDefaultItem()) {
                        lootedItems.add(new InventoryItem(lootItem.getDetails(), 1));
                    }
                }
            }

            for (InventoryItem ii : lootedItems) {
                player.addItemToInventory(ii.getDetails());

                if (ii.getQuantity() == 1) {
                    txtMessages.appendText("You loot " + ii.getQuantity() + " " + ii.getDetails().getName() + ".\n");
                } else {
                    txtMessages.appendText("You loot " + ii.getQuantity() + " " + ii.getDetails().getNamePlural() + ".\n");
                }
            }

            updateLists();

            txtMessages.appendText("\n");

            moveTo(player.getCurrentLocation());
        } else {
            damageFromMonster();
            updateLists();
        }
    }

    private void usePotion() {
        HealingPotion potion = null;
        for (InventoryItem ii : player.getInventory()) {
            if (ii.getDetails() instanceof HealingPotion) {
                if (ii.getQuantity() > 0) {
                    if (ii.getDetails().getName().equals(cboPotions.getSelectionModel().getSelectedItem())) {
                        potion = (HealingPotion) ii.getDetails();
                    }
                }
            }
        }

        player.setCurrentHitPoints(player.getCurrentHitPoints() + potion.getAmountToHeal());

        if (player.getCurrentHitPoints() > player.getMaximumHitPoints()) {
            player.setCurrentHitPoints(player.getMaximumHitPoints());
        }

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getDetails().getId() == potion.getId()) {
                ii.setQuantity(ii.getQuantity() - 1);
                break;
            }
        }

        txtMessages.appendText("You drink a " + potion.getName() + ".\n");

        damageFromMonster();
        updateLists();
    }

    private void damageFromMonster() {
        int damageToPlayer = RandomNumberGenerator.numberBetween(0, currentMonster.getMaximumDamage());

        txtMessages.appendText("The " + currentMonster.getName() + " did " + damageToPlayer + " points of damage.\n");

        player.setCurrentHitPoints(player.getCurrentHitPoints() - damageToPlayer);
        updateLists();

        if (player.getCurrentHitPoints() <= 0) {
            txtMessages.appendText("The " + currentMonster.getName() + " killed you.\n");

            moveTo(World.locationByID(World.LOCATION_ID_HOME));
        }
    }

    private void updateInventoryListUI() {
        ObservableList<InventoryTable> shortInventoryList = FXCollections.observableArrayList();
        tblInventory.getItems().clear();

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getQuantity() > 0) {
                shortInventoryList.add(new InventoryTable(ii.getDetails().getName(), String.valueOf(ii.getQuantity())));
            }
        }

        tblInventory.setItems(shortInventoryList);
    }

    private void updateQuestListUI() {
        ObservableList<QuestTable> shortQuestList = FXCollections.observableArrayList();
        tblQuests.getItems().clear();

        for (PlayerQuest pq : player.getQuests()) {
            shortQuestList.add(new QuestTable(pq.getDetails().getName(), pq.isCompleted()));
        }

        tblQuests.setItems(shortQuestList);
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
            int tmpCounter = 0;
            int currentWeaponIndex = 0;

            for (Weapon we : weapons) {
                if (player.getCurrentWeapon() != null && we.getId() == player.getCurrentWeapon().getId()) {
                    currentWeaponIndex = tmpCounter;
                }

                cboWeapons.getItems().add(we.getName());
                tmpCounter++;
            }
            cboWeapons.getSelectionModel().select(currentWeaponIndex);
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

    private void updateLists() {
        lblHitPoints.setText(String.valueOf(player.getCurrentHitPoints()));
        lblGold.setText(String.valueOf(player.getGold()));
        lblExperience.setText(String.valueOf(player.getExperiencePoints()));
        lblLevel.setText(String.valueOf(player.getLevel()));

        updateInventoryListUI();
        updateWeaponListUI();
        updateQuestListUI();
        updatePotionListUI();
    }

    public void saveDataFile() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource xmlPlayerInformation = player.toXmlDomSource();
            StreamResult streamResult = new StreamResult(new File(PLAYER_DATA_FILE_NAME));

            transformer.transform(xmlPlayerInformation, streamResult);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void loadDataFile() {
        if (new File(PLAYER_DATA_FILE_NAME).isFile()) {
            player = Player.createPlayerFromXml(PLAYER_DATA_FILE_NAME);
        } else {
            player = Player.createDefaultPlayer();
        }

        moveTo(player.getCurrentLocation());
        updateLists();
    }

    public void saveCurrentWeapon() {
        String newWeaponName = cboWeapons.getSelectionModel().getSelectedItem();

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getDetails() instanceof Weapon && ii.getDetails().getName().equals(newWeaponName)) {
                player.setCurrentWeapon((Weapon) ii.getDetails());
            }
        }
    }
}
