package csci.ooad.polymorphia.humanInteractionTests;

import csci.ooad.polymorphia.Maze;
import csci.ooad.polymorphia.NoSuchRoomException;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.characters.CharacterFactory;
import csci.ooad.polymorphia.commands.Command;
import csci.ooad.polymorphia.strategy.HumanStrategy;

public class HumanInteractionTest {

    // For testing, as JUNIT does not handle user input
    public static void main(String[] args) throws NoSuchRoomException {
        CharacterFactory characterFactory = new CharacterFactory();
        ArtifactFactory artifactFactory = new ArtifactFactory();
        Character bilbo = characterFactory.createFighter("Bilbo");
        Maze maze = Maze.getNewBuilder(characterFactory, artifactFactory)
                .createRoom("Only Room")
                .add(bilbo)
                .add(characterFactory.createCreature("Ogre"))
                .createAndAddArmor(2)
                .createAndAddCloaks(2)
                .createAndAddFoodItems(2)
                .createAndAddPotions(2)
                .build();
        HumanStrategy strategy = new HumanStrategy();
        Command command = strategy.selectAction(bilbo, maze.getRoom("Only Room"));

        System.out.println("\nYou selected " + command);
    }
}
