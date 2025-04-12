package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;

public class MoveCommand extends Command {
    Room destinationRoom;

    public MoveCommand(Character character, Room room) {
        super(character);
        this.destinationRoom = room;
    }

    @Override
    public String getName() {
        return "Move";
    }

    @Override
    public String getArtifactName() {
        return destinationRoom.getName();
    }

    @Override
    public boolean execute() {
        character.move(destinationRoom);
        return true;
    }

}
