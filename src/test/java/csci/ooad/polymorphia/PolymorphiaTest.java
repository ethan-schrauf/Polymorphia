package csci.ooad.polymorphia;

import csci.ooad.polymorphia.artifacts.Artifact.ArtifactType;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.characters.CharacterFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolymorphiaTest {
    static final String STARTING_ROOM_NAME = "Starting Room";
    static final int TOTAL_GAMES_TO_RUN_WHEN_TESTING_FAIR_PLAY = 10;

    private static final CharacterFactory characterFactory = new CharacterFactory();

    @Test
    void testOneRoom() {
        // Baby steps!
        Maze oneRoomMaze = Maze.getNewBuilder()
                .createRoom(STARTING_ROOM_NAME)
                .createAndAddFighters(1)
                .createAndAddCreatures(1)
                .build();
        Polymorphia game = new Polymorphia(oneRoomMaze);


        // Act
        game.play();

        // Assert
        assert game.isOver();
    }

    @Test
    public void test2x2Game() {
        // Arrange
        Maze twoByTwoMaze = Maze.getNewBuilder()
                .create2x2Grid()
                .createAndAddFighters(1)
                .createAndAddCreatures(1)
                .build();
        Polymorphia game = new Polymorphia(twoByTwoMaze);

        // Act
        game.play();

        // Assert
        assert game.isOver();
    }

    @Test
    public void test3x3Game() {
        // Arrange
        Maze threeByThreeMaze = Maze.getNewBuilder()
                .create3x3Grid()
                .createAndAddFighters(1)
                .createAndAddCreatures(1)
                .build();
        Polymorphia game = new Polymorphia(threeByThreeMaze);

        // Act
        game.play();

        // Assert
        assert game.isOver();
    }

    @Test
    void testFairPlay() {
        MazeCreator threeByThreeMazeCreator = () -> {
            return Maze.getNewBuilder()
                    .create3x3Grid()
                    .createAndAddFighters(2)
                    .createAndAddCreatures(5)
                    .createAndAddFoodItems(20)
                    .build();
        };
        testFairPlay(threeByThreeMazeCreator);
    }



    @Test
    void testIndividualRoomCreation() throws NoSuchRoomException {
        Character adventure = characterFactory.createFighter("Bilbo");
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
                .createAndAddFighters(1)
                .createAndAddGluttons(1)
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
    void testWithEverything() {
        Maze maze = Maze.getNewBuilder()
                .createFullyConnectedRooms(7)
                .createAndAddFighters(3)
                .createAndAddCowards(1)
                .createAndAddGluttons(1)
                .createAndAddCreatures(6)
                .createAndAddDemons(2)
                .createAndAddFoodItems(6)
                .createAndAddFoodItems(1)
                .build();

        Polymorphia polymorphia = new Polymorphia(maze);
        polymorphia.play();
    }

    @Test
    void testAlternativeBuilderMethods() {
        CharacterFactory characterFactory = new CharacterFactory();
        ArtifactFactory artifactFactory = new ArtifactFactory();
        Maze maze = Maze.getNewBuilder()
                .createFullyConnectedRooms(7)
                .addCharacters(characterFactory.createFighters(2))
                .addCharacters(characterFactory.createCowards(1))
                .addCharacters(characterFactory.createGluttons(1))
                .addCharacters(characterFactory.createCreatures(6))
                .addCharacters(characterFactory.createDemons(1))
                .addArtifacts(artifactFactory.createNumberOf(ArtifactType.Food, 6))
                .build();

        Polymorphia polymorphia = new Polymorphia(maze);
        polymorphia.play();
    }

    @Test
    void testFairPlayOfEverything() {
        MazeCreator mazeCreator = () -> {
            CharacterFactory characterFactory = new CharacterFactory();
            ArtifactFactory artifactFactory = new ArtifactFactory();
            return Maze.getNewBuilder()
                    .createFullyConnectedRooms(12)
                    .addCharacters(characterFactory.createFighters(5))
                    .addCharacters(characterFactory.createCowards(1))
                    .addCharacters(characterFactory.createGluttons(1))
                    .addCharacters(characterFactory.createCreatures(6))
                    .addCharacters(characterFactory.createDemons(5))
                    .addArtifacts(artifactFactory.createNumberOf(ArtifactType.Food, 15))
                    .createAndAddArmor(3)
                    .createAndAddCloaks(3)
                    .createAndAddPotions(3)
                    .addRandomStrategyAdventurer("Rando")
                    .build();
        };
        testFairPlay(mazeCreator);
    }

    @Test
    void testForHomeworkOutput() {
        Maze maze = Maze.getNewBuilder()
                .createFullyConnectedRooms(10)
                .createAndAddFighters(1)
                .createAndAddFighters(2)
                .createAndAddCowards(2)
                .createAndAddGluttons(1)
                .createAndAddCreatures(5)
                .createAndAddDemons(1)
                .createAndAddFoodItems(25)
                .build();

        Polymorphia polymorphia = new Polymorphia(maze);
        polymorphia.play();
    }

    void testFairPlay(MazeCreator mazeCreator) {
        int adventurerWins = 0;
        int creatureWins = 0;
        int numTies = 0;

        for (int i = 0; i < TOTAL_GAMES_TO_RUN_WHEN_TESTING_FAIR_PLAY; i++) {
            Polymorphia game = new Polymorphia(mazeCreator.create());
            game.play();
            if (game.getWinner() == null) {
                numTies++;
            } else if (game.getWinner().isCreature()) {
                creatureWins++;
            } else {
                adventurerWins++;
            }
        }

        System.out.println("Adventurers won " + adventurerWins + " and creatures won " + creatureWins);
        System.out.println("There were " + numTies + " games with no winners");

        long adventureWinPercentage = Math.round((double) adventurerWins / (double) TOTAL_GAMES_TO_RUN_WHEN_TESTING_FAIR_PLAY * 100);
        System.out.println("Adventures won " + adventureWinPercentage + "% of the games.");

        assertTrue(adventureWinPercentage > 10);
    }
}
