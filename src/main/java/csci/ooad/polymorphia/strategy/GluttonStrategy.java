package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;

import java.util.Optional;

public class GluttonStrategy extends AdventurerStrategy {

    @Override
    public Command selectAction(Character myself, Room currentRoom) {
        Optional<Character> creature = currentRoom.getCreature();
        if (shouldFight(currentRoom) && creature.isPresent()) {
            return commandFactory.createFightAndUpgradeCommand(myself, creature.get());
        }
        if (currentRoom.hasFood()) {
            return createEatCommand(myself, currentRoom);
        }

        return super.selectAction(myself, currentRoom);
    }

    boolean shouldFight(Room room) {
        return !room.hasDemon() && !room.hasFood() && room.hasLivingCreatures();
    }
}
