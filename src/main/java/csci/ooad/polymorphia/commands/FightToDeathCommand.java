package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.characters.Character;

public class FightToDeathCommand extends FightCommand {
    public FightToDeathCommand(Character character, Character foe) {
        super(character, foe);
    }

    @Override
    public String getName() {
        return "Fight to Death";
    }

    @Override
    public boolean execute() {
        return character.fightToTheDeath(foe);
    }
}
