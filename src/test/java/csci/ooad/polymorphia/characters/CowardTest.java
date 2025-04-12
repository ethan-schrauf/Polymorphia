package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.Maze;
import csci.ooad.polymorphia.NoSuchRoomException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CowardTest {
    CharacterFactory characterFactory = new CharacterFactory();

    @Test
    void testRunning() throws NoSuchRoomException {
        // Arrange - put creature in room with two adventurers
        Double initialHealth = 5.0;
        Character coward = characterFactory.createCoward("Coward", initialHealth);
        Character dragon = characterFactory.createCreature("Dragon");
        Maze twoRoomMaze = Maze.getNewBuilder()
                .createFullyConnectedRooms("initial", "final")
                .addToRoom("initial", coward)
                .addToRoom("initial", dragon)
                .build();

        // Act - the coward should fight
        coward.doAction();

        // Assert – the coward ran to the other room and lost some health doing it
        // since there was a creature in the room.
        assertTrue(twoRoomMaze.getRoom("final").hasLivingAdventurers());
        assertTrue(coward.getHealth() < initialHealth);
    }

    @Test
    void testFighting() {
        // Arrange - put creature in room with two adventurers
        Double initialHealth = 5.0;
        Character coward = characterFactory.createCoward("Coward", initialHealth);
        Character satan = characterFactory.createDemon("Satan");
        Maze twoRoomMaze = Maze.getNewBuilder()
                .createFullyConnectedRooms("initial", "final")
                .addToRoom("initial", coward)
                .addToRoom("initial", satan)
                .build();

        // Act - the coward must fight a Demon
        coward.doAction();

        // Assert – the coward ran to the other room
        assertNotEquals(initialHealth, coward.getHealth());
    }
}
