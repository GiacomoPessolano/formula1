package it.unicam.cs.giacomopessolano.formula1.grid;

public class Cell {

    private CellState state;

    public Cell(CellState state) {
        this.state = state;
    }

    public CellState getState() {
        return state;
    }

    public void oil() {
        if (state.equals(CellState.TRACK)) {
            state = CellState.OILED;
        }
    }

}
