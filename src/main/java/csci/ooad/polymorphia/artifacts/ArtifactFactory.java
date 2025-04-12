package csci.ooad.polymorphia.artifacts;

import csci.ooad.polymorphia.artifacts.Artifact.ArtifactType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class ArtifactFactory {
    private static final Random random = new Random();
    private static final Map<ArtifactType, String[]> NAMES = new HashMap<>();

    private static final double MINIMUM_FOOD_VALUE = 1.0;
    private static final double MAXIMUM_FOOD_VALUE = 2.0;
    private static final double MINIMUM_ARMOR_STRENGTH = 1.0;
    private static final double MAXIMUM_ARMOR_STRENGTH = 3.0;
    private static final double MINIMUM_POTION_HEALTH = 3.0;
    private static final double MAXIMUM_POTION_HEALTH = 6.0;
    private static final int MINIMUM_CLOAK_POWER = 3;
    private static final int MAXIMUM_CLOAK_POWER = 8;

    static {
        NAMES.put(ArtifactType.Food,
                new String[]{"cupcake", "apple", "banana", "steak", "salad", "fries", "burger", "pizza", "eggs",
                        "bacon", "muffin", "donut", "chicken", "pasta", "rice", "sushi", "taco", "burrito", "nachos", "chips"});
        NAMES.put(ArtifactType.Armor,
                new String[]{
                        "leather", "chainmail", "plate", "iron", "steel", "mithril", "dragon hide", "titanium", "platinum", "gold"});
        NAMES.put(ArtifactType.Cloak,
                new String[]{
                        "invisibility", "no-see-um", "Harry-Potter"});
        NAMES.put(ArtifactType.Potion,
                new String[]{
                        "Elixir of Life", "Potion of Reanimation", "Sorcerer's Serum"});
    }

    private static double getRandomFoodValue() {
        return random.nextDouble(MINIMUM_FOOD_VALUE, MAXIMUM_FOOD_VALUE);
    }

    private static double getRandomArmorStrength() {
        return random.nextDouble(MINIMUM_ARMOR_STRENGTH, MAXIMUM_ARMOR_STRENGTH);
    }

    private static int getRandomCloakPower() {
        return random.nextInt(MINIMUM_CLOAK_POWER, MAXIMUM_CLOAK_POWER);
    }

    private static double getRandomPotionHealth() {
        return random.nextDouble(MINIMUM_POTION_HEALTH, MAXIMUM_POTION_HEALTH);
    }

    private static String getName(ArtifactType type, int index) {
        String[] names = NAMES.get(type);
        return names[index % names.length];
    }

    public List<Artifact> createNumberOf(Artifact.ArtifactType type, int numberOfItems) {
        return IntStream.range(0, numberOfItems)
                .mapToObj(i -> create(type, getName(type, i)))
                .toList();
    }

    public Artifact createFood(String name) {
        return new Food(name, getRandomFoodValue());
    }

    public Artifact createArmor(String name) {
        return new Armor(name, getRandomArmorStrength());
    }

    public Artifact create(ArtifactType type, String name) {
        return switch (type) {
            case Food -> createFood(name);
            case Armor -> createArmor(name);
            case Cloak -> createCloak(name);
            case Potion -> createPotion(name);
            default -> null;
        };
    }

    public Artifact createArmor(String name, double strength, double movingCost) {
        return new Armor(name, strength, movingCost);
    }

    public Artifact createCloak(String name) {
        return createCloak(name, getRandomCloakPower());
    }

    public Artifact createCloak(String name, int power) {
        return new Cloak(name, power);
    }

    public Artifact createPotion(String name) {
        return createPotion(name, getRandomPotionHealth());
    }

    public Artifact createPotion(String name, double healthValue) {
        return new Potion(name, healthValue);
    }
}
