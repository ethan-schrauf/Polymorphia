package csci.ooad.polymorphia.stepdefs

import csci.ooad.polymorphia.characters.Character
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

import static org.junit.jupiter.api.Assertions.*

class CharacterStepDefs {
    World world

    CharacterStepDefs(World aWorld) {
        world = aWorld
    }

    @Given(/^a (Coward|Glutton|Fighter|Creature|Demon) named "([^"]*)"$/)
    Character createANamedCharacter(String characterType, String name) {
        String methodName = "create${characterType.capitalize()}"
        Character character = world.characterFactory."${methodName}"(name)
        world.addCharacter(character)
        return character
    }

    @Given(/^a (Coward|Glutton|Fighter|Creature|Demon) named "([^"]*)" in room "([^"]*)"$/)
    void createANamedCharacter(String characterType, String name, String roomName) {
        Character character = createANamedCharacter(characterType, name)
        world.getMaze().getRoom(roomName).add(character)
    }

    @Then(/^"([^"]*)" (lost|did not lose) some health$/)
    characterLostHealth(String characterName, String lostOrNot) {
        Character character = world.getCharacter(characterName)
        Double initialHealth = world.getInitialHealth(characterName)
        boolean characterLostHealth = character.getHealth() < initialHealth
        if (lostOrNot == "lost") {
            assertTrue(characterLostHealth)
        } else {
            assertFalse(characterLostHealth)
        }
    }

    @When("{string} executes his turn")
    void executeTurn(String characterName) {
        world.buildMazeIfNotBuilt()
        Character character = world.getCharacter(characterName)
        character.doAction()
    }

    @And("{string} gained some health")
    void gainedSomeHealth(String characterName) {
        Character character = world.getCharacter(characterName)
        assertTrue(character.getHealth() > world.getInitialHealth(characterName))
    }

    @And("{string} lost {double} health")
    public void lostHealth(String characterName, double healthLost) {
        Character character = world.getCharacter(characterName)
        assertEquals(world.getInitialHealth(characterName), character.getHealth() + healthLost)
    }
}
