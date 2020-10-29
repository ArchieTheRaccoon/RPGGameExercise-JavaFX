package engine;

public class QuestTable {
    String nameQuest, isCompleted;

    public QuestTable(String nameQuest, String isCompleted) {
        this.nameQuest = nameQuest;
        this.isCompleted = isCompleted;
    }

    public String getNameQuest() {
        return nameQuest;
    }

    public void setNameQuest(String nameQuest) {
        this.nameQuest = nameQuest;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }
}
