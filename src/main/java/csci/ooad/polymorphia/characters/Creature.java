package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.strategy.CreatureStrategy;

public class Creature extends Character {
    static final Double DEFAULT_INITIAL_HEALTH = 3.0;

    public Creature(String name) {
        this(name, DEFAULT_INITIAL_HEALTH);
    }

    public Creature(String name, double health) {
        super(name, health);
        this.setPlayStrategy(new CreatureStrategy());
    }

    @Override
    public Boolean isCreature() {
        return true;
    }

    @Override
    public String toString() {
        return "Cre: " + super.toString();
    }
}
