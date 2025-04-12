package csci.ooad.polymorphia.commands;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.characters.Character;

public abstract class ArtifactCommand extends Command {
    protected final Room room;
    protected final Artifact artifact;

    public ArtifactCommand(Character character, Room room) {
        this(character, room, null);
    }

    public ArtifactCommand(Character character, Room room, Artifact artifact) {
        super(character);
        this.room = room;
        this.artifact = artifact;
    }

    @Override
    public String getArtifactName() {
        return artifact.getName();
    }

    @Override
    public String toString() {
        return super.toString() + ": " + artifact;
    }
}
