package csci.ooad.polymorphia.characters;

import java.util.Random;

public class Demon extends Creature {
    static final double DEFAULT_INITIAL_HEALTH_MIN = 10.0;
    static final double DEFAULT_INITIAL_HEALTH_MAX = 15.0;
    static private Random random = new Random();

    static private double getRandomDemonInitialHealth() {
        return random.nextDouble(DEFAULT_INITIAL_HEALTH_MIN, DEFAULT_INITIAL_HEALTH_MAX);
    }

    public Demon(String name) {
        super(name, getRandomDemonInitialHealth());
    }

    @Override
    public Boolean isDemon() {
        return true;
    }

    @Override
    public String toString() {
        return "Dem: " + super.toString();
    }
}
