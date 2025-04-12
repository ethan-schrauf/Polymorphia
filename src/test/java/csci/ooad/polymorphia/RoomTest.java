package csci.ooad.polymorphia;

import csci.ooad.polymorphia.artifacts.Artifact.ArtifactType;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.characters.CharacterFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    static private final CharacterFactory characterFactory = new CharacterFactory();
    static private final ArtifactFactory artifactFactory = new ArtifactFactory();

    @Test
    void getRandomNeighbor() {
        Room room = new Room("mainRoom");
        Room neighbor = new Room("neighbor");
        room.addNeighbor(neighbor);

        assertEquals(room.getRandomNeighbor(), neighbor);
    }

    @Test
    void testGetRandomNeighborOnRoomWithNoNeighbors() {
        Room room = new Room("onlyRoom");
        assertNull(room.getRandomNeighbor());
    }

    @Test
    void testToString() {
        Room room = new Room("onlyRoom");
        room.add(characterFactory.createFighter("Bilbo"));
        room.add(characterFactory.createCreature("Ogre"));

        System.out.println(room);

        assertTrue(room.toString().contains("onlyRoom"));
        assertTrue(room.toString().contains("Bilbo"));
        assertTrue(room.toString().contains("Ogre"));
    }

    @Test
    void testGetHealthiestAdventurer() {
        // Arrange
        double highestHealth = 5;
        double lowestHealth = 3;

        Room room = new Room("onlyRoom");
        Character bilbo = characterFactory.createFighter("Bilbo", highestHealth);
        room.add(bilbo);
        room.add(characterFactory.createFighter("Frodo", lowestHealth));
        Character troll = characterFactory.createCreature("Troll", highestHealth);
        room.add(troll);
        room.add(characterFactory.createCreature("Orc", lowestHealth));

        // Act
        Character fittestAdventurer = room.getHealthiestAdventurer().get();
        Character fittestCreature = room.getHealthiestCreature().get();

        // Assert
        assertEquals(bilbo, fittestAdventurer);
        assertEquals(troll, fittestCreature);
    }

    @Test
    void testGeneralArtifacts() {
        Room room = new Room("onlyRoom");
        room.add(artifactFactory.createFood("Burger"));
        assertTrue(room.has(ArtifactType.Food));
        assertTrue(room.hasFood());

        room.add(artifactFactory.createArmor("Gold-plated"));
        assertTrue(room.has(ArtifactType.Armor));
        assertTrue(room.hasArmor());
    }

}