package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.characters.ArmoredCharacter;
import csci.ooad.polymorphia.characters.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WearCommand extends ArtifactCommand {
    private static final Logger logger = LoggerFactory.getLogger(WearCommand.class);

    public WearCommand(Character character, Room room, Artifact armor) {
        super(character, room, armor);
    }

    @Override
    public String getName() {
        return "Wear";
    }

    @Override
    public boolean execute() {
        if (room.getAllArmor().contains(artifact)) {
            room.removeArtifact(artifact);
            Character armoredMe = new ArmoredCharacter(character, artifact);
            room.replace(character, armoredMe);
            logger.info(character.getName() + " put on: " + artifact);
            return true;
        }
        return false;
    }
}
