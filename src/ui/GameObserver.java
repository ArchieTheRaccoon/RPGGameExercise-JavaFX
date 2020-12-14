package ui;

public interface GameObserver {
    public enum Event {
        UPDATE_ALL, UPDATE_TABLE_INVENTORY, UPDATE_TABLE_QUESTS, UPDATE_WEAPON_COMBO, UPDATE_POTION_COMBO, UPDATE_TXT_MESSAGES, UPDATE_LOCATION
    }

    void updateGUI(Event eventType, Object content);
}
