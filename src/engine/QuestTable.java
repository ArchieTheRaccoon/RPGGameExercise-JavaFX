package engine;

import javafx.beans.property.SimpleStringProperty;

public class QuestTable {
    private SimpleStringProperty nameQuest, isCompleted;

    public QuestTable(String nameQuest, boolean isCompleted) {
        this.nameQuest = new SimpleStringProperty(nameQuest);
        this.isCompleted = new SimpleStringProperty(String.valueOf(isCompleted));
    }

    public String getNameQuest() {
        return nameQuest.get();
    }

    public SimpleStringProperty nameQuestProperty() {
        return nameQuest;
    }

    public void setNameQuest(String nameQuest) {
        this.nameQuest = new SimpleStringProperty(nameQuest);
    }

    public String getIsCompleted() {
        return isCompleted.get();
    }

    public SimpleStringProperty isCompletedProperty() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = new SimpleStringProperty(isCompleted);
    }
}
