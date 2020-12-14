package ui;

import engine.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller implements GameObserver {
    private final String PLAYER_DATA_FILE_NAME = "playerData.xml";
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
    public Button btnTrade;
    public TextArea txtLocation;
    public TextArea txtMessages;
    public TableView<InventoryTable> tblInventory;
    public TableView<QuestTable> tblQuests;
    public TableColumn<InventoryTable, String> tblclmnItemName;
    public TableColumn<InventoryTable, String> tblclmnQuantity;
    public TableColumn<QuestTable, String> tblclmnQuestName;
    public TableColumn<QuestTable, String> tblclmnDone;
    public ImageView imageLogo;
    public Pane mapPane;
    private Monster currentMonster;
    private World world;
    private Player player;
    private Controller controller;

    public void initialize() {
        MusicPlayer.turnOnMenuMusic();
        initializeComponents();
        player.setController(this);
    }

    public void clickButtonNorth() {
        MusicPlayer.soundMove();
        player.moveTo(player.getCurrentLocation().getLocationToNorth());
    }

    public void clickButtonEast() {
        MusicPlayer.soundMove();
        player.moveTo(player.getCurrentLocation().getLocationToEast());
    }

    public void clickButtonSouth() {
        MusicPlayer.soundMove();
        player.moveTo(player.getCurrentLocation().getLocationToSouth());
    }

    public void clickButtonWest() {
        MusicPlayer.soundMove();
        player.moveTo(player.getCurrentLocation().getLocationToWest());
    }

    public void clickButtonUseWeapon() {
        String weaponName = cboWeapons.getSelectionModel().getSelectedItem();
        player.useWeapon(weaponName);
    }

    public void clickButtonUsePotion() {
        String potionName = cboPotions.getSelectionModel().getSelectedItem();
        player.usePotion(potionName);
    }

    private void initializeComponents() {
        world = new World();

        if (player == null) {
            player = Player.createDefaultPlayer();
        }

        initializeObservableLabels();

        player.addObserver(this);
        player.moveTo(World.locationByID(World.LOCATION_ID_HOME));

        tblclmnItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblclmnQuantity.setCellValueFactory(new PropertyValueFactory<>("ItemAmount"));
        tblclmnQuestName.setCellValueFactory(new PropertyValueFactory<>("NameQuest"));
        tblclmnDone.setCellValueFactory(new PropertyValueFactory<>("IsCompleted"));

        updateGUI(Event.UPDATE_ALL, null);
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
        if (weapons.size() > 0) {
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
        if (healingPotions.size() > 0) {
            int tmpCounter = 0;
            int currentPotionIndex = 0;

            for (HealingPotion hp : healingPotions) {
                if (player.getCurrentPotion() != null && hp.getId() == player.getCurrentPotion().getId()) {
                    currentPotionIndex = tmpCounter;
                }

                cboPotions.getItems().add(hp.getName());
                tmpCounter++;
            }
            cboPotions.getSelectionModel().select(currentPotionIndex);
        } else {
            cboPotions.setVisible(false);
            btnUsePotion.setVisible(false);
        }
    }

    public void updateLists() {
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

        initializeObservableLabels();

        player.addObserver(this);
        updateGUI(Event.UPDATE_ALL, null);

        player.moveTo(player.getCurrentLocation());
    }

    public void saveCurrentWeapon() {
        String newWeaponName = cboWeapons.getSelectionModel().getSelectedItem();

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getDetails() instanceof Weapon && ii.getDetails().getName().equals(newWeaponName)) {
                player.setCurrentWeapon((Weapon) ii.getDetails());
            }
        }
    }

    public void saveCurrentPotion() {
        String newPotionName = cboPotions.getSelectionModel().getSelectedItem();

        for (InventoryItem ii : player.getInventory()) {
            if (ii.getDetails() instanceof HealingPotion && ii.getDetails().getName().equals(newPotionName)) {
                player.setCurrentPotion((HealingPotion) ii.getDetails());
            }
        }
    }

    public void initializeObservableLabels() {
        lblGold.textProperty().bind(player.goldProperty().asString());
        lblHitPoints.textProperty().bind(player.currentHitPointsProperty().asString());
        lblExperience.textProperty().bind(player.experiencePointsProperty().asString());
        lblLevel.textProperty().bind(player.levelProperty().asString());
    }

    @Override
    public void updateGUI(Event eventType, Object content) {
        switch (eventType) {
            case UPDATE_TABLE_INVENTORY:
                updateInventoryListUI();
                break;
            case UPDATE_TABLE_QUESTS:
                updateQuestListUI();
                break;
            case UPDATE_POTION_COMBO:
                updatePotionListUI();
                break;
            case UPDATE_WEAPON_COMBO:
                updateWeaponListUI();
                break;
            case UPDATE_ALL:
                updateLists();
                break;
            case UPDATE_TXT_MESSAGES:
                txtMessages.appendText((String) content);
                break;
            case UPDATE_LOCATION:
                btnNorth.setVisible(player.getCurrentLocation().getLocationToNorth() != null);
                btnEast.setVisible(player.getCurrentLocation().getLocationToEast() != null);
                btnSouth.setVisible(player.getCurrentLocation().getLocationToSouth() != null);
                btnWest.setVisible(player.getCurrentLocation().getLocationToWest() != null);

                txtLocation.setText(player.getCurrentLocation().getName() + "\n");
                txtLocation.appendText(player.getCurrentLocation().getDescription() + "\n");

                btnTrade.setVisible(player.getCurrentLocation().getVendorWorkingHere() != null);

                if (player.getCurrentLocation().getMonsterLivingHere() != null) {
                    cboWeapons.setVisible(player.getWeapons().size() > 0);
                    cboPotions.setVisible(player.getPotions().size() > 0);
                    btnUseWeapon.setVisible(player.getWeapons().size() > 0);
                    btnUsePotion.setVisible(player.getPotions().size() > 0);
                } else {
                    MusicPlayer.turnOffFightMusic();
                    if (!MusicPlayer.isMenuMusicPlaying()) {
                        MusicPlayer.turnOnMenuMusic();
                    }
                    cboWeapons.setVisible(false);
                    cboPotions.setVisible(false);
                    btnUseWeapon.setVisible(false);
                    btnUsePotion.setVisible(false);
                }


        }
    }

    public void clickTrade() {
        MusicPlayer.turnOffMenuMusic();
        MusicPlayer.turnOnShopMusic();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tradeUI.fxml"));
            Parent root = fxmlLoader.load();

            SecondController secondController = fxmlLoader.getController();
            secondController.setCurrentPlayer(this.player);
            secondController.setController(this);

            Stage secondStage = new Stage();
            secondStage.setTitle("Trade");
            secondStage.setScene(new Scene(root));
            secondStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setPlayer(Player playerNew) {
        this.player = playerNew;
    }
}