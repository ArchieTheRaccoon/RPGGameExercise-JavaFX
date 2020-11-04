package engine;

public class HealingPotion extends Item {
    private int amountToHeal;

    public HealingPotion(int id, String name, String namePlural, int amountToHeal, int price) {
        super(id, name, namePlural, price);
        this.amountToHeal = amountToHeal;
    }

    public int getAmountToHeal() {
        return amountToHeal;
    }
    public void setAmountToHeal(int amountToHeal) {
        this.amountToHeal = amountToHeal;
    }
}
