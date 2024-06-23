package it.unicam.cs.giacomopessolano.formula1.grid;

import it.unicam.cs.giacomopessolano.formula1.player.Player;

public class Cell {

    private CellState state;

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

    public void oil() {
        if (state.equals(CellState.TRACK)) {
            state = CellState.OILED;
        }
    }

}
