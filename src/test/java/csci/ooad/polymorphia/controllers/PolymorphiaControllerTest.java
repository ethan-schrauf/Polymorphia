package csci.ooad.polymorphia.controllers;
import csci.ooad.polymorphia.server.PolymorphiaServerApplication;
import csci.ooad.polymorphia.server.controllers.PolymorphiaController;
import csci.ooad.polymorphia.server.controllers.PolymorphiaJsonAdaptor;
import csci.ooad.polymorphia.server.controllers.PolymorphiaParameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PolymorphiaServerApplication.class, PolymorphiaController.class})
class PolymorphiaControllerTest {
    static String DEFAULT_GAME_ID = "MyGame";

    PolymorphiaController polymorphiaController;

    @Autowired
    public PolymorphiaControllerTest(PolymorphiaController polymorphiaController) {
        this.polymorphiaController = polymorphiaController;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    void getGames() {
        PolymorphiaParameters params = new PolymorphiaParameters("GamesList", "Professor",
                2, 2, 2, 1, 1, 1, 1, 1, 1);
        PolymorphiaParameters params2 = new PolymorphiaParameters("GamesList2", "Tester",
                2, 2, 2, 1, 3, 1, 1, 6, 2);
        // create 2 games
        polymorphiaController.createGame(params);
        polymorphiaController.createGame(params2);

        ResponseEntity<?> response = polymorphiaController.getGames();
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        List<?> body = (List<?>) response.getBody();
        assertNotNull(body);
        assertTrue(body.contains("GamesList"));
        assertTrue(body.contains("GamesList2"));
    }

    @Test
    void createGame() {
        String playerName = "Professor";
        PolymorphiaParameters arcaneParameters = new PolymorphiaParameters("Test1", playerName,
                2, 2, 7, 1,
                2, 2, 2, 10, 2);

        ResponseEntity<?> response = polymorphiaController.createGame(arcaneParameters);
        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());

        PolymorphiaJsonAdaptor jsonAdaptor = (PolymorphiaJsonAdaptor) response.getBody();
        assert jsonAdaptor != null;
        assertEquals("Test1", jsonAdaptor.name);
        assertTrue(jsonAdaptor.livingAdventurers.contains(playerName));
        assertFalse(jsonAdaptor.rooms.isEmpty());
    }

    @Test
    void testGetGame() {
        String playerName = "Professor";
        PolymorphiaParameters arcaneParameters = new PolymorphiaParameters("test2", playerName,
                2, 2, 7, 1,
                2, 2, 2, 10, 2);
        polymorphiaController.createGame(arcaneParameters);

        ResponseEntity<?> response = polymorphiaController.getGame("test2");
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        PolymorphiaJsonAdaptor jsonAdaptor = (PolymorphiaJsonAdaptor) response.getBody();
        assert jsonAdaptor != null;
        assertEquals("test2", jsonAdaptor.name);
        assertTrue(jsonAdaptor.livingAdventurers.contains(playerName));
        assertFalse(jsonAdaptor.rooms.isEmpty());
        assertNotNull(jsonAdaptor.availableCommands);
    }

    @Test
    void playTurnWithNoHumanPlayer() {
        String gameId = "noHumanPlayerGameId";
        String playerName = "Professor";
        PolymorphiaParameters params = new PolymorphiaParameters(
                gameId, playerName,
                2, 2, 2, 0, 0, 0, 0, 1, 1
        );

        polymorphiaController.createGame(params);

        ResponseEntity<?> response = polymorphiaController.playTurn(gameId, "WAIT");

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        PolymorphiaJsonAdaptor json = (PolymorphiaJsonAdaptor) response.getBody();
        assert json != null;
        assertEquals(gameId, json.name);
        // make sure turn progressed
        assertTrue(json.turn > 0);
    }

    @Test
    void playTurnWithHumanPlayer() {
        String gameId = "withHuman";
        String playerName = "Professor";
        PolymorphiaParameters params = new PolymorphiaParameters(
                gameId, playerName,
                2, 2, 2, 1, 0, 0, 0, 1, 1
        );

        // Create the game
        polymorphiaController.createGame(params);

        // Play a turn with a command for the human player
        ResponseEntity<?> response = polymorphiaController.playTurn(gameId, "WAIT");

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        PolymorphiaJsonAdaptor json = (PolymorphiaJsonAdaptor) response.getBody();
        assert json != null;
        assertTrue(json.livingAdventurers.contains("Professor"));
    }

    @Test
    void createGameWithDuplicateNameFails() {
        String playerName = "Professor";
        String gameId = "DuplicateGame";

        PolymorphiaParameters params = new PolymorphiaParameters(gameId, playerName,
                1, 1, 1, 1, 1, 1, 1, 1, 1);

        ResponseEntity<?> response1 = polymorphiaController.createGame(params);
        assertEquals(HttpStatusCode.valueOf(201), response1.getStatusCode());

        // Duplicate
        ResponseEntity<?> response2 = polymorphiaController.createGame(params);
        assertEquals(HttpStatusCode.valueOf(405), response2.getStatusCode());
        assertEquals("Game name already exists", response2.getBody());
    }

    @Test
    void getNonExistentGameReturnsNotFound() {
        ResponseEntity<?> response = polymorphiaController.getGame("noSuchGame");
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals("Game not found!", response.getBody());
    }

    @Test
    void playTurnNonExistentGameProducesError() {
        ResponseEntity<?> response = polymorphiaController.playTurn("noSuchGame", "WAIT");
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals("Game not found!", response.getBody());
    }

    @Test
    void createdGameHasLivingCreaturesAndAdventurers() {
        String playerName = "Professor";
        String gameId = "CreatureTest";

        PolymorphiaParameters params = new PolymorphiaParameters(gameId, playerName,
                2, 2, 2, 1, 1, 1, 1, 1, 1);
        polymorphiaController.createGame(params);

        ResponseEntity<?> response = polymorphiaController.getGame(gameId);
        PolymorphiaJsonAdaptor json = (PolymorphiaJsonAdaptor) response.getBody();

        assertNotNull(json);
        assertFalse(json.livingAdventurers.isEmpty());
        assertFalse(json.livingCreatures.isEmpty());
    }

    @Test
    void playTurnAdvancesTurn() {
        String playerName = "Professor";
        String gameId = "TurnTest";

        PolymorphiaParameters params = new PolymorphiaParameters(gameId, playerName,
                2, 2, 2, 1, 1, 1, 1, 1, 1);
        polymorphiaController.createGame(params);

        // First turn
        ResponseEntity<?> beforeTurn = polymorphiaController.getGame(gameId);
        assertNotNull(beforeTurn.getBody());
        int initialTurn = ((PolymorphiaJsonAdaptor) beforeTurn.getBody()).turn;

        // Play turn
        ResponseEntity<?> afterTurn = polymorphiaController.playTurn(gameId, "WAIT");
        assertNotNull(afterTurn.getBody());
        int newTurn = ((PolymorphiaJsonAdaptor) afterTurn.getBody()).turn;

        assertTrue(newTurn > initialTurn);
    }

    @Test
    void getGamesWhenEmpty() {
        PolymorphiaController polymorphiaController = new PolymorphiaController();
        ResponseEntity<?> response = polymorphiaController.getGames();
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals("No games!", response.getBody());
    }
}