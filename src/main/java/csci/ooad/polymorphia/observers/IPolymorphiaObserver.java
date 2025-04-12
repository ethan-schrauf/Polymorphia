package csci.ooad.polymorphia.observers;

import csci.ooad.polymorphia.PolymorphiaEventType;

public interface IPolymorphiaObserver {
    void update(PolymorphiaEventType event, String message);
}
