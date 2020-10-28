package engine;

public class LivingCreature {
    private int currentHitPoints, maximumHitPoints;

    public LivingCreature(int currentHitPoints, int maximumHitPoints) {
        this.currentHitPoints = currentHitPoints;
        this.maximumHitPoints = maximumHitPoints;
    }

    public int getCurrentHitPoints() {
        return currentHitPoints;
    }

    public void setCurrentHitPoints(int currentHitPoints) {
        this.currentHitPoints = currentHitPoints;
    }

    public int getMaximumHitPoints() {
        return maximumHitPoints;
    }

    public void setMaximumHitPoints(int maximumHitPoints) {
        this.maximumHitPoints = maximumHitPoints;
    }

}
