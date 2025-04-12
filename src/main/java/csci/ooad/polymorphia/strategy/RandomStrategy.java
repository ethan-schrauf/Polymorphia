package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;

import java.util.List;
import java.util.Random;

public class RandomStrategy extends AdventurerStrategy {
    private final Random random = new Random();

    @Override
    public Command selectAction(Character character, Room room) {
        // This returns a list of lists of commands. Each sublist is a list of commands of the same type.
        List<List<Command>> possibleCommands = getAllPossibleCommands(character, room);
        // First, randomly select a type of command
        List<Command> commands = possibleCommands.get(random.nextInt(possibleCommands.size()));
        // Then, randomly select a command of that type
        return commands.get(random.nextInt(commands.size()));
    }

}
