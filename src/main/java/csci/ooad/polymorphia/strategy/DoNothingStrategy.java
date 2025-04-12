package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;

public class DoNothingStrategy extends PlayStrategy {

    @Override
    public Command selectAction(Character character, Room currentRoom) {
        // Do nothing
        return commandFactory.createNullCommand();
    }
}
