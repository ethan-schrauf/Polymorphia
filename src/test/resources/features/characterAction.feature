Feature: Details of Character play
  As a character designer
  I want to ensure that different characters are playing with the correct strategy
  So that I can ensure the game is correct


  Scenario: Glutton will fight a Creature if he cannot run away
    Given a maze with the following room:
      | Only Room |
    And a Glutton named "Sir Run Away"
    And a Creature named "Ogre"
    And all characters are in room "Only Room"

    When "Sir Run Away" executes his turn

    Then a fight took place
    And "Ogre" lost some health


  Scenario: Coward will run away if it can
    Given a maze with the following rooms:
      | Starting Room |
      | Other Room    |
    Given a Coward named "Sir Run Away"
    And a Creature named "Ogre"
    And all characters are in room "Starting Room"

    When "Sir Run Away" executes his turn

    Then a fight did not take place
    And "Ogre" did not lose some health
    And "Sir Run Away" lost some health
    And "Sir Run Away" is in room "Other Room"


  Scenario: A Fighter will fight a Creature even if he can run away
    Given a maze with the following rooms:
      | Starting Room |
      | Other Room    |
    And a Fighter named "Sir Galahad"
    And a Creature named "Ogre"
    And all characters are in room "Starting Room"

    When "Sir Galahad" executes his turn

    Then a fight took place
    And "Ogre" lost some health


  Scenario: Glutton will eat food if it is available over fighting or running
    Given a maze with the following rooms:
      | Starting Room |
      | Other Room    |
    And a Glutton named "Eats-a-lot"
    And a Creature named "Ogre"
    And food "apple" is placed in room "Starting Room"
    And all characters are in room "Starting Room"

    When "Eats-a-lot" executes his turn

    Then a fight did not take place
    And "Eats-a-lot" is in room "Starting Room"
    And room "Starting Room" has no food items


  Scenario: A Demon will fight a Glutton even when food and neighboring rooms are available
    Given a maze with the following rooms:
      | Starting Room |
      | Other Room    |
    And a Glutton named "Eats-a-lot"
    And a Demon named "Demon"
    And food "apple" is placed in room "Starting Room"
    And all characters are in room "Starting Room"

    When "Demon" executes his turn

    Then a fight took place
    And "Eats-a-lot" is in room "Starting Room"
    And "Eats-a-lot" lost some health
    And "Demon" lost some health
