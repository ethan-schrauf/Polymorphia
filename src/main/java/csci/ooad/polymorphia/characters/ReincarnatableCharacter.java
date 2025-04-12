package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.artifacts.Artifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ReincarnatableCharacter extends DecoratedCharacter {
    public static final CharacterFactory CHARACTER_FACTORY = new CharacterFactory();
    private static final Logger logger = LoggerFactory.getLogger(ReincarnatableCharacter.class);

    Artifact potion;

    public ReincarnatableCharacter(Character character, Artifact potion) {
        super(character);
        this.potion = potion;
    }

    @Override
    public String getName() {
        return "Reincarnatable " + myself.getName();
    }

    @Override
    public void loseHealth(double healthToLose) {
        myself.loseHealth(healthToLose);
        if (!isAlive()) {
            reincarnate();
        }
    }

    private void reincarnate() {
        Character rebornSelf = CHARACTER_FACTORY.createCharacter(
                "Reborn " + getRawName(),
                potion.getHealthValue(),
                getPlayStrategy());
        Optional<Character> decoratedSelf = getCurrentLocation().getMe(this);
        if (decoratedSelf.isPresent()) {
            logger.info(getName() + " just reincarnated with health " + String.format("%.2f", potion.getHealthValue()) + "!");
            rebornSelf.enterRoom(getCurrentLocation());
            getCurrentLocation().replace(decoratedSelf.get(), rebornSelf);
        } else {
            logger.warn("Could not find decorated self in current location!");
        }
    }
}
