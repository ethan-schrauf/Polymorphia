Feature: Details of game play
  As a game player
  I want to play a game with a variety of characters
  So that I can see how they interact with each other

  Scenario: Play a complicated game
    Given a maze with the following attributes:
      | number of rooms         | 7 |
      | number of fighters      | 2 |
      | number of cowards       | 2 |
      | number of gluttons      | 2 |
      | number of creatures     | 5 |
      | number of demons        | 2 |
      | number of food items    | 9 |
      | number of armored suits | 4 |
      | number of cloaks        | 3 |
      | number of potions       | 3 |
    When the game is played in the created maze
    Then the game should be over
    And all the adventurers or all of the creatures have died


  Scenario: Play a complicated game with all random adventurers
    Given a maze with the following attributes:
      | number of rooms              | 7 |
      | number of random adventurers | 6 |
      | number of creatures          | 5 |
      | number of demons             | 2 |
      | number of food items         | 9 |
      | number of armored suits      | 4 |
      | number of cloaks             | 3 |
      | number of potions            | 3 |
    When the game is played in the created maze
    Then the game should be over
    And all the adventurers or all of the creatures have died
