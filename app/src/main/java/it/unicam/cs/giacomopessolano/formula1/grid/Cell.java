package it.unicam.cs.giacomopessolano.formula1.grid;

public class Cell {

    CellState state;

    public Cell(CellState state) {
        this.state = state;
    }

    public void oil() {
        if (state.equals(CellState.ROAD)) {
            CellState state = CellState.OILED;
        }
    }

}
