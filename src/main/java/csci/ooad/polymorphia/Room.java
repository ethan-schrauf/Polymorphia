package csci.ooad.polymorphia;

import csci.ooad.polymorphia.artifacts.Artifact;
import csci.ooad.polymorphia.artifacts.Artifact.ArtifactType;
import csci.ooad.polymorphia.characters.Character;

import java.util.*;
import java.util.stream.Collectors;


public class Room {
    private final String name;
    private final List<Room> neighbors = new ArrayList<>();
    private final List<Character> characters = new ArrayList<>();
    private final List<Artifact> artifacts = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Character> getLivingAdventurers() {
        return characters.stream()
                .filter(Character::isAdventurer)
                .filter(Character::isAlive)
                .sorted().toList();
    }

    public List<Character> getLivingVisibleAdventurers() {
        return getLivingAdventurers().stream()
                .filter(Character::isVisible)
                .sorted().toList();
    }

    public List<Character> getLivingCreatures() {
        return characters.stream()
                .filter(Character::isCreature)
                .filter(Character::isAlive)
                .sorted()
                .toList();
    }

    public List<String> getContents() {
        List<String> contents = new ArrayList<>(getLivingCharacters().stream()
                .map(Object::toString)
                .toList());
        contents.addAll(this.artifacts.stream()
                .map(Object::toString)
                .toList());
        return contents;
    }

    public void addNeighbor(Room neighbor) {
        // Make sure we are never a neighbor of ourselves
        assert this != neighbor;
        this.neighbors.add(neighbor);
    }

    public void connect(Room neighbor) {
        this.addNeighbor(neighbor);
        neighbor.addNeighbor(this);
    }

    @Override
    public String toString() {
        String representation = "\t" + name + ":\n\t\t";
        representation += String.join("\n\t\t", getContents());
        return representation;
    }

    public void add(Character character) {
        characters.add(character);
        character.enterRoom(this);
    }

    public Boolean hasLivingCreatures() {
        return characters.stream()
                .filter(Character::isCreature)
                .anyMatch(Character::isAlive);
    }

    public Boolean hasLivingAdventurers() {
        return characters.stream()
                .filter(Character::isAdventurer)
                .anyMatch(Character::isAlive);
    }

    public void remove(Character character) {
        characters.remove(character);
    }

    public Room getRandomNeighbor() {
        if (neighbors.isEmpty()) {
            return null;
        }
        return neighbors.get(Die.randomLessThan(neighbors.size()));
    }

    public void enter(Character character) {
        add(character);
    }

    public List<Character> getLivingCharacters() {
        return characters.stream()
                .filter(Character::isAlive)
                .toList();
    }

    public void add(Artifact artifact) {
        artifacts.add(artifact);
    }

    public Optional<Character> getHealthiestAdventurer() {
        return getLivingAdventurers().stream().max(Comparator.naturalOrder());
    }

    public Optional<Character> getHealthiestCreature() {
        return getLivingCreatures().stream().max(Comparator.naturalOrder());
    }

    public Boolean hasDemon() {
        return characters.stream().filter(Character::isAlive).anyMatch(Character::isDemon);
    }

    public int numberOfNeighbors() {
        return neighbors.size();
    }

    public boolean hasNeighbor(Room neighbor) {
        return neighbors.contains(neighbor);
    }

    public Optional<Character> getAdventurer() {
        return getLivingVisibleAdventurers().stream().findAny();
    }

    public Optional<Character> getCreature() {
        return getLivingCreatures().stream().findAny();
    }

    public Optional<Artifact> getFood() {
        return get(ArtifactType.Food);
    }

    public boolean has(ArtifactType type) {
        return artifacts.stream().anyMatch((a) -> a.isType(type));
    }

    public Optional<Artifact> get(ArtifactType type) {
        return artifacts.stream().filter((a) -> a.isType(type)).findAny();
    }

    public List<Artifact> getAll(ArtifactType type) {
        return artifacts.stream().filter((a) -> a.isType(type)).collect(Collectors.toList());
    }

    public List<Artifact> getAllFood() {
        return getAll(ArtifactType.Food);
    }

    List<String> getNeighborRoomNames() {
        return neighbors.stream().map(Room::getName).toList();
    }

    public List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }

    public boolean hasNeighbors() {
        return !neighbors.isEmpty();
    }

    public void removeArtifact(Artifact artifact) {
        artifacts.remove(artifact);
    }

    public boolean hasFood() {
        return has(ArtifactType.Food);
    }

    public boolean hasArmor() {
        return has(ArtifactType.Armor);
    }

    public boolean hasCloak() {
        return has(ArtifactType.Cloak);
    }

    public Optional<Artifact> getCloak() {
        return get(ArtifactType.Cloak);
    }

    public Optional<Artifact> getArmor() {
        return get(ArtifactType.Armor);
    }

    public Optional<Artifact> getPotion() {
        return get(ArtifactType.Potion);
    }

    public void replace(Character original, Character replacement) {
        characters.remove(original);
        characters.add(replacement);
    }

    public boolean hasCharacterWithName(String nameSnippet) {
        return getLivingCharacters().stream().anyMatch((a) -> a.getName().contains(nameSnippet));
    }

    public boolean hasPotion() {
        return has(ArtifactType.Potion);
    }

    public List<Artifact> getAllArmor() {
        return getAll(ArtifactType.Armor);
    }

    public List<Artifact> getAllCloaks() {
        return getAll(ArtifactType.Cloak);
    }

    public List<Artifact> getAllPotions() {
        return getAll(ArtifactType.Potion);
    }

    public Collection<Room> getNeighbors() {
        return new ArrayList<>(neighbors);
    }

    public Optional<Character> getMe(Character me) {
        return characters.stream().filter((character) -> character.equals(me)).findAny();
    }
}
