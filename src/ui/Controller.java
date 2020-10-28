package ui;

import engine.Player;
import javafx.scene.control.Label;

public class Controller {
    public Label lblHitPoints;
    public Label lblGold;
    public Label lblExperience;
    public Label lblLevel;

    public void initialize() {
        Player player = new Player(10, 10, 20, 0, 1);

        lblHitPoints.setText(String.valueOf(player.getCurrentHitPoints()));
        lblGold.setText(String.valueOf(player.getGold()));
        lblExperience.setText(String.valueOf(player.getExperiencePoints()));
        lblLevel.setText(String.valueOf(player.getLevel()));
    }
}
