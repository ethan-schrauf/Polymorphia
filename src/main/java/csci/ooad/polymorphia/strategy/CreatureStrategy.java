package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;

import java.util.Optional;

public class CreatureStrategy extends PlayStrategy {

    @Override
    public Command selectAction(Character myself, Room currentRoom) {
        Optional<Character> adventurer = currentRoom.getAdventurer();
        if (adventurer.isPresent()) {
            return commandFactory.createFightCommand(myself, adventurer.get());
        } else {
            return commandFactory.createMoveCommand(myself, currentRoom.getRandomNeighbor());
        }
    }

}
