package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.Maze;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FighterTest {
    private static final CharacterFactory characterFactory = new CharacterFactory();

    static final Double INITIAL_FIGHTER_HEATH = 8.0;
    static final Double INITIAL_CREATURE_HEATH = 8.0;

    @Test
    void testFighting() {
        // Arrange - put creature in room with two adventurers
        Double lowHealth = 2.0;
        Character strongMan = characterFactory.createFighter("StrongMan", INITIAL_FIGHTER_HEATH);
        Character weakFighter = characterFactory.createFighter("WeakFighter", lowHealth);
        Character creature = characterFactory.createCreature("Ogre", INITIAL_CREATURE_HEATH);
        Maze.getNewBuilder()
                .createRoom("only room")
                .add(strongMan)
                .add(weakFighter)
                .add(creature)
                .build();

        // Act - the weak fighter should fight
        weakFighter.doAction();

        // Assert – the fight did occur and changed the health of both combatants
        assertNotEquals(lowHealth, weakFighter.getHealth());
        assertNotEquals(INITIAL_CREATURE_HEATH, creature.getHealth());
    }
}
