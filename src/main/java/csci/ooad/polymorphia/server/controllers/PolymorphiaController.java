package csci.ooad.polymorphia.server.controllers;
import csci.ooad.polymorphia.Maze;
import csci.ooad.polymorphia.Polymorphia;
import csci.ooad.polymorphia.characters.Adventurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PolymorphiaController {
    private static final Logger logger = LoggerFactory.getLogger(PolymorphiaController.class);
    private Map<String,Polymorphia> games;

    public PolymorphiaController() {
        games = new HashMap<>();
    }

    //returns all games
    @GetMapping("/api/games")
    public ResponseEntity<?> getGames() {
        if(games.isEmpty()){
            return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.NOT_FOUND);
        }
        else{
            List<Polymorphia> gamesList = new ArrayList<>(games.values());
//            List<PolymorphiaJsonAdaptor> jsonAdaptors = new ArrayList<>();
//            for(Polymorphia game : gamesList){
//                jsonAdaptors.add(new PolymorphiaJsonAdaptor(game.getName(),game));
//            }
            List<String> gameNames = new ArrayList<>();
            for(Polymorphia game : gamesList){
                gameNames.add(game.getName());
            }
            return new ResponseEntity<>(gameNames, HttpStatus.OK);
        }
    }
    //game based on id
    @GetMapping("/api/game/{gameId}")
    public ResponseEntity<?> getGame(@PathVariable(name ="gameId", required = false) String gameId) {
        if(!games.containsKey(gameId)){
            return new ResponseEntity<>("Game not found!", HttpStatus.NOT_FOUND);
        }
        else{
            Polymorphia game = games.get(gameId);
            PolymorphiaJsonAdaptor jsonAdaptor = new PolymorphiaJsonAdaptor(gameId,game);
            return new ResponseEntity<>(jsonAdaptor, HttpStatus.OK);
        }
    }

    //add game to list
    @PostMapping("/api/game/create")
    public ResponseEntity<?> createGame(@Validated @RequestBody PolymorphiaParameters params) {
        if(games.containsKey(params.name())){
            return new ResponseEntity<>("Game name already exists", HttpStatus.METHOD_NOT_ALLOWED);
        }
        else{
            Maze newMaze = Maze.getNewBuilder().createFullyConnectedRooms(params.numRooms())
                    .createAndAddRandomPlayingAdventurers(params.numAdventurers())
                    .createAndAddCreatures(params.numCreatures())
                    .createAndAddFighters(params.numKnights())
                    .createAndAddCowards(params.numCowards())
                    .createAndAddGluttons(params.numGluttons())
                    .createAndAddDemons(params.numDemons())
                    .createAndAddFoodItems(params.numFood())
                    .createAndAddArmor(params.numArmor())
//                    .addAPIPlayer(params.playerName())
                    .build();

            Polymorphia newGame = new Polymorphia(params.name(), newMaze);
            games.put(params.name(), newGame);
            PolymorphiaJsonAdaptor jsonAdaptor = new PolymorphiaJsonAdaptor(params.name(),newGame);
            return new ResponseEntity<>(jsonAdaptor, HttpStatus.CREATED);
        }

    }

    //send turn command to game
    @PutMapping("/api/game/{gameId}/playTurn/{command}")
    public ResponseEntity<?> playTurn(@PathVariable(name = "gameId") String gameId, @PathVariable(name = "command") String command) {
        if(games.get(gameId) == null){
            return new ResponseEntity<>("Game not found!", HttpStatus.NOT_FOUND);
        }
        else{
            Polymorphia game = games.get(gameId);
            PolymorphiaJsonAdaptor json = new PolymorphiaJsonAdaptor(game.getName(),game);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
    }
}
