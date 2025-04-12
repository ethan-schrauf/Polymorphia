package csci.ooad.polymorphia.artifacts;

import csci.ooad.polymorphia.artifacts.Artifact.ArtifactType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArtifactFactoryTest {
    ArtifactFactory factory = new ArtifactFactory();

    @Test
    void testSingleCreation() {
        Artifact food = factory.createFood("Salad");
        System.out.println(food);
        assertTrue(food.getHealthValue() > 1);

        Artifact armor = factory.create(ArtifactType.Armor, "Leather");
        System.out.println(armor);
        assertTrue(armor.getDefenseValue() > 1);
    }

    @Test
    void testMultipleCreation() {
        List<Artifact> foodItems = factory.createNumberOf(ArtifactType.Food, 5);
        System.out.println(foodItems);
        assertEquals(5, foodItems.size());

        List<Artifact> armoredSuits = factory.createNumberOf(ArtifactType.Armor, 5);
        System.out.println(armoredSuits);
        assertEquals(5, armoredSuits.size());
    }

}