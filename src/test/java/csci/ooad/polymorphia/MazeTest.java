package csci.ooad.polymorphia;

import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.characters.CharacterFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    private static final Logger logger = LoggerFactory.getLogger(MazeTest.class);

    CharacterFactory characterFactory = new CharacterFactory();

    @Test
    void test2x2GridCreation() {
        Maze twoByTwoMaze = Maze.getNewBuilder()
                .create2x2Grid()
                .createAndAddFighters(1)
                .createAndAddCreatures(1)
                .createAndAddFoodItems(1)
                .build();
        assertEquals(4, twoByTwoMaze.size());
    }

    @Test
    void testHasLivingAdventurers() {
        Maze maze = Maze.getNewBuilder()
                .create2x2Grid()
                .createAndAddFighters(2)
                .build();

        assertTrue(maze.hasLivingAdventurers());
        assertEquals(maze.getLivingAdventurers().size(), 2);
    }

    @Test
    void testHasLivingCreatures() {
        Maze maze = Maze.getNewBuilder()
                .create2x2Grid()
                .createAndAddCreatures(2)
                .build();

        assertTrue(maze.hasLivingCreatures());
        assertEquals(maze.getLivingCreatures().size(), 2);
    }

    @Test
    void testToString() {
        Maze maze = Maze.getNewBuilder()
                .createRoom("initial")
                .add(characterFactory.createFighter("Bilbo"))
                .add(characterFactory.createCreature("Ogre"))
                .build();

        String mazeString = maze.toString();
        logger.info(mazeString);

        assertTrue(mazeString.contains("initial"));   // Hard-coded room name for 2x2 grid
        assertTrue(mazeString.contains("Bilbo"));
        assertTrue(mazeString.contains("Ogre"));
    }

    @Test
    void testIndividualRoomCreation() throws NoSuchRoomException {
        Character adventure = characterFactory.createFighter("Bill");
        Character creature = characterFactory.createCreature("Ogre");
        Maze twoRoomMaze = Maze.getNewBuilder()
                .createFullyConnectedRooms("initial", "final")
                .addToRoom("initial", adventure)
                .addToRoom("final", creature)
                .build();

        assertEquals(2, twoRoomMaze.size());
        assertTrue(twoRoomMaze.getRoom("initial").hasLivingAdventurers());
        assertTrue(twoRoomMaze.getRoom("final").hasLivingCreatures());
    }


    @Test
    void testUseOfFactories() throws NoSuchRoomException {
        Maze maze = Maze.getNewBuilder(new CharacterFactory(), new ArtifactFactory())
                .createRoom("initial")
                .createAndAddFighters(2)
                .createAndAddCreatures(1)
                .createAndAddDemons(1)
                .createAndAddFoodItems(1)
                .build();

        assertTrue(maze.hasLivingAdventurers());
        assertTrue(maze.hasLivingCreatures());
        assertTrue(maze.hasDemon());
        assertTrue(maze.getRoom("initial").hasFood());
    }

    @Test
    void testSequentialDistribution() {
        Maze maze = Maze.getNewBuilder()
                .createFullyConnectedRooms(4)
                .distributeSequentially()
                .createAndAddFoodItems(4)
                .build();


        // Since we added four food items into a four-room maze, with sequential distribution, each room should have some food.
        for (Room room : maze.getRooms()) {
            assertTrue(room.hasFood());
        }
    }

    @Test
    void testRandomDistribution() {
        Maze maze = Maze.getNewBuilder()
                .createFullyConnectedRooms(10)
                .distributeRandomly()
                .createAndAddFoodItems(10)
                .build();


        // Since we added four food items into a four-room maze, with random distribution, it's likely (not assured)
        // that one room will not have any food in it.
        assertTrue(maze.getRooms().stream().anyMatch(r -> !r.hasFood()));
    }

    @Test
    void testRoomsWithNConnections() {
        int minimumNumberOfNeighbors = 2;
        Maze maze = Maze.getNewBuilder()
                .createConnectedRooms(2, 5)
                .distributeSequentially()
                .createAndAddFoodItems(5)
                .build();

        for (Room room : maze.getRooms()) {
            assertTrue(room.numberOfNeighbors() >= minimumNumberOfNeighbors);
        }
    }

    @Test
    void testCreationViaNumber() {
        Maze maze = Maze.getNewBuilder()
                .createConnectedRooms(3, 4)
                .createAndAddFighters(4)
                .createAndAddCreatures(4)
                .createAndAddFoodItems(4)
                .build();

        assertEquals(4, maze.getRooms().size());
    }

    @Test
    void testNoSuchRoom() {
        Maze maze = Maze.getNewBuilder()
                .createConnectedRooms(3, 4)
                .build();

        assertThrows(NoSuchRoomException.class, () -> maze.getRoom("nonexistent"));
    }

    @Test
    void testRoomsWithArmor() {
        Maze maze = Maze.getNewBuilder()
                .createFullyConnectedRooms("initial", "final")
                .distributeSequentially()
                .createAndAddArmor(2)
                .build();

        for (Room room : maze.getRooms()) {
            assertTrue(room.hasArmor());
        }

        System.out.println(maze);
    }

    @Test
    void testMazePrinting() {
        Maze maze = Maze.getNewBuilder()
                .createFullyConnectedRooms("initial", "final")
                .distributeSequentially()
                .createAndAddFighters(4)
                .createAndAddCreatures(4)
                .createAndAddDemons(4)
                .createAndAddFoodItems(4)
                .createAndAddArmor(2)
                .build();

        System.out.println(maze);
    }

}