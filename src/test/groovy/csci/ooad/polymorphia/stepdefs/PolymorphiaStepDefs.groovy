package csci.ooad.polymorphia.stepdefs


import csci.ooad.polymorphia.Polymorphia
import csci.ooad.polymorphia.PolymorphiaEventType
import csci.ooad.polymorphia.observers.TestObserver
import io.cucumber.java.Before
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue

class PolymorphiaStepDefs {

    World world


    PolymorphiaStepDefs(World aWorld) {
        world = aWorld
    }

    @Before
    void setUp() {
        world.attachObserver(new TestObserver())
    }

    @When("the game is played in the created maze")
    void iPlayTheGame() {
        world.polymorphia = new Polymorphia(world.getMaze())
        world.polymorphia.play()
    }

    @Then("all the adventurers or all of the creatures have died")
    void iShouldBeToldThatEitherAllTheAdventurersOrAllOfTheCreaturesHaveDied() {
        !world.polymorphia.hasLivingAdventurers() || !world.polymorphia.hasLivingCreatures()
    }

    @Then("the game should be over")
    void theGameShouldBeOver() {
        println(world.polymorphia)
        assertTrue(world.polymorphia.isOver())
    }

    @Then(/^a fight took place$/)
    void aFightTookPlace() {
        assertTrue(world.eventReceived(PolymorphiaEventType.FightOccurred))
    }

    @Then(/^a fight did not take place$/)
    void aFightDoesNotTakePlace() {
        assertFalse(world.eventReceived(PolymorphiaEventType.FightOccurred))
    }
}
