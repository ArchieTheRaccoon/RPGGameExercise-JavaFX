package engine;

public class Location {
    private String name, description;
    private int id;
    private Item itemRequiredToEnter;
    private Quest questAvailableHere;
    private Monster monsterLivingHere;
    private Location locationToNorth, locationToEast, locationToSouth, locationToWest;
    private Vendor vendorWorkingHere;
    private boolean playerHasBeenHere;


    public Location(int id, String name, String description, Item itemRequiredToEnter, Quest questAvailableHere, Monster monsterLivingHere, boolean wasHere) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemRequiredToEnter = itemRequiredToEnter;
        this.questAvailableHere = questAvailableHere;
        this.monsterLivingHere = monsterLivingHere;
        this.playerHasBeenHere = wasHere;
    }

    public boolean isPlayerHasBeenHere() {
        return playerHasBeenHere;
    }

    public void setPlayerHasBeenHere(boolean playerHasBeenHere) {
        this.playerHasBeenHere = playerHasBeenHere;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item getItemRequiredToEnter() {
        return itemRequiredToEnter;
    }

    public void setItemRequiredToEnter(Item itemRequiredToEnter) {
        this.itemRequiredToEnter = itemRequiredToEnter;
    }

    public Quest getQuestAvailableHere() {
        return questAvailableHere;
    }

    public void setQuestAvailableHere(Quest questAvailableHere) {
        this.questAvailableHere = questAvailableHere;
    }

    public Monster getMonsterLivingHere() {
        return monsterLivingHere;
    }

    public void setMonsterLivingHere(Monster monsterLivingHere) {
        this.monsterLivingHere = monsterLivingHere;
    }

    public Location getLocationToNorth() {
        return locationToNorth;
    }

    public void setLocationToNorth(Location locationToNorth) {
        this.locationToNorth = locationToNorth;
    }

    public Location getLocationToEast() {
        return locationToEast;
    }

    public void setLocationToEast(Location locationToEast) {
        this.locationToEast = locationToEast;
    }

    public Location getLocationToSouth() {
        return locationToSouth;
    }

    public void setLocationToSouth(Location locationToSouth) {
        this.locationToSouth = locationToSouth;
    }

    public Location getLocationToWest() {
        return locationToWest;
    }

    public void setLocationToWest(Location locationToWest) {
        this.locationToWest = locationToWest;
    }

    public Vendor getVendorWorkingHere() {
        return vendorWorkingHere;
    }

    public void setVendorWorkingHere(Vendor vendorWorkingHere) {
        this.vendorWorkingHere = vendorWorkingHere;
    }
}
