package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;
import csci.ooad.polymorphia.commands.CommandFactory;

import java.util.List;

abstract public class PlayStrategy {
    CommandFactory commandFactory = new CommandFactory();

    abstract public Command selectAction(Character character, Room currentRoom);
    boolean shouldMove(Room room) {
        return true;
    }
    public Boolean isAPICharacter(){
        return false;
    }
    public abstract List<List<Command>> getAllPossibleCommands(Character character, Room room);
}
