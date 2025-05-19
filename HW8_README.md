# OOAD Homework Standard Intro / Preamble

    Team Members: Ethan Schrauf & Ragan Lee
    Java Version: JDK 23
    Additional Comments, Assumptions, Required Written Comment (not usually required):

For all code submissions, the following will be required:

## Grading Rubric

### Deductions

* Meaningful names for everything: variables, methods, classes, interfaces, etc. (1% off for each bad name, up to 10%
  total)
* No "magic" numbers or strings (1% off for each one, up to 10%)
* 1% deduction for each missing required addition to the README.md (game outputs, screenshots, diagrams)

### Method Construction Possible Deductions (max is listed under Required Capabilities)

Methods should:

* be "short" -- with very few exceptions all methods should fit on a screen using a readable font. Use functional
  decomposition to break up big methods.
  well named (duh).
* be properly denoted as instance methods vs. static methods (static methods can’t use the this keyword).
* be of limited complexity (level of indentation due to control structures).
* not have comments that could be turned into just as readable code.

### Variables should be:

* of the “right” type. The most common mistake is creating a “constant” (using the final keyword) that is an instance
  variable. These should always be static (or class) variables.
* well named (duh).

## Github Guidelines

Since this homework is a team assignment, only one repository submission is required per team. Hence, do the following:

* Only one team member submits the repository link in Canvas.
* Please do not make the repositories public since this violates the Honor Code.
* Please note that repositories not established using the GitHub Classroom link will not be considered valid for
  submission.
* Please contact the class staff if you encounter technical difficulties or have questions about the setup process.

## Overall Project Guidelines

Assignments will be accepted late for one day only with a penalty of 10%. After one day, assignments will be given a 0
and will not be graded. Use office hours, e-mail, or Piazza to reach the class staff regarding homework questions or if
you have issues completing the assignment.

# OOAD Homework 8:

## Using the Command Pattern and Implementing Random and Human Strategies (40 points)

## Introduction

In this assignment, you are going to refactor the Strategy classes to separate
the concerns. Currently, the Strategy classes decide what action to take and also
take that action. You're going to separate those two things so that the
Strategy classes only decide which action to take and return a Command object,
which will execute that action when requested. You'll use
the Command Pattern and the Simple Factory Pattern to implement this.

Here's an example of what you need to do.
Given this Strategy class:

```java
abstract public class AdventurerStrategy extends PlayStrategy {

    public void doAction(Character character, Room room) {
        if (currentRoom.hasPotion()) {
            drinkPotion(character, room);
            return;
        }
```

You job is to refactor it into something like this:

```java
abstract public class AdventurerStrategy extends PlayStrategy {

    public Command selectAction(Character character, Room room) {
        if (room.hasPotion()) {
            Artifact potion = room.getPotion();
            return commandFactory.createDrinkCommand(character, room, potion);
        }
```

Then, in the Character class' _doAction()_ method, you'll have to run the command
that is returned from the Strategy's _selectAction()_ method.

Review the Command Pattern to implement this.

## Random Strategy

Implement a random strategy that selects a random
command from all the valid commands for the room.

Add methods to the CharacterFactory and Maze.Builder classes
so that Characters with a random strategy can be created and added to
the maze. Update a test (or write a new test) to includes these Characters.

## Human Strategy

We are finally going to be active participants in Polymorphia.
To do this we're going to create a human strategy that uses the
command line interface (CLI) to show us the command choices for the current
situation and allow us to select which command to run.

In order to print out the choices, you'll need to generate a list of
valid choices for the current situation (sounds like the random strategy, huh? Probably some opportunities to be DRY).
Then you'll prompt the user for their choice and return the
command just like every other strategy now does.

### Printing a Menu to the Command Line

Here's an example of code to print a menu out:

```java
for(Command command :validCommands){
        System.out.

println(currentChoiceNumber +": "+option);

currentChoiceNumber++;
        }
```

And here's some code to read a choice from the command line:

```java
    System.out.print("Enter your option: ");

Scanner scanner = new Scanner(System.in);
int choiceNumber = Integer.parseInt(scanner.nextLine());
```

You should handle invalid inputs typed by the human
so that your CLI is robust and will not break if used by
a malicious user or a typing error.

## Testing the Human Strategy

Testing this type of CLI interaction is difficult with
JUnit and, regardless, we don't want an automated test for this
since there will be no one to answer the prompts. Hence,
you are finally going to implement a static _main()_ method!

Recall from the Java lectures that the static _main()_ method
must have this signature:

```java
public static void main(String[] args) {
    // Create the Maze here (hard-coding configuration like a test is fine)
    // Create Polymorphia here, pass it the maze, and run the game
    System.exit(0);
}
```

If you create the above method, IntelliJ will put a green arrow by it
and you can run the game by just clicking on it. Make sure
you're game plays correctly.

You'll demonstrate your game during the interview
grading, where you'll also be asked questions about
the code used in your implementation (same as the last interview grading assignment).

## Requirements for this Assignment

* Implement Command object for every option (10 points):
    * Move
    * Eat
    * Fight
    * Wear Armor
    * Wear Cloak
    * Drink Potion
    * Do Nothing
* Create a CommandFactory and use it to create Commands (5 points)
* Create a Random Strategy and update the Character Factor and Maze.Builder classes to add them to the Maze (5 points).
* Create a Human Strategy and update the Character Factor and Maze.Builder classes to add them to the Maze (10 points).
* Ensure a Random Player and a Human Player can participate in a full game correctly (10 points)
* __Extra Credit (5 points):__ Offer a secondary menu for all Commands with choices. All commands potentially have
  choices, it depends on the contents of the room:
    * For the move command, offer a choice of which room to move to out of the neighboring rooms
    * For the fight command, offer a choice of creature, if there are more than one in the room
    * For the food command, offer a choice of food item, if there are more than one in the room
    * etc.

## Getting Started

Take baby steps. Do a bit and then make sure all the tests still run.
You should not modify any existing tests, except for possibly adding to a test.
These tests will give you the confidence to refactor into the Command Pattern.

Remember that you can run all the Cucumber (Feature File) tests like this:
> ./gradlew cucumberCli

