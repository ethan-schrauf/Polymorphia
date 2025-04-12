package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.characters.CloakedCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloakCommand extends ArtifactCommand {
    private static final Logger logger = LoggerFactory.getLogger(CloakCommand.class);

    public CloakCommand(Character character, Room room, Artifact cloak) {
        super(character, room, cloak);
    }

    @Override
    public String getName() {
        return "Cloak";
    }

    @Override
    public boolean execute() {
        if (room.getAllCloaks().contains(artifact)) {
            room.removeArtifact(artifact);
            Character cloakedMe = new CloakedCharacter(character, artifact);
            room.replace(character, cloakedMe);
            logger.info(character.getName() + " put on: " + artifact + " and is now invisible");
            return true;
        }
        return false;
    }
}
