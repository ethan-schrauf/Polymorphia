Feature: Details of game play
  As a game player
  I want to play a game with a variety of characters
  So that I can see how they interact with each other


  Scenario: Fighter wears armor
    Given a maze with the following rooms:
      | Starting Room |
      | Other Room    |
    And a Fighter named "Sir Galahad"
    And a Creature named "Ogre"
    And all characters are in room "Starting Room"
    And armor "mithril" with strength 5.0 and moving cost 0.25 is placed in room "Starting Room"

    When "Sir Galahad" executes his turn

    Then a fight did not take place
    And "Sir Galahad" is in room "Starting Room"
    And there is no armor in room "Starting Room"
    And room "Starting Room" now contains an armored character

  Scenario: Fighter wears two sets of armor and has greater defense
    Given a maze with the following rooms:
      | Starting Room |
    And a Fighter named "Sir Galahad" in room "Starting Room"
    And a Creature named "Ogre" in room "Starting Room"
    And armor "mithril" with strength 3.0 and moving cost 0.25 is placed in room "Starting Room"
    And armor "gold-plated" with strength 4.0 and moving cost 0.25 is placed in room "Starting Room"

    When "Sir Galahad" executes his turn
    Then room "Starting Room" now contains an armored character

    When "Sir Galahad" executes his turn
    Then there is no armor in room "Starting Room"

    When "Sir Galahad" executes his turn
    Then a fight took place
    And "Sir Galahad" did not lose some health
    And "Ogre" lost some health


  Scenario: Armored Character moves correctly
    Given a maze with the following rooms:
      | Starting Room |
      | Other Room    |
    And a Fighter named "Sir Galahad" in room "Starting Room"
    And armor "mithril" with strength 1.0 and moving cost 0.25 is placed in room "Starting Room"

    When "Sir Galahad" executes his turn

    Then there is no armor in room "Starting Room"
    And room "Starting Room" now contains an armored character
    And "Sir Galahad" is in room "Starting Room"

    When "Sir Galahad" executes his turn

    Then "Sir Galahad" is in room "Other Room"
    And room "Other Room" now contains an armored character
    And "Sir Galahad" is not in room "Starting Room"
    # Lost default health (0.25) and moving cost health (0.25)
    And "Sir Galahad" lost 0.5 health



  Scenario: Coward wears invisibility cloak and Demon can't see him.
    Given a maze with the following rooms:
      | Only Room |
    And a Coward named "Sir Fearful" in room "Only Room"
    And a Demon named "Satan" in room "Only Room"
    And cloak "Invisibility" is placed in room "Only Room"

    When "Sir Fearful" executes his turn
    Then room "Only Room" now contains a cloaked character
    Then there is no cloak in room "Only Room"

    When "Satan" executes his turn
    Then a fight did not take place


