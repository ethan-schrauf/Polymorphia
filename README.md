[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/7D8a6TyM)
# OOAD Homework Standard Intro / Preamble

    Team Members: Ethan Schrauf and Ragan Lee
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

# OOAD Homework 9:

## Turning Polymorphia into a Service (50 points)

## Introduction

In this assignment, you are going to turn the Polymorphia game into a service. You will be using Spring Boot to create a
RESTful
service that will allow you to play the game over HTTP. We'll also modify the game so that it can be played one turn at
a time.
We'll also add a new player type called APIPlayer that will allow you to play the game using our web interface (or web
API).

### Grading Rubric

* Game changes to play one turn at a time (5 points)
* Adding the API Player and to keep track of players still in need of a turn (10 points)
* Creating the adapter class (5 points)
* Implementing endpoints (5 points for each of four endpoints - 20 points total)
* Writing the tests (10 points)

### Required Capabilities

* Convert game play to go one turn at a time.
* A turn ends when all players have executed their turn
* but a turn will **pause** when the game needs human input (if there is a API player in the game).
* Create a new subclass of Adventurer called APIPlayer (or handle it with a new Strategy). This player will have a
  strategy that will be called to
  determine the next move, just like our Human player, but this time the human will use the web UI and not type into a
  command-line console.
* Use the _PlayStrategy.getAllPossibleCommands()_ method to return list of command choices.
  These will be passed to the UI (in some JSON form) via the API where the user will be able to select one.
* Modify Polymorphia to check if there is an API Player in the game and handle it accordingly.
* Polymorphia needs to keep track of the players still pending a turn.
* You'll need to create an adaptor class that represents the game state which can be easily turned into JSON by our
  SpringBoot framework.
  This class should just consist of simple types and a constructor. You'll return it
  from some of the endpoints, and it will automatically be converted to JSON by the SpringBoot framework. I've supplied
  you with a starting class _PolymorphiaJsonAdaptor_.
* Implement the endpoints listed below.

#### /api/games [GET]

Example response:

```json
[
  "Seven-room Maze",
  "API2"
]
```

This returns a list of the names/ids of the games that are currently running. You can use the name/id of the game to get
the
game's state (see below).

#### /api/game/create [POST]

This endpoint creates a new game. The request body will be a JSON object with the fields shown below.
You'll need to create a record class to represent this request. Your endpoint declaration will look like this:

```java

@PostMapping("/api/game/create")
public ResponseEntity<?> createGame(@Validated @RequestBody PolymorphiaParameters params) {

}
```

Example request:

```json
{
  "name": "My Game",
  "playerName": "Professor",
  "numRooms": 7,
  "numAdventurers": 2,
  "numCreatures": 2,
  "numKnights": 1,
  "numCowards": 1,
  "numGluttons": 1,
  "numDemons": 2,
  "numFood": 10,
  "numArmor": 3
}
```

Example response:

```json
{
  "name": "My Game",
  "turn": 0,
  "inMiddleOfTurn": false,
  "gameOver": false,
  "statusMessage": "Turn 0 just ended.",
  "livingAdventurers": [
    "Arwen",
    "Professor",
    "Lady Brienne",
    "Sir Eats-a-lot",
    "Sir Lancelot",
    "Frodo",
    "Sir Robin"
  ],
  "livingCreatures": [
    "Ogre",
    "Beelzebub",
    "Satan",
    "Dragon"
  ],
  "rooms": [
    {
      "name": "Goblin's Fountain",
      "neighbors": [
        "Pool of Lava",
        "Troll Bridge"
      ],
      "contents": [
        "Lady Brienne(health: 8.00)",
        "Dragon(health: 3.00)",
        "eggs(1.9)",
        "plate-Armor"
      ]
    },
    {
      "name": "Pit of Despair",
      "neighbors": [
        "Stalactite Cave",
        "Troll Bridge"
      ],
      "contents": [
        "Frodo(health: 5.00)",
        "Sir Robin(health: 5.00)",
        "fries(2.0)",
        "pizza(1.4)"
      ]
    },
    {
      "name": "Pool of Lava",
      "neighbors": [
        "Goblin's Fountain",
        "Dungeon",
        "Stalactite Cave"
      ],
      "contents": [
        "Ogre(health: 3.00)",
        "Beelzebub(health: 15.00)"
      ]
    },
    {
      "name": "Dungeon",
      "neighbors": [
        "Pool of Lava",
        "Dragon's Den"
      ],
      "contents": [
        "cupcake(1.7)",
        "banana(1.7)"
      ]
    },
    {
      "name": "Dragon's Den",
      "neighbors": [
        "Dungeon",
        "Stalactite Cave"
      ],
      "contents": [
        "Sir Lancelot(health: 8.00)",
        "Sir Eats-a-lot(health: 5.00)",
        "salad(1.6)"
      ]
    },
    {
      "name": "Stalactite Cave",
      "neighbors": [
        "Pit of Despair",
        "Pool of Lava",
        "Dragon's Den"
      ],
      "contents": [
        "Arwen(health: 5.00)",
        "Satan(health: 15.00)",
        "Professor(health: 8.00)",
        "burger(1.4)",
        "chainmail-Armor"
      ]
    },
    {
      "name": "Troll Bridge",
      "neighbors": [
        "Goblin's Fountain",
        "Pit of Despair"
      ],
      "contents": [
        "apple(1.6)",
        "steak(1.8)",
        "bacon(1.2)",
        "leather-Armor"
      ]
    }
  ],
  "availableCommands": []
}
```

#### /api/game/{gameId}/playTurn [PUT]

This is the request to just play a turn. Notice that NULL the final word.

Example request: /api/game/My%20Game/playTurn/NULL

A request that indicates which command the human player has chosen looks like this:

    /api/game/My%20Game/playTurn/MOVE
    /api/game/My%20Game/playTurn/EAT
    /api/game/My%20Game/playTurn/WEAR%20ARMOR
    /api/game/My%20Game/playTurn/FIGHT
    /api/game/My%20Game/playTurn/DO%20NOTHING

There is no payload for this command. An example response is the new status of
the game:

```json
{
  "name": "Four Room",
  "turn": 2,
  "inMiddleOfTurn": false,
  "gameOver": false,
  "statusMessage": "Turn 2 just ended.",
  "livingAdventurers": [
    "Frodo",
    "chainmail-Armored Lady Brienne",
    "Arwen",
    "Professor",
    "Sir Lancelot"
  ],
  "livingCreatures": [
    "Dragon",
    "Satan"
  ],
  "rooms": [
    {
      "name": "Crystal Palace",
      "neighbors": [
        "Pool of Lava",
        "Swamp",
        "Stalactite Cave"
      ],
      "contents": [
        "leather-Armor"
      ]
    },
    {
      "name": "Pool of Lava",
      "neighbors": [
        "Crystal Palace",
        "Swamp",
        "Stalactite Cave"
      ],
      "contents": [
        "Arwen(health: 6.05)",
        "Professor(health: 7.50)"
      ]
    },
    {
      "name": "Swamp",
      "neighbors": [
        "Crystal Palace",
        "Pool of Lava"
      ],
      "contents": [
        "Frodo(health: 6.66)",
        "chainmail-Armored Lady Brienne(health: 8.00)",
        "cupcake(1.7)",
        "apple(1.3)",
        "burger(1.5)"
      ]
    },
    {
      "name": "Stalactite Cave",
      "neighbors": [
        "Crystal Palace",
        "Pool of Lava"
      ],
      "contents": [
        "Sir Lancelot(health: 2.00)",
        "Dragon(health: 3.00)",
        "Satan(health: 14.00)",
        "salad(1.7)",
        "fries(1.3)"
      ]
    }
  ],
  "availableCommands": []
}
```

If the game pauses to get input from the API player, the response will populate the availableCommands array with the
possible commands
and look like this:

```json
{
  "name": "Four Room",
  "turn": 3,
  "inMiddleOfTurn": true,
  "gameOver": false,
  "statusMessage": "In the middle of turn 3 Waiting for Professor to make a move.",
  "livingAdventurers": [
    "Arwen",
    "Frodo",
    "chainmail-Armored Lady Brienne",
    "Professor"
  ],
  "livingCreatures": [
    "Dragon",
    "Satan"
  ],
  "rooms": [
    {
      "name": "Crystal Palace",
      "neighbors": [
        "Pool of Lava",
        "Swamp",
        "Stalactite Cave"
      ],
      "contents": [
        "leather-Armor"
      ]
    },
    {
      "name": "Pool of Lava",
      "neighbors": [
        "Crystal Palace",
        "Swamp",
        "Stalactite Cave"
      ],
      "contents": [
        "Professor(health: 7.50)"
      ]
    },
    {
      "name": "Swamp",
      "neighbors": [
        "Crystal Palace",
        "Pool of Lava"
      ],
      "contents": [
        "Frodo(health: 8.35)",
        "chainmail-Armored Lady Brienne(health: 9.47)",
        "Arwen(health: 5.80)",
        "apple(1.3)"
      ]
    },
    {
      "name": "Stalactite Cave",
      "neighbors": [
        "Crystal Palace",
        "Pool of Lava"
      ],
      "contents": [
        "Dragon(health: 3.00)",
        "Satan(health: 13.50)",
        "salad(1.7)",
        "fries(1.3)"
      ]
    }
  ],
  "availableCommands": [
    "DO NOTHING",
    "MOVE"
  ]
}
```

## Getting Started

First, you should start up the service and confirm that it comes up and you can hit the base URL
and see the UI. Nothing will work at this point.

Create your Adapter for the state of Polymorphia. Make a test for it.

Next, get the UI working for a default game that does not contain an API player.

Some tests are provided for you. You should run these tests and see that they fail. Then you should
implement the endpoints and the game logic to make the tests pass.

Search for TODO: to find areas you will need to expand on. This is likely not a complete
list. IntelliJ has a nice facility to view TODOs:
![](images/screenshotOfTODOsInIntellij.png)