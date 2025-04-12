package csci.ooad.polymorphia.artifacts;

public class Cloak extends Artifact {
    int power;

    public Cloak(String name, int power) {
        super(ArtifactType.Cloak, name);
        this.power = power;
    }

    @Override
    public String toString() {
        return getName() + " cloak(" + power + ")";
    }

    public void losePower() {
        power -= 1;
    }

    public boolean hasNoPower() {
        return power == 0;
    }
}
