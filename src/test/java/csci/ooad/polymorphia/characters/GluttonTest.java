package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.Maze;
import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GluttonTest {
    CharacterFactory characterFactory = new CharacterFactory();
    ArtifactFactory artifactFactory = new ArtifactFactory();

    @Test
    void testEating() {
        // Arrange
        double initialHealth = 3.0;
        Character glutton = characterFactory.createGlutton("Glutton", initialHealth);
        Artifact cake = artifactFactory.createFood("Cake");

        Maze.getNewBuilder()
                .createFullyConnectedRooms(1)
                .add(glutton)
                .createAndAddCreatures(1)
                .add(cake)
                .build();

        // Act - the glutton should not fight. It should eat
        glutton.doAction();

        // Assert – the glutton ate the cake and gained default health points (1.0)
        assertEquals(initialHealth + cake.getHealthValue(), glutton.getHealth());
    }

    @Test
    void testFighting() {
        // Arrange - put Demon in room with Glutton
        double initialHealth = 3.0;
        Character glutton = characterFactory.createGlutton("Glutton", initialHealth);
        Character satan = characterFactory.createDemon("Satan");
        Artifact steak = artifactFactory.createFood("Steak");

        Maze.getNewBuilder()
                .createFullyConnectedRooms("initial", "final")
                .addToRoom("initial", glutton)
                .addToRoom("initial", satan)
                .addToRoom("initial", steak)
                .build();

        // Act - the glutton will eat
        glutton.doAction();

        // Assert – the glutton ran to the other room
        double healthAfterEating = glutton.getHealth();
        assertEquals(initialHealth + steak.getHealthValue(), healthAfterEating);


        // Act - the demon fights the glutton
        satan.doAction();

        double healthAfterFighting = glutton.getHealth();
        assertTrue(healthAfterFighting < healthAfterEating);
    }

    @Test
    void testStrategyUpgrade() {
        // Arrange - put Demon in room with Glutton
        double initialHealth = 100.0;
        Character glutton = characterFactory.createGlutton("Glutton", initialHealth);
        Character ogre = characterFactory.createCreature("Ogre");
        Room room = new Room("onlyRoom");
        room.add(glutton);
        room.add(ogre);

        // Act - the glutton will fight
        while (ogre.isAlive() && glutton.isAlive()) {
            glutton.doAction();
        }

        assertTrue(glutton.isAlive());
        room.add(artifactFactory.createFood("Steak"));
        Character dragon = characterFactory.createCreature("Dragon");
        room.add(dragon);
        double dragonInitialHealth = dragon.getHealth();
        double gluttonInitialHealth = glutton.getHealth();
        glutton.doAction();

        assertTrue(glutton.isAlive());
        assertTrue(room.hasFood());
        assertTrue(dragon.getHealth() < dragonInitialHealth);
        assertTrue(glutton.getHealth() < gluttonInitialHealth);
    }
}
