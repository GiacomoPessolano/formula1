package it.unicam.cs.giacomopessolano.formula1.game;

import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.grid.GridInitializerFromTxt;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import it.unicam.cs.giacomopessolano.formula1.player.PlayerInitializerFromTxt;
import it.unicam.cs.giacomopessolano.formula1.player.Position;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GameInitializerFromTxt implements GameInitializer {

    private final GridInitializerFromTxt gridInitializer;
    private final PlayerInitializerFromTxt playerInitializer;

    public GameInitializerFromTxt(String filename, GridInitializerFromTxt gridInitializer,
                                  PlayerInitializerFromTxt playerInitializer) throws IOException {
        this.gridInitializer = gridInitializer;
        gridInitializer.initialize(filename);
        this.playerInitializer = playerInitializer;
        playerInitializer.initialize(filename);
    }

    @Override
    public Map<Player, Position> parsePlayers() {
        return playerInitializer.getPositions();
    }

    @Override
    public List<Player> parseTurns() {
        return playerInitializer.getPlayers();
    }

    @Override
    public Grid parseGrid() {
        return gridInitializer.getGrid();
    }
}
