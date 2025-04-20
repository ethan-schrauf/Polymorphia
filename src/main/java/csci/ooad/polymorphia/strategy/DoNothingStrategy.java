package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;

import java.util.List;

public class DoNothingStrategy extends PlayStrategy {

    @Override
    public Command selectAction(Character character, Room currentRoom) {
        // Do nothing
        return commandFactory.createNullCommand();
    }

    @Override
    public List<List<Command>> getAllPossibleCommands(Character character, Room room) {
        return List.of();
    }
}
