package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.characters.Character;

public class EatCommand extends ArtifactCommand {

    public EatCommand(Character character, Room room, Artifact food) {
        super(character, room, food);
    }

    @Override
    public String getName() {
        return "Eat";
    }

    @Override
    public boolean execute() {
        if (!room.getAllFood().contains(artifact)) {
            return false;
        }
        room.removeArtifact(artifact);
        character.eat(artifact);
        return true;
    }
}
