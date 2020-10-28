package engine;

import java.util.ArrayList;

public class Quest {
    private String name, description;
    private int id, rewardExperiencePoints, rewardGold;
    private Item rewardItem;
    private ArrayList<QuestCompletionItem> questCompletionItems;

    public Quest(int id, String name, String description, int rewardExperiencePoints, int rewardGold) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rewardExperiencePoints = rewardExperiencePoints;
        this.rewardGold = rewardGold;
        questCompletionItems = new ArrayList<QuestCompletionItem>();
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Item getRewardItem() {
        return rewardItem;
    }

    public void setRewardItem(Item rewardItem) {
        this.rewardItem = rewardItem;
    }

    public ArrayList<QuestCompletionItem> getQuestCompletionItems() {
        return questCompletionItems;
    }

    public void setQuestCompletionItems(ArrayList<QuestCompletionItem> questCompletionItems) {
        this.questCompletionItems = questCompletionItems;
    }

}
