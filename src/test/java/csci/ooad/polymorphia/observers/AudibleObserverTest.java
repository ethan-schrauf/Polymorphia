package csci.ooad.polymorphia.observers;

import csci.ooad.polymorphia.PolymorphiaEventType;
import org.junit.jupiter.api.Disabled;

class AudibleObserverTest {

    @Disabled
    void testAudibleObserver() {
        AudibleObserver audibleObserver = new AudibleObserver();
        audibleObserver.update(PolymorphiaEventType.ALL, "Hello, world!");
    }

}