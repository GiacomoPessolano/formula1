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

    private final String filename;
    private final GridInitializerFromTxt gridInitializer;
    private final PlayerInitializerFromTxt playerInitializer;

    public GameInitializerFromTxt(String filename, GridInitializerFromTxt gridInitializer,
                                  PlayerInitializerFromTxt playerInitializer) throws IOException {
        this.filename = filename;
        this.gridInitializer = gridInitializer;
        this.playerInitializer = playerInitializer;
    }

    @Override
    public Map<Player, Position> parsePlayers() throws IOException {
        return playerInitializer.parsePlayers(filename);
    }

    @Override
    public List<Player> parseTurns() throws IOException {
        return playerInitializer.parseTurns(filename);
    }

    @Override
    public Grid parseGrid() throws IOException {
        return gridInitializer.parseGrid(filename);
    }
}
