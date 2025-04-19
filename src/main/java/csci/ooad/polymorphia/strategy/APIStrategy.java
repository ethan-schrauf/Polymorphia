package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.commands.Command;

import java.util.List;
import java.util.Scanner;

public class APIStrategy extends AdventurerStrategy{
    @Override
    public Command selectAction(Character character, Room room) {
        System.out.print("You (" + character + ") are in " + room + "\n\n");

        // This returns a list of lists of commands. Each sublist is a list of commands of the same type.
        List<List<Command>> allCommands = getAllPossibleCommands(character, room);

        printTopLevelChoices(allCommands);
        int topLevelChoice = getChoice(allCommands.size());
        List<Command> chosenCommands = allCommands.get(topLevelChoice);

        if (chosenCommands.size() == 1) {
            return chosenCommands.getFirst();
        }

        printSecondLevelChoices(chosenCommands);
        int secondLevelChoice = getChoice(chosenCommands.size());
        return chosenCommands.get(secondLevelChoice);
    }

    private static int getChoice(int numChoices) {
        while (true) {  // This exits the method if a valid option is selected
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print("Enter the number of your choice: ");
                int choiceNumber = Integer.parseInt(scanner.nextLine()) - 1;
                if (choiceNumber < 0 || choiceNumber >= numChoices) {
                    throw new NumberFormatException();
                }
                return choiceNumber;
            } catch (NumberFormatException e) {
                System.out.println("Invalid option selected");
            }
        }
    }

    private void printTopLevelChoices(List<List<Command>> allCommands) {
        System.out.println("What would you like to do? ");
        int currentChoiceNumber = 1;
        for (List<Command> sameTypeCommands : allCommands) {
            System.out.println("\t" + currentChoiceNumber + ": " + sameTypeCommands.getFirst().getName());
            currentChoiceNumber++;
        }
    }

    private void printSecondLevelChoices(List<Command> commandsOfChosenType) {
        System.out.println("\nWhat do you want to " + commandsOfChosenType.getFirst().getName() + "?");
        int currentChoiceNumber = 1;
        for (Command command : commandsOfChosenType) {
            System.out.println("\t" + currentChoiceNumber + ": " + command.getArtifactName());
            currentChoiceNumber++;
        }
    }
}
