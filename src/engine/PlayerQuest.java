package engine;

public class PlayerQuest {
    private Quest details;
    private boolean isCompleted;

    public PlayerQuest(Quest details) {
        this.details = details;
    }

    public Quest getDetails() {
        return details;
    }
    public void setDetails(Quest details) {
        this.details = details;
    }
    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
