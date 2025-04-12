package csci.ooad.polymorphia.humanInteractionTests;

import csci.ooad.polymorphia.Maze;
import csci.ooad.polymorphia.Polymorphia;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.characters.CharacterFactory;
import csci.ooad.polymorphia.observers.AudibleObserver;

public class PolymorphiaWithTwoHumansTest {

    public static void main(String[] args) {
        Maze maze = Maze.getNewBuilder(new CharacterFactory(), new ArtifactFactory())
                .createFullyConnectedRooms(12)
                .createAndAddRandomPlayingAdventurers(4)
                .createAndAddCreatures(4)
                .createAndAddRandomPlayingCreatures(4)
                .createAndAddDemons(5)
                .createAndAddFoodItems(15)
                .createAndAddArmor(3)
                .createAndAddCloaks(3)
                .createAndAddPotions(3)
                .addHumanPlayer("Professor")
                .addHumanPlayer("Maryanne")
                .build();
        Polymorphia polymorphia = new Polymorphia(maze);

        AudibleObserver audibleObserver = new AudibleObserver();
        polymorphia.attach(audibleObserver);

        polymorphia.play();
        System.exit(0);
    }
}
