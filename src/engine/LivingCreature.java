package engine;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LivingCreature extends ObservableGamePart {
    private int maximumHitPoints;
    IntegerProperty currentHitPoints = new SimpleIntegerProperty();

    public LivingCreature(int currentHitPoints, int maximumHitPoints) {
        setCurrentHitPoints(currentHitPoints);
        this.maximumHitPoints = maximumHitPoints;
    }

    public int getCurrentHitPoints() {
        return currentHitPoints.get();
    }

    public IntegerProperty currentHitPointsProperty() {
        return currentHitPoints;
    }

    public void setCurrentHitPoints(int currentHitPoints) {
        this.currentHitPoints.set(currentHitPoints);
    }

    public int getMaximumHitPoints() {
        return maximumHitPoints;
    }

    public void setMaximumHitPoints(int maximumHitPoints) {
        this.maximumHitPoints = maximumHitPoints;
    }
}
