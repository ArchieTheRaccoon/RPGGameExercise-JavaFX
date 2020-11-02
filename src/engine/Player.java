package engine;

import java.util.ArrayList;

public class Player extends LivingCreature {
    private int gold, experiencePoints, level;
    private ArrayList<InventoryItem> inventory;
    private ArrayList<PlayerQuest> quests;
    private Location currentLocation;

    public Player(int currentHitPoints, int maximumHitPoints, int gold, int experiencePoints) {
        super(currentHitPoints, maximumHitPoints);
        this.gold = gold;
        this.experiencePoints = experiencePoints;
        inventory = new ArrayList<InventoryItem>();
        quests = new ArrayList<PlayerQuest>();
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getLevel() {
        return (experiencePoints / 100) + 1;
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
            for (InventoryItem ii : inventory) {
                if (ii.getDetails().getId() == qci.getDetails().getId()) {
                    ii.setQuantity(ii.getQuantity() - qci.getQuantity());
                    break;
                }
            }
        }
    }

    public void addItemToInventory(Item itemToAdd) {
        for (InventoryItem ii : inventory) {
            if (ii.getDetails().getId() == itemToAdd.getId()) {
                ii.setQuantity(ii.getQuantity() + 1);
                return;
            }
        }
        inventory.add(new InventoryItem(itemToAdd, 1));
    }

    public void markQuestCompleted(Quest quest) {
        for (PlayerQuest pq : quests) {
            if (pq.getDetails().getId() == quest.getId()) {
                pq.setCompleted(true);
                return;
            }
        }
    }
}
