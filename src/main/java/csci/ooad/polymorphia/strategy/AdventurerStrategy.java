package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

abstract public class AdventurerStrategy extends PlayStrategy {
    private static final Logger logger = LoggerFactory.getLogger(AdventurerStrategy.class);

    @Override
    public Command selectAction(Character myself, Room currentRoom) {
        if (currentRoom.hasPotion()) {
            return createDrinkCommand(myself, currentRoom);
        }
        if (currentRoom.hasCloak() && myself.isVisible()) {
            return createCloakCommand(myself, currentRoom);
        }
        if (currentRoom.hasArmor()) {
            return createWearCommand(myself, currentRoom);
        }
        if (shouldFight(currentRoom)) {
            return createFightCommand(myself, currentRoom);
        }
        if (currentRoom.hasFood()) {
            return createEatCommand(myself, currentRoom);
        }
        if (shouldMove(currentRoom)) {
            return commandFactory.createMoveCommand(myself, currentRoom.getRandomNeighbor());
        }
        return commandFactory.createNullCommand();
    }

    private Command createFightCommand(Character myself, Room currentRoom) {
        Optional<Character> creature = currentRoom.getCreature();
        if (creature.isPresent()) {
            return commandFactory.createFightCommand(myself, creature.get());
        }
        return commandFactory.createNullCommand();
    }

    boolean shouldFight(Room currentRoom) {
        return currentRoom.hasLivingCreatures();
    }

    Command createEatCommand(Character myself, Room currentRoom) {
        Optional<Artifact> food = currentRoom.getFood();
        if (food.isPresent()) {
            return commandFactory.createEatCommand(myself, currentRoom, food.get());
        } else {
            logger.error("No food in room");
            return commandFactory.createNullCommand();
        }
    }

    Command createWearCommand(Character myself, Room currentRoom) {
        Optional<Artifact> armor = currentRoom.getArmor();
        if (armor.isPresent()) {
            return commandFactory.createWearCommand(myself, currentRoom, armor.get());
        }
        return commandFactory.createNullCommand();
    }

    private Command createCloakCommand(Character myself, Room currentRoom) {
        Optional<Artifact> cloak = currentRoom.getCloak();
        if (cloak.isPresent()) {
            return commandFactory.createCloakCommand(myself, currentRoom, cloak.get());
        }
        return commandFactory.createNullCommand();
    }

    Command createDrinkCommand(Character myself, Room currentRoom) {
        Optional<Artifact> potion = currentRoom.getPotion();
        if (potion.isPresent()) {
            return commandFactory.createDrinkCommand(myself, currentRoom, potion.get());
        }
        return commandFactory.createNullCommand();
    }

    // Returns every possible version of all commands for this character and room
    public List<List<Command>> getAllPossibleCommands(Character character, Room room) {
        List<List<Command>> possibleCommands = new ArrayList<>();
        possibleCommands.add(Collections.singletonList(commandFactory.createNullCommand()));

        if (room.hasLivingCreatures()) {
            List<Command> fightCommands = room.getLivingCreatures().stream()
                    .filter(creature -> creature != character)  // In case we have a Creature using this strategy
                    .map(creature -> commandFactory.createFightCommand(character, creature))
                    .toList();
            if (!fightCommands.isEmpty()) {
                possibleCommands.add(fightCommands);
            }

            List<Command> fightToTheDeathCommands = room.getLivingCreatures().stream()
                    .filter(creature -> creature != character)  // In case we have a Creature using this strategy
                    .map(creature -> commandFactory.createFightToTheDeathCommand(character, creature))
                    .toList();
            if (!fightToTheDeathCommands.isEmpty()) {
                possibleCommands.add(fightToTheDeathCommands);
            }
        }
        if (room.hasFood()) {
            List<Command> eatCommands = room.getAllFood().stream()
                    .map(food -> commandFactory.createEatCommand(character, room, food))
                    .toList();
            possibleCommands.add(eatCommands);
        }
        if (room.hasNeighbors()) {
            List<Command> moveCommands = room.getNeighbors().stream()
                    .map(randomRoom -> commandFactory.createMoveCommand(character, randomRoom))
                    .toList();
            possibleCommands.add(moveCommands);
        }
        if (room.hasArmor()) {
            List<Command> wearCommands = room.getAllArmor().stream()
                    .map(armor -> commandFactory.createWearCommand(character, room, armor))
                    .toList();
            possibleCommands.add(wearCommands);
        }
        if (room.hasCloak()) {
            List<Command> cloakCommands = room.getAllCloaks().stream()
                    .map(cloak -> commandFactory.createCloakCommand(character, room, cloak))
                    .toList();
            possibleCommands.add(cloakCommands);
        }
        if (room.hasPotion()) {
            List<Command> drinkCommands = room.getAllPotions().stream()
                    .map(potion -> commandFactory.createDrinkCommand(character, room, potion))
                    .toList();
            possibleCommands.add(drinkCommands);
        }
        if (character.isCloaked()) {
            List<Command> commands = List.of(commandFactory.createDecloakCommand(character, room));
            possibleCommands.add(commands);
        }
        return possibleCommands;
    }
}
