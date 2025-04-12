package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.characters.Character;

public abstract class Command {
    Character character;

    public Command() {
    }
    public Command(Character character) {
        this.character = character;
    }

    public abstract boolean execute();

    public abstract String getName();

    public abstract String getArtifactName();

    @Override
    public String toString() {
        return getName() + " Command";
    }
}
