package it.unicam.cs.giacomopessolano.formula1.grid;

import it.unicam.cs.giacomopessolano.formula1.player.Player;

public class Cell implements Cloneable {

    private final CellState state;

    private Player player;

    public Cell(CellState state) {
        this.state = state;
    }

    public CellState getState() {
        return state;
    }

    public void occupy(Player player) {
        this.player = player;
    }

    public void flushPlayer() {
        this.player = null;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Cell clone() {
        try {
            Cell cloned = (Cell) super.clone();

            if (this.player != null) {
                cloned.player = this.player.clone();
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
