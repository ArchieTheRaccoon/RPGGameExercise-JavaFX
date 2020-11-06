package engine;

public class SavePlayer {
    private static Player savedPlayer;

    public static void setSavedPlayer(Player player) {
        savedPlayer = player;
    }

    public static Player getSavedPlayer() {
        return savedPlayer;
    }
}
