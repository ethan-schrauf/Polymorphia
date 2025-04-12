package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.artifacts.Armor;
import csci.ooad.polymorphia.artifacts.Cloak;
import csci.ooad.polymorphia.strategy.*;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class CharacterFactory {
    public static String[] RANDOM_NAMES = new String[]{"Rando", "Loose Cannon", "Chaos", "Bozo", "Unpredictable", "Nuts", "Crazy"};
    public static String[] FIGHTER_NAMES = new String[]{"Sir Lancelot", "Lady Brienne", "King Arthur", "Sir Jamey", "Aragorn", "Isildur"};
    public static String[] COWARD_NAMES = new String[]{"Sir Robin", "Sir Scaredy Cat", "Lady Faints-a-lot", "Lady Runaway", "Sir Chicken", "Lady Hides-a-lot"};
    public static String[] GLUTTON_NAMES = new String[]{"Sir Eats-a-lot", "Sir Gobbles", "Lady Munches", "Lady Snacks", "Sir Nibbles", "Lady Noshes"};
    public static String[] CREATURE_NAMES = new String[]{"Dragon", "Ogre", "Orc", "Shelob", "Troll", "Evil Wizard"};
    public static String[] DEMON_NAMES = new String[]{"Satan", "Beelzebub", "Devil", "Incubus", "Lucifer", "Succubus"};
    public static String[] HUMAN_NAMES = new String[]{"Alice", "Bob", "Chris", "Derek", "Elaine", "Frank"};

    public static final Double DEFAULT_FIGHTER_INITIAL_HEALTH = 8.0;
    public static final Double DEFAULT_COWARD_INITIAL_HEALTH = 3.0;
    public static final Double DEFAULT_GLUTTON_INITIAL_HEALTH = 3.0;
    public static final Double DEFAULT_CREATURE_INITIAL_HEALTH = 3.0;
    public static final Double MIN_INITIAL_HEALTH = 5.0;
    public static final Double MAX_INITIAL_HEALTH = 7.0;

    private static final Random random = new Random();

    private static double getRandomInitialHealth() {
        return random.nextDouble(MIN_INITIAL_HEALTH, MAX_INITIAL_HEALTH);
    }
    public Character createFighter(String name) {
        return createFighter(name, DEFAULT_FIGHTER_INITIAL_HEALTH);
    }

    public Character createFighter(String name, double initialHealth) {
        Character fighter = new Adventurer(name, initialHealth);
        fighter.setPlayStrategy(new FighterStrategy());
        return fighter;
    }

    public Character createCoward(String name) {
        return createCoward(name, DEFAULT_COWARD_INITIAL_HEALTH);
    }

    public Character createCoward(String name, double initialHealth) {
        Character coward = new Adventurer(name, initialHealth);
        coward.setPlayStrategy(new CowardStrategy());
        return coward;
    }

    public Character createGlutton(String name) {
        return createGlutton(name, DEFAULT_GLUTTON_INITIAL_HEALTH);
    }

    public Character createGlutton(String name, double initialHealth) {
        Character glutton = new Adventurer(name, initialHealth);
        glutton.setPlayStrategy(new GluttonStrategy());
        return glutton;
    }

    public Character createCreature(String name) {
        return createCreature(name, DEFAULT_CREATURE_INITIAL_HEALTH);
    }

    public Character createCreature(String name, double initialHealth) {
        return new Creature(name, initialHealth);
    }

    public Character createDemon(String name) {
        return new Demon(name);
    }

    public List<Character> createCharacters(Integer numToCreate, String[] names, CharacterCreator characterCreator) {
        return IntStream.range(0, numToCreate)
                .mapToObj((i) -> names[i % names.length])
                .map(characterCreator::create)
                .toList();
    }

    public List<Character> createFighters(int numToCreate) {
        return createCharacters(numToCreate, FIGHTER_NAMES, this::createFighter);
    }

    public List<Character> createRandomStrategyAdventurers(int numToCreate) {
        return createCharacters(numToCreate, RANDOM_NAMES, this::createRandomStrategyAdventurer);
    }

    public List<Character> createRandomStrategyCreatures(Integer numToCreate) {
        return createCharacters(numToCreate, CREATURE_NAMES, this::createRandomStrategyCreature);
    }

    public List<Character> createCowards(Integer numToCreate) {
        return createCharacters(numToCreate, COWARD_NAMES, this::createCoward);
    }

    public List<Character> createGluttons(int numToCreate) {
        return createCharacters(numToCreate, GLUTTON_NAMES, this::createGlutton);
    }

    public List<Character> createCreatures(int numToCreate) {
        // These next two are just alternatives way to do this, but the last is best as it is the most abstract.
        // return createCharacters(numToCreate, CREATURE_NAMES, (name) -> new Creature(name));
        //  return createCharacters(numToCreate, CREATURE_NAMES, Creature::new);
        return createCharacters(numToCreate, CREATURE_NAMES, this::createCreature);
    }

    public List<Character> createDemons(int numToCreate) {
        return createCharacters(numToCreate, DEMON_NAMES, this::createDemon);
    }

    public List<Character> createHumanPlayers(int numToCreate) {
        return createCharacters(numToCreate, HUMAN_NAMES, this::createHumanPlayer);
    }

    public Character createArmoredCharacter(Character character, Armor armor) {
        return new ArmoredCharacter(character, armor);
    }

    public Character createCloakedCharacter(Character character, Cloak cloak) {
        return new CloakedCharacter(character, cloak);
    }

    public Character createRandomStrategyAdventurer(String name) {
        Character rando = new Adventurer(name, getRandomInitialHealth());
        rando.setPlayStrategy(new RandomStrategy());
        return rando;
    }

    public Character createRandomStrategyCreature(String name) {
        Character rando = new Creature(name, getRandomInitialHealth());
        rando.setPlayStrategy(new RandomStrategy());
        return rando;
    }

    public Character createHumanPlayer(String name) {
        Character rando = new Adventurer(name, getRandomInitialHealth());
        rando.setPlayStrategy(new HumanStrategy());
        return rando;
    }

    public Character createCharacter(String name, double initialHealthValue, PlayStrategy playStrategy) {
        Character character = new Adventurer(name, initialHealthValue);
        character.setPlayStrategy(playStrategy);
        return character;
    }
}
