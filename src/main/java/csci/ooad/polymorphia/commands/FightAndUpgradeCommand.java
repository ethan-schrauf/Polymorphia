package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.strategy.FighterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FightAndUpgradeCommand extends FightCommand {
    private static final Logger logger = LoggerFactory.getLogger(FightAndUpgradeCommand.class);

    public FightAndUpgradeCommand(Character character, Character foe) {
        super(character, foe);
    }

    @Override
    public boolean execute() {
        boolean iWonFight = super.execute();
        if (iWonFight) {
            character.setPlayStrategy(new FighterStrategy());
            logger.info("Glutton won fight! Changing strategy to fighter!");
        }
        return iWonFight;
    }
}
