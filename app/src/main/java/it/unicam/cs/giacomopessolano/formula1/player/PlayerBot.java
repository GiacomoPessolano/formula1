package it.unicam.cs.giacomopessolano.formula1.player;

public class PlayerBot implements Player {

    private final String name;
    private final Strategy strategy;

    PlayerBot(String name, Strategy strategy) {
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
}
