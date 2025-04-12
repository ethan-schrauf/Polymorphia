package csci.ooad.polymorphia.stepdefs

import csci.ooad.polymorphia.Room
import csci.ooad.polymorphia.artifacts.Artifact
import csci.ooad.polymorphia.artifacts.ArtifactFactory
import io.cucumber.java.en.And
import io.cucumber.java.en.Given

import static org.junit.jupiter.api.Assertions.*

class RoomStepDefs {
    World world
    ArtifactFactory artifactFactory

    RoomStepDefs(World aWorld, ArtifactFactory artifactFactory) {
        world = aWorld
        this.artifactFactory = artifactFactory
    }

    @Given(/^a room named "([^"]*)"$/)
    void createAndSaveRoom(String roomName) {
        world.addRoom(roomName, new Room(roomName))
    }

    @And("all characters are in room {string}")
    void allCharactersAreInRoom(String roomName) {
        world.allCharactersAreInRoom(roomName)
    }

    @And("room {string} has no food items")
    void roomHasFoodItems(String roomName) {
        Room room = world.getMaze().getRoom(roomName)
        assertFalse(room.hasFood())
    }

    @And(/^"([^"]*)" (is in|is not in) room "([^"]*)"$/)
    void isInRoom(String characterName, String test, String roomName) {
        Room room = world.maze.getRoom(roomName)
        if (test == 'is in') {
            assertEquals(roomName, world.getCharacter(characterName).getCurrentLocation().getName())
            assertTrue(room.hasCharacterWithName(characterName))
        } else {
            assertNotEquals(roomName, world.getCharacter(characterName).getCurrentLocation().getName())
            assertFalse(room.hasCharacterWithName(characterName))
        }
    }

    @And("armor {string} with strength {double} and moving cost {double} is placed in room {string}")
    void armorWithStrengthAndMovingCostIsPlacedInRoom(String armorName, double strength, double movingCost, String roomName) {
        Room room = world.getMaze().getRoom(roomName)
        Artifact armor = artifactFactory.createArmor(armorName, strength, movingCost)
        room.add(armor)
    }

    @And("cloak {string} is placed in room {string}")
    void cloakIsPlacedInRoom(String cloakName, String roomName) {
        Room room = world.getMaze().getRoom(roomName)
        Artifact cloak = artifactFactory.createCloak(cloakName)
        room.add(cloak)
    }

    @And(/^there is no (armor|cloak) in room "([^"]*)"$/)
    public void thereIsNoArmorInRoom(String artifactType, String roomName) {
        Room room = world.getMaze().getRoom(roomName)
        Artifact.ArtifactType type = switch (artifactType) {
            case 'cloak' -> Artifact.ArtifactType.Cloak
            default -> Artifact.ArtifactType.Armor
        }
        assertFalse(room.has(type))
    }

    @And("room {string} now contains an armored character")
    boolean roomNowContainsAnArmoredCharacter(String roomName) {
        Room room = world.getMaze().getRoom(roomName)
        return room.getCharacters().stream()
                .anyMatch { c -> c.toString().contains("armored") }
    }

    @And("room {string} now contains a cloaked character")
    boolean roomNowContainsACloakedCharacter(String roomName) {
        Room room = world.getMaze().getRoom(roomName)
        return room.getCharacters().stream()
                .anyMatch { c -> c.toString().contains("cloaked") }
    }
}
