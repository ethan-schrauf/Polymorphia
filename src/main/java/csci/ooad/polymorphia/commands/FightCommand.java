package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.characters.Character;

public class FightCommand extends Command {
    Character foe;

    public FightCommand(Character character, Character foe) {
        super(character);
        this.foe = foe;
    }

    @Override
    public String getName() {
        return "Fight";
    }

    @Override
    public String getArtifactName() {
        return foe.getName();
    }
    @Override
    public boolean execute() {
        return character.fight(foe);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + foe;
    }
}
