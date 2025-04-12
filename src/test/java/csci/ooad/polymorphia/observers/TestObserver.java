package csci.ooad.polymorphia.observers;

import csci.ooad.polymorphia.PolymorphiaEventType;

import java.util.HashMap;
import java.util.Map;

public class TestObserver implements IPolymorphiaObserver {
    Map<PolymorphiaEventType, Integer> eventCounts = new HashMap<>();

    @Override
    public void update(PolymorphiaEventType event, String message) {
        eventCounts.put(event, eventCounts.getOrDefault(event, 0) + 1);
    }

    public int numberOfReceivedDeathEvents() {
        return numberOfReceivedEvents(PolymorphiaEventType.Death);
    }

    public int numberOfReceivedEvents(PolymorphiaEventType event) {
        return eventCounts.getOrDefault(event, 0);
    }

    @Override
    public String toString() {
        return eventCounts.toString();
    }
}
