package csci.ooad.polymorphia;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.observers.EventBusEnum;
import csci.ooad.polymorphia.observers.IPolymorphiaObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Polymorphia {
    static final String DEFAULT_NAME = "Polymorphia";
    private static final Logger logger = LoggerFactory.getLogger(Polymorphia.class);

    private final String name;
    private final Maze maze;
    private Integer turnCount = 0;
    private final Random rand = new Random();

    public Polymorphia(Maze maze) {
        this(DEFAULT_NAME, maze);
    }

    public Polymorphia(String name, Maze maze) {
        this.maze = maze;
        this.name = name;
    }
    public Maze getMaze(){
        return maze;
    }

    public String getName() {
        return name;
    }

    private List<String> shortStatus() {
        return List.of(
                "After turn " + turnCount,
                "there are " + (getLivingAdventurers().size()) + " living adventurers",
                "there are " + (getLivingCreatures().size()) + " living creatures"
        );
    }

    private List<String> status() {
        return List.of(
                "Polymorphia " + getName() + " after turn " + turnCount,
                "Live adventurers(" + (getLivingAdventurers().size()) + "): " +
                        String.join(", ", getNamesOfLivingAdventurers()),
                "Live creatures(" + getLivingCreatures().size() + "): " +
                        String.join(", ", getNamesOfLivingCreatures())
        );
    }

    public List<String> getNamesOfLivingAdventurers() {
        return getLivingAdventurers().stream().map(Character::getName).toList();
    }

    public List<String> getNamesOfLivingCreatures() {
        return getLivingCreatures().stream().map(Character::getName).toList();
    }

    List<Character> getLivingAdventurers() {
        return getLivingCharacters().stream().filter(Character::isAdventurer).toList();
    }

    List<Character> getLivingCreatures() {
        return getLivingCharacters().stream().filter(Character::isCreature).toList();
    }

    @Override
    public String toString() {
        return "Polymorphia MAZE: turn " + turnCount + "\n" + maze.toString();
    }

    // Game is over when all creatures are killed
    // or all adventurers are killed
    public Boolean isOver() {
        return !hasLivingCreatures() || !hasLivingAdventurers();
    }

    public Boolean hasLivingCreatures() {
        return maze.hasLivingCreatures();
    }

    public Boolean hasLivingAdventurers() {
        return maze.hasLivingAdventurers();
    }

    public void playTurn() {
        turnCount += 1;

        // Process all the characters in random order
        List<Character> characters = getLivingCharacters();
        while (!characters.isEmpty()) {
            int index = rand.nextInt(characters.size());
            Character currentCharacter = characters.get(index);
            if (!isOver() && currentCharacter.isAlive()) {
                characters.get(index).doAction();
            }
            characters.remove(index);
        }
    }

    public List<Character> getLivingCharacters() {
        return maze.getLivingCharacters();
    }

    public void play() {
        logger.info("Starting play...");
        while (!isOver()) {
            logger.info(this.toString());
            playTurn();
            EventBusEnum.INSTANCE.broadcast(PolymorphiaEventType.TurnEnded, shortStatus());
        }
        announceResults();
    }

    private void announceResults() {
        logger.info("The game ended after {} turns.", turnCount);
        logger.info(generateResults());
    }

    private String generateResults() {
        if (hasLivingAdventurers()) {
            return "The adventurers won! Left standing are:\n" + getAdventurerNames() + "\n";
        }
        if (hasLivingCreatures()) {
            return "The creatures won! Left standing are:\n" + getCreatureNames() + "\n";
        }
        return "No team won! Everyone died!\n";
    }

    String getAdventurerNames() {
        return String.join("\n", getLivingCharacters().stream().map(Object::toString).toList());
    }

    String getCreatureNames() {
        return String.join("\n", getAliveCreatures().stream().map(Object::toString).toList());
    }

    public List<Character> getAliveCreatures() {
        return maze.getLivingCreatures();
    }

    public Character getWinner() {
        if (!isOver() || !hasLivingCharacters()) {
            // No one has won yet or no one won -- all died
            return null;
        }
        return getLivingCharacters().getFirst();
    }

    private boolean hasLivingCharacters() {
        return !getLivingCharacters().isEmpty();
    }

    public void attach(IPolymorphiaObserver observer) {
        EventBusEnum.INSTANCE.attach(observer);
    }

    public void detach(IPolymorphiaObserver observer) {
        EventBusEnum.INSTANCE.detach(observer);
    }

    public int getTurnNumber() {
        return turnCount;
    }
}