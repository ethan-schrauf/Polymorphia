Feature: Details of game play
  As a game player
  I want to play a game with a variety of characters
  So that I can see how they interact with each other


  Scenario: Play the simplest game
    Given a maze with the following attributes:
      | number of rooms      | 1 |
      | number of fighters   | 1 |
      | number of creatures  | 1 |
      | number of food items | 1 |
    When the game is played in the created maze
    Then all the adventurers or all of the creatures have died


  Scenario: Play a complicated game
    Given a maze with the following attributes:
      | number of rooms      | 7 |
      | number of fighters   | 2 |
      | number of cowards    | 2 |
      | number of gluttons   | 2 |
      | number of creatures  | 5 |
      | number of demons     | 2 |
      | number of food items | 9 |
    When the game is played in the created maze
    Then the game should be over
    And all the adventurers or all of the creatures have died
