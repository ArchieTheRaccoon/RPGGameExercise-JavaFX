package engine;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.w3c.dom.*;

public class Player extends LivingCreature {
    private IntegerProperty gold = new SimpleIntegerProperty();
    private IntegerProperty experiencePoints = new SimpleIntegerProperty();
    private IntegerProperty level = new SimpleIntegerProperty(1);
    private ArrayList<InventoryItem> inventory;
    private ArrayList<PlayerQuest> quests;
    private Location currentLocation;
    private Weapon currentWeapon;

    public Player(int currentHitPoints, int maximumHitPoints, int gold, int experiencePoints) {
        super(currentHitPoints, maximumHitPoints);
        setGold(gold);
        setExperiencePoints(experiencePoints);
        setInventory(new ArrayList<InventoryItem>());
        setQuests(new ArrayList<PlayerQuest>());
    }

    public static Player createDefaultPlayer() {
        Player player = new Player(10,10,20,0);
        player.inventory.add(new InventoryItem(World.itemByID(World.ITEM_ID_RUSTY_SWORD), 1));
        player.currentLocation = World.locationByID(World.LOCATION_ID_HOME);

        return player;
    }

    public static Player createPlayerFromXml(String xmlPlayerDataPath) {
        try {
            File xmlFile = new File(xmlPlayerDataPath);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            int currentHitPoints = Integer.valueOf(doc.getElementsByTagName("currentHitPoints").item(0).getTextContent());
            int maximumHitPoints = Integer.valueOf(doc.getElementsByTagName("maximumHitPoints").item(0).getTextContent());
            int gold = Integer.valueOf(doc.getElementsByTagName("gold").item(0).getTextContent());
            int experiencePoints = Integer.valueOf(doc.getElementsByTagName("experiencePoints").item(0).getTextContent());
            int currentLocation = Integer.valueOf(doc.getElementsByTagName("currentLocation").item(0).getTextContent());

            Player player = new Player(currentHitPoints, maximumHitPoints, gold, experiencePoints);
            player.setCurrentLocation(World.locationByID(currentLocation));

            if (doc.getElementById("currentWeapon") != null) {
                int currentWeapon = Integer.valueOf(doc.getElementsByTagName("currentWeapon").item(0).getTextContent());
                player.setCurrentWeapon((Weapon) World.itemByID(currentWeapon));
            }

            NodeList iiNodeList = doc.getElementsByTagName("inventoryItem");
            NodeList pqNodeList = doc.getElementsByTagName("playerQuest");

            for (int i = 0; i < iiNodeList.getLength(); i++) {
                int id = Integer.valueOf(iiNodeList.item(i).getAttributes().item(0).getTextContent());
                int quantity = Integer.valueOf(iiNodeList.item(i).getAttributes().item(1).getTextContent());

                for (int q = 0; q < quantity; q++) {
                    player.addItemToInventory(World.itemByID(id));
                }
            }

            for (int i = 0; i < pqNodeList.getLength(); i++) {
                int id = Integer.valueOf(pqNodeList.item(i).getAttributes().item(0).getTextContent());
                boolean isCompleted = Boolean.valueOf(pqNodeList.item(i).getAttributes().item(1).getTextContent());

                PlayerQuest playerQuest = new PlayerQuest(World.questByID(id));
                playerQuest.setCompleted(isCompleted);

                player.quests.add(playerQuest);
            }

            return player;
        } catch (Exception e) {

            //If there was an error with the XML data, return a default player object
            return createDefaultPlayer();
        }
    }

    public void addExperiencePoints(int experiencePointsToAdd) {
        this.experiencePoints.set(getExperiencePoints() + experiencePointsToAdd);
        setMaximumHitPoints(getLevel() * 10);
        setLevel((experiencePoints.get() / 100) + 1);
    }

    public int getGold() {
        return gold.get();
    }

    public IntegerProperty goldProperty() {
        addExperiencePoints(0);
        return gold;
    }

    public void setGold(int gold) {
        this.gold.set(gold);
    }

    public int getExperiencePoints() {
        return experiencePoints.get();
    }

    public IntegerProperty experiencePointsProperty() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints.set(experiencePoints);
    }

    public int getLevel() {
        return ((experiencePoints.get() / 100) + 1);
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }

    public ArrayList<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<InventoryItem> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<PlayerQuest> getQuests() {
        return quests;
    }

    public void setQuests(ArrayList<PlayerQuest> quests) {
        this.quests = quests;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean hasRequiredItemToEnterThisLocation(Location location) {
        if (location.getItemRequiredToEnter() == null) {
            return true;
        }
        return inventory.stream().anyMatch(ii -> ii.getDetails().getId() == location.getItemRequiredToEnter().getId());
    }

    public boolean hasThisQuest(Quest quest) {
        return quests.stream().anyMatch(playerQuest -> playerQuest.getDetails().getId() == quest.getId());
    }

    public boolean completedThisQuest(Quest quest) {
        for (PlayerQuest playerQuest : quests) {
            if (playerQuest.getDetails().getId() == quest.getId()) {
                return playerQuest.isCompleted();
            }
        }
        return false;
    }

    public boolean hasAllQuestCompletionItems(Quest quest) {
        for (QuestCompletionItem qci : quest.getQuestCompletionItems()) {
            if (!inventory.stream().anyMatch(ii -> ii.getDetails().getId() == qci.getDetails().getId()
                    && ii.getQuantity() >= qci.getQuantity())) {
                return false;
            }
        }
        return true;
    }

    public void removeQuestCompletionItems(Quest quest) {
        for (QuestCompletionItem qci : quest.getQuestCompletionItems()) {
            InventoryItem item = inventory.stream().filter(ii -> ii.getDetails().getId() == qci.getDetails().getId()).findFirst().orElse(null);
            if (item != null) {
                item.setQuantity(item.getQuantity() - qci.getQuantity());
            }
        }
    }

    public void addItemToInventory(Item itemToAdd) {
        InventoryItem item = inventory.stream().filter(ii -> ii.getDetails().getId() == itemToAdd.getId()).findFirst().orElse(null);
        if (item == null) {
            inventory.add(new InventoryItem(itemToAdd, 1));
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    public void markQuestCompleted(Quest quest) {
        PlayerQuest playerQuest = quests.stream().filter(pq -> pq.getDetails().getId() == quest.getId()).findFirst().orElse(null);
        if (playerQuest != null) {
            playerQuest.setCompleted(true);
        }
    }

    public DOMSource toXmlDomSource() {
        try {
            Document playerData = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            //Create top-level XML node/element
            Element player = playerData.createElement("Player");
            playerData.appendChild(player);

            //Create the "Stats" child node to hold the other player statistics nodes/elements
            Element stats = playerData.createElement("Stats");
            player.appendChild(stats);

            //Create the child nodes for the "Stats" node
            Element currentHitPointsValue = playerData.createElement("currentHitPoints");
            currentHitPointsValue.appendChild(playerData.createTextNode(String.valueOf(getCurrentHitPoints())));
            stats.appendChild(currentHitPointsValue);

            Element maximumHitPoints = playerData.createElement("maximumHitPoints");
            maximumHitPoints.appendChild(playerData.createTextNode(String.valueOf(getMaximumHitPoints())));
            stats.appendChild(maximumHitPoints);

            Element goldValue = playerData.createElement("gold");
            goldValue.appendChild(playerData.createTextNode(String.valueOf(getGold())));
            stats.appendChild(goldValue);

            Element experiencePointsValue = playerData.createElement("experiencePoints");
            experiencePointsValue.appendChild(playerData.createTextNode(String.valueOf(getExperiencePoints())));
            stats.appendChild(experiencePointsValue);

            Element currentLocationValue = playerData.createElement("currentLocation");
            currentLocationValue.appendChild(playerData.createTextNode(String.valueOf(getCurrentLocation().getId())));
            stats.appendChild(currentLocationValue);

            if(currentWeapon != null) {
                Element currentWeaponValue = playerData.createElement("currentWeapon");
                currentWeaponValue.appendChild(playerData.createTextNode(String.valueOf(getCurrentWeapon().getId())));
                stats.appendChild(currentWeaponValue);
            }

            //Create "inventoryItems" child node to hold each inventoryItem node
            Element inventoryItems = playerData.createElement("inventoryItems");
            player.appendChild(inventoryItems);

            //Create an "inventoryItem" node for each item in the player's inventory
            for (InventoryItem item : inventory) {
                Element inventoryItem = playerData.createElement("inventoryItem");

                Attr idAttribute = playerData.createAttribute("ID");
                idAttribute.setValue(String.valueOf(item.getDetails().getId()));
                inventoryItem.setAttributeNode(idAttribute);

                Attr quantityAttribute = playerData.createAttribute("quantity");
                quantityAttribute.setValue(String.valueOf(item.getQuantity()));
                inventoryItem.setAttributeNode(quantityAttribute);

                inventoryItems.appendChild(inventoryItem);
            }

            //Create the "playerQuests" child node to hold each playerQuest node
            Element playerQuests = playerData.createElement("playerQuests");
            player.appendChild(playerQuests);

            //Create a "playerQuest" node for each quest the player has acquired
            for (PlayerQuest quest : quests) {
                Element playerQuest = playerData.createElement("playerQuest");

                Attr idAttribute = playerData.createAttribute("ID");
                idAttribute.setValue(String.valueOf(quest.getDetails().getId()));
                playerQuest.setAttributeNode(idAttribute);

                Attr isCompletedAttribute = playerData.createAttribute("isCompleted");
                isCompletedAttribute.setValue(String.valueOf(quest.isCompleted()));
                playerQuest.setAttributeNode(isCompletedAttribute);

                playerQuests.appendChild(playerQuest);
            }

            return new DOMSource(playerData); //The DOM document as a DOM Source, so we can save data to a XML file on the disk
        } catch (Exception e) {
            e.printStackTrace();

            //Something went wrong
            return null;
        }
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }
}
