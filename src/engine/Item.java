package engine;

public class Item {
    private String name, namePlural;
    private int id, price;

    public Item(int id, String name, String namePluralб, int price) {
        this.id = id;
        this.name = name;
        this.namePlural = namePlural;
        this.price = price;
    }

    public String getNamePlural() {
        return namePlural;
    }

    public void setNamePlural(String namePlural) {
        this.namePlural = namePlural;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
