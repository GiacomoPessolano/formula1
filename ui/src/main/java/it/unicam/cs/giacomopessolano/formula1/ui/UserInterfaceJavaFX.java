package it.unicam.cs.giacomopessolano.formula1.ui;

import it.unicam.cs.giacomopessolano.formula1.game.GameManager;
import it.unicam.cs.giacomopessolano.formula1.grid.Cell;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;
import  it.unicam.cs.giacomopessolano.formula1.player.Position;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class UserInterfaceJavaFX implements UserInterface {

    private final Stage primaryStage;

    public UserInterfaceJavaFX(Stage stage) {
        this.primaryStage = stage;
    }

    @Override
    public String chooseTrack(String dir) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choose Track");
        dialog.setHeaderText("Choose your track from " + dir);
        dialog.setContentText("Enter track name:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    @Override
    public void displayGrid(GameManager manager) {
        Grid grid = manager.getGrid();
        int width = grid.getWidth();
        int height = grid.getHeight();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char cellChar = parseCellState(manager, grid.getCell(new Position(x, y)));
                javafx.scene.control.Label label = new javafx.scene.control.Label(String.valueOf(cellChar));
                gridPane.add(label, x, y);
            }
        }

        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void turnMessage(GameManager manager) {
        Player player = manager.getCurrentPlayer();
        String name = player.getName();

        if (player.hasCrashed()) {
            showAlert("Crash Notification", name + " has crashed.");
        } else {
            showAlert("Turn Notification", "It's " + name + "'s turn.");
        }
    }

    @Override
    public void pause() {
        // No specific JavaFX equivalent to System.in.read(), so just display a message and wait for user input
        showAlert("Pause", "Press OK to continue...");
    }

    @Override
    public void gameOverMessage(GameManager manager) {
        String winner = manager.getWinner() != null ? manager.getWinner().getName() : "Nobody";
        showAlert("Game Over", "The game has ended. The winner is " + winner + "!");
    }

    @Override
    public boolean wantToPlayAgainMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Play Again?");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to play again?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @Override
    public void errorMessage(String message) {
        showAlert("Error", message);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private char parseCellState(GameManager manager, Cell cell) {
        if (cell.isOccupied()) return String.valueOf(manager.getID(cell.getPlayer())).charAt(0);

        return switch (cell.getState()) {
            case END -> '@';
            case OFFTRACK -> 'X';
            default -> ' ';
        };
    }
}

