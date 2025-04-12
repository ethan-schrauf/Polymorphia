package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.commands.Command;
import csci.ooad.polymorphia.commands.CommandFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloakedCharacterTest {
    CharacterFactory characterFactory = new CharacterFactory();
    ArtifactFactory artifactFactory = new ArtifactFactory();
    CommandFactory commandFactory = new CommandFactory();

    @Test
    void testCloakingAndDecloaking() {
        // Arrange
        Room room = new Room("Only Room");
        Character bilbo = characterFactory.createFighter("Bilbo");
        room.add(bilbo);
        Artifact cloak = artifactFactory.createCloak("Invisibility", 3);
        room.add(cloak);

        // Act
        bilbo.doAction();

        // Assert
        assertTrue(room.hasCharacterWithName("cloak"));
        assertFalse(room.hasCloak());

        // Act
        double bilboOriginalHealth = bilbo.getHealth();
        Character cloakedBilbo = room.getCharacters().getFirst();
        Character ogre = characterFactory.createCreature("Ogre");
        room.add(ogre);
        ogre.doAction();

        // Assert no fight has taken place because bilbo is invisible
        assertEquals(bilboOriginalHealth, cloakedBilbo.getHealth());

        // Act
        Command command = commandFactory.createDecloakCommand(cloakedBilbo, room);
        command.execute();

        // Assert
        assertFalse(room.hasCharacterWithName("cloak"));

        // Act
        ogre.doAction();

        // Assert a fight now took place because the Ogre can see Bilbo
        assertTrue(cloakedBilbo.getHealth() < bilboOriginalHealth);
    }

    @Test
    void testCloakWearingOut() {
        // Arrange
        Room room = new Room("Only Room");
        Character bilbo = characterFactory.createFighter("Bilbo");
        room.add(bilbo);
        Artifact cloak = artifactFactory.createCloak("Invisibility", 1);
        room.add(cloak);
        bilbo.doAction();
        Character cloakedBilbo = room.getCharacters().getFirst();

        Character ogre = characterFactory.createCreature("Ogre");
        room.add(ogre);

        // Act
        ogre.doAction();

        // Assert no fight has taken place because bilbo is invisible
        double bilboOriginalHealth = cloakedBilbo.getHealth();
        assertEquals(bilboOriginalHealth, cloakedBilbo.getHealth());
        // Bilbo should no longer be cloaked
        assertFalse(room.hasCharacterWithName("cloak"));

        // Act
        ogre.doAction();

        // Assert a fight now did take place because bilbo is once again visible
        assertTrue(cloakedBilbo.getHealth() < bilboOriginalHealth);

    }
}