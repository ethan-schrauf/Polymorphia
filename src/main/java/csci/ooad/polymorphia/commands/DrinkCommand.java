package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.characters.ReincarnatableCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DrinkCommand extends ArtifactCommand {
    private static final Logger logger = LoggerFactory.getLogger(DrinkCommand.class);

    public DrinkCommand(Character character, Room room, Artifact potion) {
        super(character, room, potion);
    }

    @Override
    public String getName() {
        return "Drink";
    }

    @Override
    public boolean execute() {
        if (room.getAllPotions().contains(artifact)) {
            room.removeArtifact(artifact);
            Character reincarnatableMe = new ReincarnatableCharacter(character, artifact);
            room.replace(character, reincarnatableMe);
            logger.info(character.getName() + " just drank the " + artifact);
            return true;
        }
        return false;
    }
}
