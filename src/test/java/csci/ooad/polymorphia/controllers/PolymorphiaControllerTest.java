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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        ResponseEntity<?> response = polymorphiaController.getGames();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        // TODO: Add more assertions
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
        // TODO: Add more assertions
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
        // TODO: Add more assertions
    }

    @Test
    void playTurnWithNoHumanPlayer() {
        // TODO: Implement this test
    }

    @Test
    void playTurnWithHumanPlayer() {
        // TODO: Implement this test
    }

}