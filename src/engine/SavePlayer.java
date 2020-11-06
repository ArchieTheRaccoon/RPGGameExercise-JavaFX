package engine;

public class SavePlayer {
    private static Player savedPlayer = null;

    public static void setSavedPlayer(Player player) {
        savedPlayer = player;
    }

    public static Player getSavedPlayer() {
        return savedPlayer;
    }
}
