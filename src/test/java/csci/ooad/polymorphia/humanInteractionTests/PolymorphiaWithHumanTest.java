package csci.ooad.polymorphia.humanInteractionTests;

import csci.ooad.polymorphia.Maze;
import csci.ooad.polymorphia.Polymorphia;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.characters.CharacterFactory;

public class PolymorphiaWithHumanTest {

    public static void main(String[] args) {
        Maze maze = Maze.getNewBuilder(new CharacterFactory(), new ArtifactFactory())
                .createFullyConnectedRooms(12)
                .createAndAddFighters(5)
                .createAndAddCowards(1)
                .createAndAddGluttons(1)
                .createAndAddCreatures(6)
                .createAndAddDemons(5)
                .createAndAddFoodItems(15)
                .createAndAddArmor(3)
                .createAndAddCloaks(3)
                .createAndAddPotions(3)
                .addRandomStrategyAdventurer("Rando")
                .addHumanPlayer("Professor")
                .build();
        Polymorphia polymorphia = new Polymorphia(maze);
        polymorphia.play();
        System.exit(0);
    }
}
