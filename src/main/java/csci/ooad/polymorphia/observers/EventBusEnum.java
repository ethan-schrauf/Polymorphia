package csci.ooad.polymorphia.observers;

import csci.ooad.polymorphia.PolymorphiaEventType;
import csci.ooad.polymorphia.characters.Character;

import java.util.ArrayList;
import java.util.List;

public enum EventBusEnum {
    INSTANCE;

    private final List<IPolymorphiaObserver> observers = new ArrayList<>();

    public void attach(IPolymorphiaObserver observer) {
        observers.add(observer);
    }

    public void detach(IPolymorphiaObserver observer) {
        observers.remove(observer);
    }

    public void broadcast(PolymorphiaEventType event, Character character) {
        broadcast(event, character.getName() + ": " + event.getDescription());
    }

    public void broadcast(PolymorphiaEventType event, String message) {
        broadcast(event, List.of(message));
    }
    public void broadcast(PolymorphiaEventType event, List<String> messages) {
        for (IPolymorphiaObserver observer : observers) {
            for (String message : messages) {
                observer.update(event, message);
            }
        }
    }
}
