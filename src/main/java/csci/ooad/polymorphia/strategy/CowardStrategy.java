package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Room;

public class CowardStrategy extends AdventurerStrategy {

    @Override
    boolean shouldFight(Room room) {
        return false;
    }

    @Override
    boolean shouldMove(Room room) {
        return room.hasLivingCreatures() && room.hasNeighbors();
    }
}
