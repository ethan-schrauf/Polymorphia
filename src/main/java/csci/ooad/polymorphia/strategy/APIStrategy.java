package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;

public class APIStrategy extends AdventurerStrategy{
    Command command;
    public APIStrategy(Command command) {
        this.command = command;
    }
    @Override
    public Command selectAction(Character character, Room room) {
        return command;
    }
    public Boolean isAPICharacter(){
        return true;
    }
}
