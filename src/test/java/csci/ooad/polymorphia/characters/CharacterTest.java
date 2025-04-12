package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    CharacterFactory characterFactory = new CharacterFactory();
    ArtifactFactory artifactFactory = new ArtifactFactory();

    Character joe;

    @BeforeEach
    void setUp() {
        joe = characterFactory.createFighter("Joe", 5.0);
    }

    @Test
    void testToString() {
        System.out.println(joe);

        assertTrue(joe.toString().contains("Joe"));
    }

    @Test
    void isAlive() {
        assertTrue(joe.isAlive());
    }

    @Test
    void testSorting() {
        double mostHealth = 10.0;
        double mediumHealth = 5.0;
        double leastHealth = 3.0;

        List<Character> characters = new ArrayList<>(Arrays.asList(
                characterFactory.createFighter("Bill", mostHealth),
                characterFactory.createCreature("Ogre", mediumHealth),
                characterFactory.createFighter("Sheri", leastHealth)));

        Collections.sort(characters);

        System.out.println(characters);
    }

    @Test
    void testLoseHealthAndDeath() {
        joe.loseHealth(3.0);
        assertEquals(2.0, joe.getHealth());

        joe.loseHealth(2.0);
        assertFalse(joe.isAlive());
    }

    @Test
    void testFightingMandatoryLossOfHalfAPoint() {
        Creature ogre = new Creature("Ogre");
        joe.fight(ogre);
        System.out.println("After the fight, Joe's health is: " + joe.getHealth());

        // Joe should have lost 0.5 health and he started with a integer health value
        // of 5.0. After the fight he should have 4.5 health. Or 3.5, or 2.5, etc. depending
        // upon the outcome of the fight. So, we just check to make sure the health is x.5
        assertEquals(0.5, joe.getHealth() % 1);
    }

    @Test
    void testMovingRoomLossOfQuarterPoint() {
        Room currentRoom = new Room("currentRoom");
        Room newRoom = new Room("newRoom");
        currentRoom.addNeighbor(newRoom);
        joe.enterRoom(currentRoom);

        // Since nothing is in the current room with Joe, he should move to the new room
        joe.doAction();

        System.out.println("After moving rooms, Joe's health is: " + joe.getHealth());
        assertEquals(4.75, joe.getHealth());
        assertEquals(newRoom, joe.getCurrentLocation());
    }

    @Test
    void testMovingWithNoNeighbors() {
        Room room = new Room("room");
        Character adventurer = characterFactory.createFighter("Adventurer");
        room.add(adventurer);

        // Act -- no error occurs
        adventurer.doAction();
    }

    @Test
    void testEatingFood() {
        Room room = new Room("room");
        Character adventurer = characterFactory.createFighter("Adventurer");
        room.add(adventurer);
        Artifact popcorn = artifactFactory.createFood("popcorn");
        room.add(popcorn);

        adventurer.doAction();

        assertEquals(adventurer.getHealth(), CharacterFactory.DEFAULT_FIGHTER_INITIAL_HEALTH + popcorn.getHealthValue());
        assertFalse(room.hasFood());
    }

    @Test
    void testFighting() {
        Character adventurer = characterFactory.createFighter("Adventurer");
        Character creature = characterFactory.createCreature("Creature");

        double initialHealth = adventurer.getHealth();
        adventurer.fight(creature);

        assertNotEquals(initialHealth, adventurer.getHealth());
    }

    @Test
    void testCreatureDoesNotDoAction() {
        Character creature = characterFactory.createCreature("Creature");
        Room room = new Room("room");
        room.add(creature);
        creature.doAction();
    }

}