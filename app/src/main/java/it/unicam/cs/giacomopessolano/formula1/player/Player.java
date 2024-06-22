package it.unicam.cs.giacomopessolano.formula1.player;

//TODO javadoc

public interface Player {
    String getName();

    Strategy getStrategy();

    boolean isInteractive();
}
