package csci.ooad.polymorphia;

import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.artifacts.Artifact.ArtifactType;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.characters.CharacterFactory;

import java.util.*;


public class Maze {
    private final Random rand = new Random();

    private List<Room> rooms = new ArrayList<>();

    public Maze() {
    }

    public static Builder getNewBuilder() {
        return new Builder();
    }

    public static Builder getNewBuilder(CharacterFactory characterFactory, ArtifactFactory artifactFactory) {
        return new Builder(characterFactory, artifactFactory);
    }

    public int size() {
        return rooms.size();
    }

    @Override
    public String toString() {
        return String.join("\n\n", rooms.stream().map(Object::toString).toList());
    }

    public Boolean hasLivingCreatures() {
        return rooms.stream().anyMatch(Room::hasLivingCreatures);
    }

    public Boolean hasLivingAdventurers() {
        return rooms.stream().anyMatch(Room::hasLivingAdventurers);
    }

    private Room getRandomRoom() {
        return rooms.get(rand.nextInt(rooms.size()));
    }

    public List<Character> getCharacters() {
        List<Character> characters = new ArrayList<>();
        for (Room room : rooms) {
            characters.addAll(room.getCharacters());
        }
        return characters;
    }

    public List<Character> getLivingAdventurers() {
        List<Character> adventurers = new ArrayList<>();
        for (Room room : rooms) {
            adventurers.addAll(room.getLivingAdventurers());
        }
        return adventurers;
    }

    public List<Character> getLivingCreatures() {
        List<Character> creatures = new ArrayList<>();
        for (Room room : rooms) {
            creatures.addAll(room.getLivingCreatures());
        }
        return creatures;
    }

    public List<Character> getLivingCharacters() {
        List<Character> characters = new ArrayList<>();
        for (Room room : rooms) {
            characters.addAll(room.getLivingCharacters());
        }
        return characters;
    }

    public boolean hasDemon() {
        return rooms.stream().anyMatch(Room::hasDemon);
    }

    public Room getRoom(String roomName) throws NoSuchRoomException {
        Optional<Room> namedRoom = rooms.stream().filter(r -> r.getName().equals(roomName)).findFirst();
        if (namedRoom.isEmpty()) {
            throw new NoSuchRoomException(roomName);
        }
        return namedRoom.get();
    }

    private void addRoom(Room room) {
        if (!rooms.isEmpty()) {
            room.addNeighbor(getRandomRoom());
        }
        rooms.add(room);

    }

    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    public static class Builder {
        public static String[] ROOM_NAMES = new String[]{"Room of Horrors", "Dungeon", "Pit of Despair", "Sanctuary",
                "Den of Souls", "Map Room", "Fangorn Forest", "Rivendell", "Misty Mountains", "Mordor", "Shire",
                "Hobbiton", "Gondor", "Chamber of Secrets", "Room of Doom", "Room of Gloom"};

        final Maze maze = new Maze();

        private final CharacterFactory characterFactory;
        private final ArtifactFactory artifactFactory;

        Map<String, Room> roomMap = new HashMap<>();
        private Boolean distributeSequentially = false;
        private int currentRoomIndex = -1;  // This is incremented before any use

        Builder() {
            this(new CharacterFactory(), new ArtifactFactory());
        }

        public Builder(CharacterFactory characterFactory, ArtifactFactory artifactFactory) {
            this.characterFactory = characterFactory;
            this.artifactFactory = artifactFactory;
        }

        private static String[] createRoomNames(Integer numRooms) {
            // Room names must be unique, as the maze drawer uses them as keys to connect rooms
            String[] roomNames = new String[numRooms];
            //int roomNameIndex = (new Random()).nextInt(ROOM_NAMES.length);
            for (int i = 0; i < numRooms; i++) {
                roomNames[i] = ROOM_NAMES[i % ROOM_NAMES.length];
            }
            return roomNames;
        }

        private Room nextRoom() {
            if (distributeSequentially) {
                currentRoomIndex = (currentRoomIndex + 1) % maze.getRooms().size();
                return maze.getRooms().get(currentRoomIndex);
            }
            return maze.getRandomRoom();
        }

        public Builder createRoom(String name) {
            Room initialRoom = new Room(name);
            maze.addRoom(initialRoom);
            return this;
        }

        public Builder createGridOfRooms(int rows, int columns, String[][] roomNames) {
            Room[][] roomGrid = new Room[rows][columns];
            List<Room> rooms = new ArrayList<>();
            // Notice -- don't use i and j. Use row and column -- they are better
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Room newRoom = new Room(roomNames[row][column]);
                    roomGrid[row][column] = newRoom;
                    rooms.add(newRoom);
                }
            }
            maze.rooms = rooms;

            // Now connect the rooms
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Room currentRoom = roomGrid[row][column];
                    if (row > 0) {
                        currentRoom.connect(roomGrid[row - 1][column]);
                    }
                    if (column > 0) {
                        currentRoom.connect(roomGrid[row][column - 1]);
                    }
                }
            }
            return this;
        }

        public Builder create2x2Grid() {
            String[][] roomNames = new String[][]{{"Northwest", "Northeast"}, {"Southwest", "Southeast"}};
            return createGridOfRooms(2, 2, roomNames);
        }

        public Builder create3x3Grid() {
            String[][] roomNames = new String[][]{{"Northwest", "North", "Northeast"}, {"West", "Center", "East"}, {"Southwest", "South", "Southeast"}};
            return createGridOfRooms(3, 3, roomNames);
        }

        public Builder createFullyConnectedRooms(Integer numRooms) {
            String[] roomNames = createRoomNames(numRooms);
            return createFullyConnectedRooms(roomNames);
        }

        public Builder createFullyConnectedRooms(String... roomNames) {
            List<Room> rooms = new ArrayList<>();
            for (String roomName : roomNames) {
                Room currentRoom = new Room(roomName);
                roomMap.put(roomName, currentRoom);
                for (Room otherRoom : rooms) {
                    currentRoom.connect(otherRoom);
                }
                rooms.add(currentRoom);
            }
            maze.rooms = rooms;
            return this;
        }

        public Builder createConnectedRooms(Integer minConnections, Integer numRooms) {
            return createConnectedRooms(minConnections, createRoomNames(numRooms));
        }

        public Builder createConnectedRooms(Integer minConnections, String... roomNames) {
            List<Room> rooms = new ArrayList<>();
            for (String roomName : roomNames) {
                Room currentRoom = new Room(roomName);
                roomMap.put(roomName, currentRoom);
                rooms.add(currentRoom);
            }
            maze.rooms = rooms;
            Boolean oldDistributionSetting = this.distributeSequentially;
            distributeSequentially();
            int realMinimumConnections = Math.min(minConnections, Math.max(rooms.size() - 1, 1));
            for (Room room : rooms) {
                while (room.numberOfNeighbors() < realMinimumConnections) {
                    Room neighbor = nextRoom();
                    if (!room.equals(neighbor) && !room.hasNeighbor(neighbor)) {
                        room.connect(neighbor);
                    }
                }
            }
            this.distributeSequentially = oldDistributionSetting;
            return this;
        }

        public Builder add(Character character) {
            nextRoom().add(character);
            return this;
        }

        public Builder add(Artifact artifact) {
            nextRoom().add(artifact);
            return this;
        }

        public Builder createAndAddFighters(Integer numFighters) {
            addCharacters(characterFactory.createFighters(numFighters));
            return this;
        }

        public Builder createAndAddRandomPlayingAdventurers(Integer numAdventurers) {
            addCharacters(characterFactory.createRandomStrategyAdventurers(numAdventurers));
            return this;
        }

        public Builder createAndAddRandomPlayingCreatures(Integer numAdventurers) {
            addCharacters(characterFactory.createRandomStrategyCreatures(numAdventurers));
            return this;
        }

        public Builder createAndAddHumanAdventurers(Integer numAdventurers) {
            addCharacters(characterFactory.createHumanPlayers(numAdventurers));
            return this;
        }

        public Builder createAndAddGluttons(Integer numGluttons) {
            addCharacters(characterFactory.createGluttons(numGluttons));
            return this;
        }

        public Builder createAndAddCowards(Integer numCowards) {
            addCharacters(characterFactory.createCowards(numCowards));
            return this;
        }

        public Builder createAndAddCreatures(Integer numCreatures) {
            return addCharacters(characterFactory.createCreatures(numCreatures));
        }

        public Builder createAndAddDemons(Integer numDemons) {
            addCharacters(characterFactory.createDemons(numDemons));
            return this;
        }

        public Maze build() {
            return maze;
        }

        public Builder createAndAddFoodItems(Integer numItems) {
            return createAndAddArtifacts(ArtifactType.Food, numItems);
        }

        public Builder createAndAddArmor(Integer numItems) {
            return createAndAddArtifacts(ArtifactType.Armor, numItems);
        }

        public Builder createAndAddCloaks(Integer numItems) {
            return createAndAddArtifacts(ArtifactType.Cloak, numItems);
        }

        public Builder createAndAddPotions(Integer numItems) {
            return createAndAddArtifacts(ArtifactType.Potion, numItems);
        }

        public Builder createAndAddArtifacts(ArtifactType type, Integer numItems) {
            for (Artifact artifact : artifactFactory.createNumberOf(type, numItems)) {
                nextRoom().add(artifact);
            }
            return this;
        }

        public Builder addToRoom(String roomName, Character character) {
            roomMap.get(roomName).add(character);
            return this;
        }

        public Builder addToRoom(String roomName, Artifact artifact) {
            roomMap.get(roomName).add(artifact);
            return this;
        }

        public Builder distributeSequentially() {
            distributeSequentially = true;
            return this;
        }

        public Builder distributeRandomly() {
            distributeSequentially = false;
            return this;
        }

        public Builder addCharacters(List<Character> characters) {
            for (Character character : characters) {
                nextRoom().add(character);
            }
            return this;
        }

        public Builder addArtifacts(List<Artifact> artifacts) {
            for (Artifact foodItem : artifacts) {
                nextRoom().add(foodItem);
            }
            return this;
        }

        public Builder addHumanPlayer(String name) {
            nextRoom().add(characterFactory.createHumanPlayer(name));
            return this;
        }

        public Builder addRandomStrategyAdventurer(String name) {
            nextRoom().add(characterFactory.createRandomStrategyAdventurer(name));
            return this;
        }
    }
}
