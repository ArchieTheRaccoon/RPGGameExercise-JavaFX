package ui;

public interface GameObserver {
    public enum Event {
        UPDATE_ALL, UPDATE_TABLE_INVENTORY, UPDATE_TABLE_QUESTS, UPDATE_WEAPON_COMBO, UPDATE_POTION_COMBO
    }

    void updateGUI(Event eventType, Object content);
}
