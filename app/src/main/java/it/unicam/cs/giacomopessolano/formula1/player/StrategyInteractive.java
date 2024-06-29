package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.exceptions.NoPossibleMoveException;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;

import java.util.List;

public class StrategyInteractive implements Strategy {

    InteractionHandler interactionHandler;

    public StrategyInteractive(InteractionHandler interactionHandler) {
        this.interactionHandler = interactionHandler;
    }

    @Override
    public Direction makeChoice(Grid grid, Move lastMove, Position position) throws NoPossibleMoveException {
        List<Direction> choices = Strategy.possibleMoves(grid, lastMove, position);
        if (choices.isEmpty()) {
            throw new NoPossibleMoveException("There are no more possible moves.");
        }

        return interactionHandler.takeInput(choices);
    }
}
