package it.unicam.cs.giacomopessolano.formula1.player;

public class PlayerBot implements Player {

    private final String name;
    private final Strategy strategy;
    private boolean hasCrashed;

    PlayerBot(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
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

    @Override
    public boolean hasCrashed() {
        return hasCrashed;
    }
}
