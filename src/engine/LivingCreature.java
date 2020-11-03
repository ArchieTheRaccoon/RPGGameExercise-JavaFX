package engine;

public class LivingCreature extends ObservableGamePart {
    private int currentHitPoints, maximumHitPoints;

    public LivingCreature(int currentHitPoints, int maximumHitPoints) {
        this.currentHitPoints = currentHitPoints;
        this.maximumHitPoints = maximumHitPoints;
    }

    public int getCurrentHitPoints() {
        return currentHitPoints;
    }

    public void setCurrentHitPoints(int currentHitPoints) {
        int oldCurrentHitPoints = this.currentHitPoints;
        this.currentHitPoints = currentHitPoints;
        firePropertyChange("currentHitPoints", oldCurrentHitPoints, this.currentHitPoints);
    }

    public int getMaximumHitPoints() {
        return maximumHitPoints;
    }

    public void setMaximumHitPoints(int maximumHitPoints) {
        this.maximumHitPoints = maximumHitPoints;
    }

}
