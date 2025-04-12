package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.artifacts.Artifact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.max;

public class ArmoredCharacter extends DecoratedCharacter {
    private static final Logger logger = LoggerFactory.getLogger(ArmoredCharacter.class);

    Artifact armor;

    public ArmoredCharacter(Character self, Artifact armor) {
        super(self);
        this.armor = armor;
    }

    @Override
    public void loseFightDamage(double damage) {
        double actualLoss = max(0.0, damage - armor.getDefenseValue());
        myself.loseFightDamage(actualLoss);
        logger.info(armor.getName() + " reduced damage for " + myself.getName() + " to " + actualLoss + " from " + getFormattedValue(damage));
    }

    @Override
    public void loseMovingHealth(double healthPoints) {
        myself.loseHealth(healthPoints + armor.getMovingCost());
    }

    @Override
    public String getName() {
        return armor.getName() + "-armored " + super.getName();
    }
}
