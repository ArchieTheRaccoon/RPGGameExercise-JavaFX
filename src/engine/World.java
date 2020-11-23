package engine;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static List<Item> items = new ArrayList<>();
    public static List<Monster> monsters = new ArrayList<>();
    public static List<Quest> quests = new ArrayList<>();
    public static List<Location> locations = new ArrayList<>();

    public final static int ITEM_ID_RUSTY_SWORD = 1;
    public final static int ITEM_ID_RAT_TAIL = 2;
    public final static int ITEM_ID_PIECE_OF_FUR = 3;
    public final static int ITEM_ID_SNAKE_FANG = 4;
    public final static int ITEM_ID_SNAKESKIN = 5;
    public final static int ITEM_ID_BANDITS_KNIFE = 6;
    public final static int ITEM_ID_HEALING_POTION = 7;
    public final static int ITEM_ID_SPIDER_FANG = 8;
    public final static int ITEM_ID_SPIDER_SILK = 9;
    public final static int ITEM_ID_ADVENTURER_PASS = 10;

    public final static int MONSTER_ID_RAT = 1;
    public final static int MONSTER_ID_SNAKE = 2;
    public final static int MONSTER_ID_GIANT_SPIDER = 3;

    public final static int QUEST_ID_CLEAR_ALCHEMIST_GARDEN = 1;
    public final static int QUEST_ID_CLEAR_FARMERS_FIELD = 2;

    public final static int LOCATION_ID_HOME = 1;
    public final static int LOCATION_ID_TOWN_SQUARE = 2;
    public final static int LOCATION_ID_GUARD_POST = 3;
    public final static int LOCATION_ID_ALCHEMIST_HUT = 4;
    public final static int LOCATION_ID_ALCHEMISTS_GARDEN = 5;
    public final static int LOCATION_ID_FARMHOUSE = 6;
    public final static int LOCATION_ID_FARM_FIELD = 7;
    public final static int LOCATION_ID_BRIDGE = 8;
    public final static int LOCATION_ID_SPIDER_FIELD = 9;

    public final static int UNSELLABLE_ITEM_PRICE = -1;

    public World() {
        populateItems();
        populateMonsters();
        populateQuests();
        populateLocations();
    }

    private static void populateItems() {
        items.add(new Weapon(ITEM_ID_RUSTY_SWORD, "Rusty sword", "Rusty swords", 0, 5, 5));
        items.add(new Item(ITEM_ID_RAT_TAIL, "Rat tail", "Rat tails", 1));
        items.add(new Item(ITEM_ID_PIECE_OF_FUR, "Piece of fur", "Pieces of fur", 1));
        items.add(new Item(ITEM_ID_SNAKE_FANG, "Snake fang", "Snake fangs", 1));
        items.add(new Item(ITEM_ID_SNAKESKIN, "Snakeskin", "Snakeskins", 2));
        items.add(new Weapon(ITEM_ID_BANDITS_KNIFE, "Bandit's Knife", "Bandit's Knifes", 0, 7, 150));
        items.add(new HealingPotion(ITEM_ID_HEALING_POTION, "Healing potion", "Healing potions", 5, 3));
        items.add(new Item(ITEM_ID_SPIDER_FANG, "Spider fang", "Spider fangs", 1));
        items.add(new Item(ITEM_ID_SPIDER_SILK, "Spider silk", "Spider silks", 1));
        items.add(new Item(ITEM_ID_ADVENTURER_PASS, "Adventurer pass", "Adventurer passes", UNSELLABLE_ITEM_PRICE));
    }

    private static void populateMonsters() {
        Monster rat = new Monster(MONSTER_ID_RAT, "Rat", 5, 3, 5, 3, 3);
        rat.getLootTable().add(new LootItem(itemByID(ITEM_ID_RAT_TAIL), 75, false));
        rat.getLootTable().add(new LootItem(itemByID(ITEM_ID_PIECE_OF_FUR), 75, true));

        Monster snake = new Monster(MONSTER_ID_SNAKE, "Snake", 5, 3, 5, 3, 3);
        snake.getLootTable().add(new LootItem(itemByID(ITEM_ID_SNAKE_FANG), 75, false));
        snake.getLootTable().add(new LootItem(itemByID(ITEM_ID_SNAKESKIN), 75, true));

        Monster giantSpider = new Monster(MONSTER_ID_GIANT_SPIDER, "Giant spider", 20, 5, 40, 10, 10);
        giantSpider.getLootTable().add(new LootItem(itemByID(ITEM_ID_SPIDER_FANG), 75, true));
        giantSpider.getLootTable().add(new LootItem(itemByID(ITEM_ID_SPIDER_SILK), 25, false));

        monsters.add(rat);
        monsters.add(snake);
        monsters.add(giantSpider);
    }

    private static void populateQuests() {
        Quest clearAlchemistGardenQuest = new Quest(QUEST_ID_CLEAR_ALCHEMIST_GARDEN, "Clear the alchemist's garden",
                "Kill rats in the alchemist's garden and bring back 3 rat tails. You will receive a healing potion and 10 gold pieces.", 20, 10);

        clearAlchemistGardenQuest.getQuestCompletionItems().add(new QuestCompletionItem(itemByID(ITEM_ID_RAT_TAIL), 3));
        clearAlchemistGardenQuest.setRewardItem(itemByID(ITEM_ID_HEALING_POTION));

        Quest clearFarmersField = new Quest(QUEST_ID_CLEAR_FARMERS_FIELD, "Clear the farmer's field",
                "Kill snakes in the farmer's field and bring back 3 snake fangs. You will receive an adventurer's pass and 20 gold pieces", 20, 20);

        clearFarmersField.getQuestCompletionItems().add(new QuestCompletionItem(itemByID(ITEM_ID_SNAKE_FANG), 3));
        clearFarmersField.setRewardItem(itemByID(ITEM_ID_ADVENTURER_PASS));

        quests.add(clearAlchemistGardenQuest);
        quests.add(clearFarmersField);
    }

    private static void populateLocations() {
        // Create each location
        Location home = new Location(LOCATION_ID_HOME, "Home", "Your house. You really need to clean up your room!", null, null, null, false);

        Location townSquare = new Location(LOCATION_ID_TOWN_SQUARE, "Town square", "You see a fountain.", null, null, null, false);
        Vendor greiratTheMasterThief = new Vendor("Greirat the Master-Thief");
        greiratTheMasterThief.addItemToInventory(itemByID(ITEM_ID_PIECE_OF_FUR), 5);
        greiratTheMasterThief.addItemToInventory(itemByID(ITEM_ID_RAT_TAIL), 3);
        greiratTheMasterThief.addItemToInventory(itemByID(ITEM_ID_BANDITS_KNIFE), 1);
        townSquare.setVendorWorkingHere(greiratTheMasterThief);

        Location alchemistHut = new Location(LOCATION_ID_ALCHEMIST_HUT, "Alchemist's hut", "There are many strange plants on the shelves", null, null, null, false);
        alchemistHut.setQuestAvailableHere(questByID(QUEST_ID_CLEAR_ALCHEMIST_GARDEN));

        Location alchemistsGarden = new Location(LOCATION_ID_ALCHEMISTS_GARDEN, "Alchemist's garden", "Many plants are growing here.", null, null, null, false);
        alchemistsGarden.setMonsterLivingHere(monsterByID(MONSTER_ID_RAT));

        Location farmhouse = new Location(LOCATION_ID_FARMHOUSE, "Farmhouse", "There is a small farmhouse, with a farmer in front.", null, null, null, false);
        farmhouse.setQuestAvailableHere(questByID(QUEST_ID_CLEAR_FARMERS_FIELD));

        Location farmersField = new Location(LOCATION_ID_FARM_FIELD, "Farmer's field", "You see rows of vegetables growing here.", null, null, null, false);
        farmersField.setMonsterLivingHere(monsterByID(MONSTER_ID_SNAKE));

        Location guardPost = new Location(LOCATION_ID_GUARD_POST, "Guard post", "There is a large, tough-looking guard here.", itemByID(ITEM_ID_ADVENTURER_PASS), null, null, false);

        Location bridge = new Location(LOCATION_ID_BRIDGE, "Bridge", "A stone bridge crosses a wide river.", null, null, null, false);

        Location spiderField = new Location(LOCATION_ID_SPIDER_FIELD, "Forest", "You see spider webs covering the trees in this forest.", null, null, null, false);
        spiderField.setMonsterLivingHere(monsterByID(MONSTER_ID_GIANT_SPIDER));

        // Link the locations together
        home.setLocationToNorth(townSquare);

        townSquare.setLocationToNorth(alchemistHut);
        townSquare.setLocationToSouth(home);
        townSquare.setLocationToEast(guardPost);
        townSquare.setLocationToWest(farmhouse);

        farmhouse.setLocationToEast(townSquare);
        farmhouse.setLocationToWest(farmersField);

        farmersField.setLocationToEast(farmhouse);

        alchemistHut.setLocationToSouth(townSquare);
        alchemistHut.setLocationToNorth(alchemistsGarden);

        alchemistsGarden.setLocationToSouth(alchemistHut);

        guardPost.setLocationToEast(bridge);
        guardPost.setLocationToWest(townSquare);

        bridge.setLocationToWest(guardPost);
        bridge.setLocationToEast(spiderField);

        spiderField.setLocationToWest(bridge);

        // Add the locations to the static list
        locations.add(home);
        locations.add(townSquare);
        locations.add(guardPost);
        locations.add(alchemistHut);
        locations.add(alchemistsGarden);
        locations.add(farmhouse);
        locations.add(farmersField);
        locations.add(bridge);
        locations.add(spiderField);
    }

    public static Item itemByID(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public static Monster monsterByID(int id) {
        for (Monster monster : monsters) {
            if (monster.getId() == id) {
                return monster;
            }
        }
        return null;
    }

    public static Quest questByID(int id) {
        for (Quest quest : quests) {
            if (quest.getId() == id) {
                return quest;
            }
        }
        return null;
    }

    public static Location locationByID(int id) {
        for (Location location : locations) {
            if (location.getId() == id) {
                return location;
            }
        }
        return null;
    }
}
