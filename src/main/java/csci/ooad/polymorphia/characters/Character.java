package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.Die;
import csci.ooad.polymorphia.PolymorphiaEventType;
import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.commands.Command;
import csci.ooad.polymorphia.observers.EventBusEnum;
import csci.ooad.polymorphia.strategy.DoNothingStrategy;
import csci.ooad.polymorphia.strategy.PlayStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.abs;
import static java.lang.Math.min;


public abstract class Character implements Comparable<Character> {
    private static final Logger logger = LoggerFactory.getLogger(Character.class);

    static final Double DEFAULT_INITIAL_HEALTH = 5.0;
    static final Double HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME = 0.5;
    static final Double HEALTH_LOST_IN_MOVING_ROOMS = 0.25;

    protected String name;
    private Double health;
    PlayStrategy playStrategy;

    private Room currentLocation;

    public Room getCurrentLocation() {
        return currentLocation;
    }

    void setCurrentLocation(Room currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Character(String name) {
        this.name = name;
        this.health = DEFAULT_INITIAL_HEALTH;
    }

    public Character(String name, Double initialHealth) {
        this(name);
        this.health = initialHealth;
        this.playStrategy = new DoNothingStrategy();
    }

    PlayStrategy getPlayStrategy() {
        return this.playStrategy;
    }
    public void setPlayStrategy(PlayStrategy playStrategy) {
        this.playStrategy = playStrategy;
    }

    @Override
    public int compareTo(Character otherCharacter) {
        return getHealth().compareTo(otherCharacter.getHealth());
    }

    @Override
    public String toString() {
        return getName() + "(" + getFormattedHealth() + ")";
    }

    String getFormattedHealth() {
        return getFormattedValue(getHealth());
    }

    String getFormattedValue(Double value) {
        return String.format("%.2f", value);
    }
    public void loseFightDamage(double healthPoints) {
        loseHealth(healthPoints);
    }

    public void loseMovingHealth(double healthPoints) {
        loseHealth(healthPoints);
    }

    public void loseHealth(double healthPoints) {
        if (health == 0) {
            return;     // already dead, probably called for mandatory health loss for having a fight
        }
        // The smallest our health value can be is zero
        health -= min(healthPoints, getHealth());

        if (health == 0) {
            logger.info("{} just died!", name);
            EventBusEnum.INSTANCE.broadcast(PolymorphiaEventType.Death, this);
        }
    }

    private void broadcast(PolymorphiaEventType event) {
        EventBusEnum.INSTANCE.broadcast(event, this);
    }

    public Double getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public String getRawName() {
        return getName();
    }

    public Boolean isAlive() {
        return getHealth() > 0;
    }

    public Boolean isVisible() {
        return true;
    }

    public void eat(Artifact foodItem) {
        logger.info(getName() + " just ate " + foodItem);
        this.gainHealth(foodItem.getHealthValue());
    }

    public Boolean isAdventurer() {
        return false;
    }
    public Boolean isCreature() {
        return false;
    }
    public Boolean isDemon() {
        return false;
    }

    public boolean fightToTheDeath(Character foe) {
        boolean result = false;
        while (isAlive() && foe.isAlive()) {
            result = fight(foe);
        }
        return result;
    }

    public boolean fight(Character foe) {
        EventBusEnum.INSTANCE.broadcast(PolymorphiaEventType.FightOccurred, this);

        Integer adventurerRoll = Die.rollSixSided();
        Integer creatureRoll = Die.rollSixSided();
        Double loserDamage = abs(adventurerRoll - creatureRoll) + HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME;
        logger.info(getName() + " is fighting " + foe);

        logger.info(getName() + " rolled " + adventurerRoll);
        logger.info(foe + " rolled " + creatureRoll);

        boolean wonFight = false;
        if (adventurerRoll > creatureRoll) {
            double originalFoeHealth = foe.getHealth();
            foe.loseFightDamage(loserDamage);
            loseFightDamage(HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME);
            wonFight = true;

            if (!foe.isAlive()) {
                killedFoe(foe, originalFoeHealth);
            }
        } else if (creatureRoll > adventurerRoll) {
            loseFightDamage(loserDamage);
            foe.loseFightDamage(HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME);
        } else {
            // tied, but still need to lose fight damage
            // I don't have this outside of the if-then-else because I only want to call loseFightDamage once
            loseFightDamage(HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME);
            foe.loseFightDamage(HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME);
        }

        return wonFight;
    }

    private void killedFoe(Character foe, double originalFoeHealth) {
        String message = getName() + " killed " + foe.getName() +
                " and gained the foe's health at the start of the fight: " +
                getFormattedValue(originalFoeHealth);
        logger.info(message);
        gainHealth(originalFoeHealth);
        EventBusEnum.INSTANCE.broadcast(PolymorphiaEventType.Killed, message);
    }

    public void enterRoom(Room room) {
        if (getCurrentLocation() != null) {
            if (getCurrentLocation().equals(room)) {
                return;
            }
            getCurrentLocation().remove(this);
        }
        setCurrentLocation(room);
    }

    public void doAction() {
        Command command = getPlayStrategy().selectAction(this, getCurrentLocation());
        command.execute();
    }

    public void move(Room nextLocation) {
        if (nextLocation != null) {
            logger.info(getName() + " moved from " + getCurrentLocation().getName() + " to " + nextLocation.getName());
            nextLocation.enter(this);
            loseMovingHealth(HEALTH_LOST_IN_MOVING_ROOMS);
        } else {
            logger.warn(getCurrentLocation().getName() + " has no neighbors!");
        }
    }

    protected void gainHealth(double healthValue) {
        this.health += healthValue;
        logger.info(getName() + " gained health: " + getFormattedHealth());
    }

    public void deCloak(Room room) {
        // Do nothing
    }

    public boolean isCloaked() {
        return false;
    }
}
