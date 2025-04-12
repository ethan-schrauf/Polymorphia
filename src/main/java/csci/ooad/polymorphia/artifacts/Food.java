package csci.ooad.polymorphia.artifacts;

public class Food extends Artifact {
    public static double DEFAULT_FOOD_HEALTH_VALUE = 1.0;

    public Food(String name) {
        this(name, DEFAULT_FOOD_HEALTH_VALUE);
    }

    public Food(String name, double healthValue) {
        super(ArtifactType.Food, name, healthValue);
    }

    @Override
    public String toString() {
        String formattedHealth = String.format("%.2f", healthValue);
        return getName() + "(" + formattedHealth + ")";
    }
}
