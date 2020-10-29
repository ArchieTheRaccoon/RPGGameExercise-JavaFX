package engine;

import java.util.ArrayList;

public class Player extends LivingCreature {
    private int gold, experiencePoints, level;
    private ArrayList<InventoryItem> inventory;
    private ArrayList<PlayerQuest> quests;
    private Location currentLocation;

    public Player(int currentHitPoints, int maximumHitPoints, int gold, int experiencePoints, int level) {
        super(currentHitPoints, maximumHitPoints);
        this.gold = gold;
        this.experiencePoints = experiencePoints;
        this.level = level;
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
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
}
