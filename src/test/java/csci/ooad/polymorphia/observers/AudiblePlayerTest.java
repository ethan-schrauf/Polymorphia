package csci.ooad.polymorphia.observers;

import org.junit.jupiter.api.Disabled;

class AudiblePlayerTest {

    @Disabled
    void testTextToSpeech() {
        AudiblePlayer tts = new AudiblePlayer();
        tts.say("Hello, world!");
    }

}