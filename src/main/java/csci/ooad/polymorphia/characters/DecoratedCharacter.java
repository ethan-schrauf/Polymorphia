package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.strategy.PlayStrategy;


public class DecoratedCharacter extends Character {
    Character myself;

    public DecoratedCharacter(Character self) {
        super(self.getName());
        myself = self;
    }

    @Override
    public Room getCurrentLocation() {
        return myself.getCurrentLocation();
    }

    @Override
    void setCurrentLocation(Room room) {
        myself.setCurrentLocation(room);
    }

    @Override
    public PlayStrategy getPlayStrategy() {
        return myself.getPlayStrategy();
    }

    @Override
    public void setPlayStrategy(PlayStrategy strategy) {
        myself.setPlayStrategy(strategy);
    }

    @Override
    public int compareTo(Character other) {
        return myself.compareTo(other);
    }

    @Override
    public void loseFightDamage(double damage) {
        myself.loseFightDamage(damage);
    }

    @Override
    public void loseMovingHealth(double healthPoints) {
        myself.loseHealth(healthPoints);
    }

    @Override
    public void loseHealth(double damage) {
        myself.loseHealth(damage);
    }

    @Override
    public Double getHealth() {
        return myself.getHealth();
    }

    @Override
    public String getName() {
        return myself.getName();
    }


    @Override
    public String getRawName() {
        return myself.getRawName();
    }

    @Override
    public Boolean isAlive() {
        return myself.isAlive();
    }

    @Override
    public Boolean isAdventurer() {
        return myself.isAdventurer();
    }

    @Override
    public Boolean isCreature() {
        return myself.isCreature();
    }

    @Override
    public Boolean isDemon() {
        return myself.isDemon();
    }

    @Override
    protected void gainHealth(double healthValue) {
        myself.gainHealth(healthValue);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Character otherCharacter) {
            if (getName().equals(otherCharacter.getName())) {
                return true;
            } else {
                return myself.equals(otherCharacter);
            }
        }
        return false;
    }
}
