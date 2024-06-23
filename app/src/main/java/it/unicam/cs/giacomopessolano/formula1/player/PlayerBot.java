package it.unicam.cs.giacomopessolano.formula1.player;

public class PlayerBot implements Player {

    private final String name;
    private final Strategy strategy;
    private Move lastMove;
    private boolean hasCrashed;

    PlayerBot(String name, Strategy strategy, Direction choice) {
        this.name = name;
        this.strategy = strategy;
        lastMove = firstMove(choice);
        hasCrashed = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Strategy getStrategy() {
        return strategy;
    }

    public void crash() {
        hasCrashed = true;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Direction choice) {
        this.lastMove = lastMove.update(choice);
    }

    @Override
    public boolean hasCrashed() {
        return hasCrashed;
    }

    private Move firstMove(Direction choice) {
        return new Move(0, 0).update(choice);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof PlayerBot other) {
            return name.equals(other.name) && strategy.equals(other.strategy)
                    && lastMove.equals(other.lastMove);
        }

        return false;
    }
}
