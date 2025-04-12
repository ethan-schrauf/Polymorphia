package csci.ooad.polymorphia.artifacts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArtifactTest {

    @Test
    void testArtifactCreation() {
        Artifact popcorn = new Food("popcorn");
        assertTrue(popcorn.isType(Artifact.ArtifactType.Food));

        Artifact ironSuit = new Armor("Iron");
        assertTrue(ironSuit.isType(Artifact.ArtifactType.Armor));
    }

}
