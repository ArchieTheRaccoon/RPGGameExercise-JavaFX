package engine;

import java.util.ArrayList;

public class Monster extends LivingCreature {
    private String name;
    private int id, maximumDamage, rewardExperiencePoints, rewardGold;
    private ArrayList<LootItem> lootTable;

    public Monster(int id, String name,  int maximumDamage, int currentHitPoints, int maximumHitPoints, int rewardExperiencePoints, int rewardGold) {
        super(currentHitPoints, maximumHitPoints);
        this.name = name;
        this.maximumDamage = maximumDamage;
        this.rewardExperiencePoints = rewardExperiencePoints;
        this.rewardGold = rewardGold;
        this.id = id;
        lootTable = new ArrayList<LootItem>();
    }

    public int getMaximumDamage() {
        return maximumDamage;
    }

    public void setMaximumDamage(int maximumDamage) {
        this.maximumDamage = maximumDamage;
    }

    public int getRewardExperiencePoints() {
        return rewardExperiencePoints;
    }

    public void setRewardExperiencePoints(int rewardExperiencePoints) {
        this.rewardExperiencePoints = rewardExperiencePoints;
    }

    public int getRewardGold() {
        return rewardGold;
    }

    public void setRewardGold(int rewardGold) {
        this.rewardGold = rewardGold;
    }

    public ArrayList<LootItem> getLootTable() {
        return lootTable;
    }

    public void setLootTable(ArrayList<LootItem> lootTable) {
        this.lootTable = lootTable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
