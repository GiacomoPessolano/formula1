package it.unicam.cs.giacomopessolano.formula1.player;

public interface Player extends Cloneable {

    String getName();

    Strategy getStrategy();

    Move getLastMove();

    void setLastMove(Direction choice);

    void crash();

    boolean hasCrashed();

    Player clone();
}
