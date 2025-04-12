package csci.ooad.polymorphia.stepdefs

import csci.ooad.polymorphia.artifacts.ArtifactFactory
import io.cucumber.java.en.And
import io.cucumber.java.en.Given

class MazeStepDefs {
    static Map<String, String> featureFileAttributesToBuilderMethods = [
            "number of rooms"             : "createFullyConnectedRooms",
            "number of fighters"          : "createAndAddFighters",
            "number of gluttons"          : "createAndAddGluttons",
            "number of cowards"           : "createAndAddCowards",
            "number of creatures"         : "createAndAddCreatures",
            "number of food items"        : "createAndAddFoodItems",
            "number of demons"            : "createAndAddArmor",
            "number of armored suits"     : "createAndAddDemons",
            "number of cloaks"            : "createAndAddCloaks",
            "number of potions"           : "createAndAddPotions",
            "number of random adventurers": "createAndAddRandomPlayingAdventurers",
            "number of random creatures"  : "createAndAddRandomPlayingCreatures",
            "number of human adventurers" : "createAndAddHumanAdventurers",
    ]

    World world
    ArtifactFactory artifactFactory

    MazeStepDefs(World aWorld, ArtifactFactory artifactFactory) {
        world = aWorld
        this.artifactFactory = artifactFactory
    }

    @Given("a maze with the following attributes:")
    void iHaveAGameWithTheFollowingAttributes(Map<String, Integer> gameAttributes) {
        gameAttributes.each { key, value ->
            if (featureFileAttributesToBuilderMethods.containsKey(key)) {
                world.builder."${featureFileAttributesToBuilderMethods[key]}"(value)
            }
        }
    }

    @Given(/^a maze with the following rooms?:$/)
    void aMazeWithTheFollowingRooms(List<String> roomNames) {
        world.builder.createFullyConnectedRooms(roomNames.toArray(new String[0]))
    }

    @And("food {string} is placed in room {string}")
    void foodIsPlacedInRoom(String foodName, String roomName) {
        world.builder.addToRoom(roomName, artifactFactory.createFood(foodName))
    }

    @And("armor {string} is placed in room {string}")
    void armorIsPlacedInRoom(String foodName, String roomName) {
        world.builder.addToRoom(roomName, artifactFactory.createArmor(foodName))
    }

}
