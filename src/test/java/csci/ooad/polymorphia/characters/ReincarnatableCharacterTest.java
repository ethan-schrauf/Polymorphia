package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReincarnatableCharacterTest {
    CharacterFactory factory = new CharacterFactory();
    ArtifactFactory artifactFactory = new ArtifactFactory();

    @Test
    void testReincarnation() {
        // Arrange
        Room room = new Room("Only Room");
        Character bilbo = factory.createFighter("Bilbo");
        room.add(bilbo);
        Artifact potion = artifactFactory.createPotion("Elixir of Life", 4.5);
        room.add(potion);

        // Act
        bilbo.doAction();

        // Assert
        assertTrue(room.hasCharacterWithName("Reincarnatable"));
        assertFalse(room.hasPotion());

        // Act
        Character reincarnatableBilbo = room.getCharacters().getFirst();
        reincarnatableBilbo.loseHealth(reincarnatableBilbo.getHealth());

        // Assert
        Character rebornBilbo = room.getCharacters().getFirst();
        assertTrue(rebornBilbo.isAlive());
        assertEquals(rebornBilbo.getHealth(), potion.getHealthValue());
        assertTrue(rebornBilbo.getName().contains("Reborn"));
    }

    @Test
    void testMultipleReincarnation() {
        // Arrange
        Room room = new Room("Only Room");
        Character bilbo = factory.createFighter("Bilbo");
        room.add(bilbo);
        Artifact elixirOfLife = artifactFactory.createPotion("Elixir of Life", 4.5);
        Artifact potion = artifactFactory.createPotion("Potion of Reanimation", 3.1);
        room.add(elixirOfLife);
        room.add(potion);

        // Act - die and be reborn
        bilbo.doAction();

        // Assert
        assertTrue(room.hasCharacterWithName("Reincarnatable"));
        System.out.println(room);
        assertTrue(room.hasPotion());

        // Act
        Character reincarnatableBilbo = room.getCharacters().getFirst();
        reincarnatableBilbo.doAction();

        // Assert
        assertTrue(room.hasCharacterWithName("Reincarnatable"));
        System.out.println(room);
        assertFalse(room.hasPotion());

        // Act - die and be reborn
        Character doublyReincarnatableBilbo = room.getCharacters().getFirst();
        doublyReincarnatableBilbo.loseHealth(reincarnatableBilbo.getHealth());

        // Assert
        Character rebornBilbo = room.getCharacters().getFirst();
        assertTrue(rebornBilbo.isAlive());
        assertEquals(rebornBilbo.getHealth(), elixirOfLife.getHealthValue());
        assertTrue(rebornBilbo.getName().contains("Reborn"));

        // Act - die and Bilbo will NOT be reborn again, as Bilbo is no longer reincarnatable
        rebornBilbo.loseHealth(rebornBilbo.getHealth());

        // Assert
        assertFalse(rebornBilbo.isAlive());
        assertFalse(room.hasCharacterWithName("Bilbo"));
    }

    @Test
    void testCorrectNameForReincarnatableCloakedCharacter() {
        Room room = new Room("Only Room");
        Character bilbo = factory.createFighter("Bilbo");
        room.add(bilbo);
        Artifact invisibilityCloak = artifactFactory.createCloak("Invisibility");
        room.add(invisibilityCloak);

        bilbo.doAction();
        System.out.println(room);
        assertTrue(room.hasCharacterWithName("cloaked"));

        Artifact potion = artifactFactory.createPotion("Potion of Reanimation", 3.1);
        room.add(potion);

        bilbo = room.getCharacters().getFirst();
        bilbo.doAction();
        System.out.println(room);
        assertTrue(room.hasCharacterWithName("Reincarnatable"));
        assertTrue(room.hasCharacterWithName("cloaked"));
    }

    @Test
    void testCloakedReincarnatableCharacterGetsReincarnated() {
        Room room = new Room("Only Room");
        Character bilbo = factory.createFighter("Bilbo");
        room.add(bilbo);
        Artifact potion = artifactFactory.createPotion("Potion of Reanimation", 3.1);
        room.add(potion);

        bilbo.doAction();
        System.out.println(room);
        assertTrue(room.hasCharacterWithName("Reincarnatable"));

        Artifact invisibilityCloak = artifactFactory.createCloak("Invisibility");
        room.add(invisibilityCloak);

        Character reincarnatableBilbo = room.getCharacters().getFirst();
        reincarnatableBilbo.doAction();
        System.out.println(room);

        assertTrue(room.hasCharacterWithName("Reincarnatable"));
        assertTrue(room.hasCharacterWithName("cloaked"));

        Character cloakedReincarnatableBilbo = room.getCharacters().getFirst();
        cloakedReincarnatableBilbo.loseHealth(bilbo.getHealth());

        Character rebornBilbo = room.getCharacters().getFirst();
        assertTrue(room.hasCharacterWithName("Reborn"));
        assertEquals(potion.getHealthValue(), rebornBilbo.getHealth());
    }
}