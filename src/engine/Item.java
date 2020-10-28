package engine;

public class Item {
    private String name, namePlural;
    private int id;

    public Item(int id, String name, String namePlural) {
        this.id = id;
        this.name = name;
        this.namePlural = namePlural;
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

}
