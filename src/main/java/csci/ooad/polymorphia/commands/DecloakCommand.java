package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;

public class DecloakCommand extends ArtifactCommand {
    public DecloakCommand(Character character, Room room) {
        super(character, room);
    }

    @Override
    public boolean execute() {
        character.deCloak(room);
        return true;
    }

    @Override
    public String getName() {
        return "Decloak";
    }
}
