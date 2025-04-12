package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;

public class FighterStrategy extends AdventurerStrategy {

    @Override
    public Command selectAction(Character myself, Room currentRoom) {
        if (currentRoom.hasArmor()) {
            return createWearCommand(myself, currentRoom);
        }
        return super.selectAction(myself, currentRoom);
    }
}
