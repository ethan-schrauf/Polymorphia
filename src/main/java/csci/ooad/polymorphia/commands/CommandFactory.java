package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.characters.Character;

public class CommandFactory {

    public Command createMoveCommand(Character character, Room destinationRoom) {
        return new MoveCommand(character, destinationRoom);
    }

    public Command createNullCommand() {
        return new NullCommand();
    }

    public Command createFightCommand(Character character, Character foe) {
        return new FightCommand(character, foe);
    }

    public Command createEatCommand(Character character, Room room, Artifact food) {
        return new EatCommand(character, room, food);
    }

    public Command createWearCommand(Character character, Room room, Artifact armor) {
        return new WearCommand(character, room, armor);
    }

    public Command createDrinkCommand(Character character, Room room, Artifact potion) {
        return new DrinkCommand(character, room, potion);
    }

    public Command createCloakCommand(Character character, Room room, Artifact cloak) {
        return new CloakCommand(character, room, cloak);
    }

    public Command createFightAndUpgradeCommand(Character character, Character foe) {
        return new FightAndUpgradeCommand(character, foe);
    }

    public Command createFightToTheDeathCommand(Character character, Character foe) {
        return new FightToDeathCommand(character, foe);
    }

    public Command createDecloakCommand(Character character, Room room) {
        return new DecloakCommand(character, room);
    }
}
