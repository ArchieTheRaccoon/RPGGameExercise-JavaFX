package engine;

public class Weapon extends Item {
    private int minimumDamage, maximumDamage;

    public Weapon(int id, String name, String namePlural, int minimumDamage, int maximumDamage, int price) {
        super(id, name, namePlural, price);
        this.minimumDamage = minimumDamage;
        this.maximumDamage = maximumDamage;
    }

    public int getMinimumDamage() {
        return minimumDamage;
    }
    public void setMinimumDamage(int minimumDamage) {
        this.minimumDamage = minimumDamage;
    }
    public int getMaximumDamage() {
        return maximumDamage;
    }
    public void setMaximumDamage(int maximumDamage) {
        this.maximumDamage = maximumDamage;
    }
}