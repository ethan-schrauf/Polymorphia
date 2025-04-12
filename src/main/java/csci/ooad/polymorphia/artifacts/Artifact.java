package csci.ooad.polymorphia.artifacts;

public abstract class Artifact {
    protected final double healthValue;
    protected final double strength;
    protected final double movingCost;
    protected final double attackValue;

    public enum ArtifactType {
        Food,
        Armor,
        Cloak,
        Potion,
        NeutronBomb,
        Shield,
        Sword,
        MagicWand,
        Irradiator
    }

    private final ArtifactType type;
    private final String name;

    public Artifact(ArtifactType type, String name, double healthValue, double strength, double movingCost, double attackValue) {
        this.healthValue = healthValue;
        this.type = type;
        this.name = name;
        this.strength = strength;
        this.movingCost = movingCost;
        this.attackValue = attackValue;
    }

    public Artifact(ArtifactType type, String name) {
        this(type, name, 0.0, 0.0, 0.0, 0);
    }

    public Artifact(ArtifactType type, String name, double healthValue) {
        this(type, name, healthValue, 0.0, 0.0, 0);
    }

    public boolean isType(ArtifactType type) {
        return this.type == type;
    }
    public String getName() {
        return name;
    }

    public double getHealthValue() {
        return healthValue;
    }

    public double getAttackValue() {
        return attackValue;
    }

    public double getDefenseValue() {
        return strength;
    }

    public double getMovingCost() {
        return movingCost;
    }

    @Override
    public String toString() {
        return name;
    }
}
