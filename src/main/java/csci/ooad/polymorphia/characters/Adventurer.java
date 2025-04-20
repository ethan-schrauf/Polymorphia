package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.commands.Command;

import java.util.List;

public class Adventurer extends Character {
    public Adventurer(String name) {
        super(name);
    }

    public Adventurer(String name, Double initialHealth) {
        super(name, initialHealth);
    }

    @Override
    public Boolean isAdventurer() {
        return true;
    }

    @Override
    public String toString() {
        return "Adv: " + super.toString();
    }


}