package it.unicam.cs.giacomopessolano.formula1.player;

//TODO javadoc

public class Formula1Player implements Player {

    private final String name;
    private final Strategy strategy;

    public Formula1Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Strategy getStrategy() {
        return strategy;
    }

    public boolean isInteractive() {
        return strategy != null;
    }
}
