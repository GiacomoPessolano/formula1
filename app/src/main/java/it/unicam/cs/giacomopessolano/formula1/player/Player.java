package it.unicam.cs.giacomopessolano.formula1.player;

public interface Player {

    String getName();

    Strategy getStrategy();

    boolean hasCrashed();
}
