package csci.ooad.polymorphia.observers;

import csci.ooad.polymorphia.PolymorphiaEventType;

public class AudibleObserver implements IPolymorphiaObserver {
    private final AudiblePlayer audiblePlayer = new AudiblePlayer();

    @Override
    public void update(PolymorphiaEventType event, String message) {
        audiblePlayer.say(message);
    }
}
