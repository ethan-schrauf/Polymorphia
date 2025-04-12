package csci.ooad.polymorphia.artifacts;

public class Armor extends Artifact {
    public static double DEFAULT_STRENGTH = 1.0;
    public static double DEFAULT_MOVING_COST = 1.0;
    public static double HEALTH_VALUE = 1.0;
    public static double ATTACK_VALUE = 1.0;

    public Armor(String name) {
        this(name, DEFAULT_STRENGTH, DEFAULT_MOVING_COST);
    }

    public Armor(String name, double strength) {
        this(name, strength, DEFAULT_MOVING_COST);
    }

    public Armor(String name, double strength, double movingCost) {
        super(ArtifactType.Armor, name, HEALTH_VALUE, strength, movingCost, ATTACK_VALUE);
    }

    @Override
    public String toString() {
        String formattedStrength = String.format("%.2f", getDefenseValue());
        return getName() + " armor(strength:" + formattedStrength + ", movingCost:" + getMovingCost() + ")";
    }
}
