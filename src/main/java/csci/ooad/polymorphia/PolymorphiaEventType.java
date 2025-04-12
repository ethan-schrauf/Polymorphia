package csci.ooad.polymorphia;

public enum PolymorphiaEventType {
    FightOccurred,
    AteSomething,
    TurnEnded,
    GameOver,
    GameStart,
    Moved,
    Death,
    Killed,
    ALL;

    public String getDescription() {
        return switch (this) {
            case FightOccurred -> "fought";
            case Killed -> "killed a creature";
            case AteSomething -> "ate something";
            case TurnEnded -> "turn ended";
            case GameOver -> "game over";
            case GameStart -> "game start";
            case Moved -> "moved";
            case Death -> "just died";
            case ALL -> "all";
        };
    }
}
