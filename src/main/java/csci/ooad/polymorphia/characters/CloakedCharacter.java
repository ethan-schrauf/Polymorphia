package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.artifacts.Cloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloakedCharacter extends DecoratedCharacter {
    private static final Logger logger = LoggerFactory.getLogger(CloakedCharacter.class);

    Cloak cloak;

    public CloakedCharacter(Character self, Artifact cloak) {
        super(self);
        this.cloak = (Cloak) cloak;
    }

    @Override
    public Boolean isVisible() {
        logger.info(getName() + " wasn't seen!");
        cloak.losePower();
        if (cloak.hasNoPower()) {
            logger.info(getName() + "'s cloak disintegrated");
            deCloak(getCurrentLocation());
        }
        return false;
    }

    @Override
    public String getName() {
        return cloak.getName() + "-cloaked " + super.getName();
    }

    @Override
    public void deCloak(Room room) {
        room.replace(this, myself);
    }

    @Override
    public boolean isCloaked() {
        return true;
    }
}

