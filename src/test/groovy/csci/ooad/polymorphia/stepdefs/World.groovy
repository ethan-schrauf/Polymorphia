package csci.ooad.polymorphia.stepdefs


import csci.ooad.polymorphia.Maze
import csci.ooad.polymorphia.Polymorphia
import csci.ooad.polymorphia.PolymorphiaEventType
import csci.ooad.polymorphia.Room
import csci.ooad.polymorphia.artifacts.ArtifactFactory
import csci.ooad.polymorphia.characters.Character
import csci.ooad.polymorphia.characters.CharacterFactory
import csci.ooad.polymorphia.observers.EventBusEnum
import csci.ooad.polymorphia.observers.TestObserver
import io.cucumber.java.After

class World {

    Polymorphia polymorphia
    CharacterFactory characterFactory
    ArtifactFactory artifactFactory
    Maze.Builder builder
    private Maze maze
    TestObserver polymorphiaObserver
    Map<String, Character> characters = new HashMap<>()
    Map<String, Double> charactersInitialHealth = new HashMap<>()
    Map<String, Room> rooms = new HashMap<>()

    World(CharacterFactory characterFactory, ArtifactFactory artifactFactory) {
        this.characterFactory = characterFactory
        this.artifactFactory = artifactFactory
        builder = Maze.getNewBuilder(characterFactory, artifactFactory)
    }

    @After
    void tearDown() {
        EventBusEnum.INSTANCE.detach(polymorphiaObserver)
    }

    Maze getMaze() {
        if (maze == null) {
            maze = builder.build()
        }
        return maze
    }

    void buildMazeIfNotBuilt() { getMaze() }

    boolean eventReceived(PolymorphiaEventType eventType) {
        return polymorphiaObserver.numberOfReceivedEvents(eventType) > 0
    }

    void allCharactersAreInRoom(String roomName) {
        characters.values().each { Character character ->
            builder.addToRoom(roomName, character)
        }
    }

    Character getCharacter(String name) {
        Optional<Character> character = getMaze().getCharacters().stream().filter(c -> c.getName().contains(name)).findAny()
        if (character.isPresent()) {
            return character.get()
        }
        print("I should not be here...")
        return null
    }

    Double getInitialHealth(String characterName) {
        return charactersInitialHealth[characterName]
    }

    void addCharacter(Character character) {
        characters.put(character.getName(), character)
        charactersInitialHealth.put(character.getName(), character.getHealth())
    }

    void addRoom(String name, Room room) {
        rooms.put(name, room)
    }

    void attachObserver(TestObserver testObserver) {
        EventBusEnum.INSTANCE.attach(testObserver)
        polymorphiaObserver = testObserver
    }
}