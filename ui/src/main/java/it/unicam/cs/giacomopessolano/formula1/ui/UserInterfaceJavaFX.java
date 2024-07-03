/*
 * MIT License
 *
 * Copyright (c) 2024 Giacomo Pessolano
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.unicam.cs.giacomopessolano.formula1.ui;

import it.unicam.cs.giacomopessolano.formula1.game.GameManager;
import it.unicam.cs.giacomopessolano.formula1.grid.Cell;
import it.unicam.cs.giacomopessolano.formula1.grid.Grid;
import it.unicam.cs.giacomopessolano.formula1.player.Player;

import it.unicam.cs.giacomopessolano.formula1.player.Position;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Implementation of a simple user interface using JavaFX.
 */
public class UserInterfaceJavaFX implements UserInterface {

    /**
     * Size of the grid's cells in the graphical display.
     */
    private static final int CELL_SIZE = 15;
    /**
     * UI's primary stage.
     */
    private final Stage primaryStage;
    /**
     * GridPane used to represent a game's grid.
     */
    private final GridPane gridPane = new GridPane();
    /**
     * Label with a message on the game's current state.
     */
    private final Label message = new Label();
    /**
     * Button for unpausing the simulation.
     */
    private final Button unpauseButton = new Button("Play next turn.");
    /**
     * Lock for pausing the game.
     */
    private final Object pauseLock = new Object();
    /**
     * Checks if the game is paused.
     */
    private boolean isGamePaused = false;

    /**
     * Constructs an UserInterfaceJavaFX by initializing its primary stage and components.
     * Creates a scene with the message on top, the grid in the center and the button at the bottom.
     * The scene's elements start as empty.
     *
     * @param stage The primary stage of the graphical interface.
     */
    public UserInterfaceJavaFX(Stage stage) {
        assert stage != null;
        this.primaryStage = stage;

        unpauseButton.setOnAction(press -> {
            synchronized (pauseLock) {
                isGamePaused = false;
                pauseLock.notify();
            }
        });

        BorderPane root = new BorderPane();
        root.setTop(message);
        root.setCenter(gridPane);
        root.setBottom(unpauseButton);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Formula 1 Game");
    }

    /**
     * Lets the user enter a track name.
     *
     * @param dir The directory where tracks are located.
     * @return The track's name if the user pressed OK, empty string otherwise.
     */
    @Override
    public String chooseTrack(String dir) {
        assert dir != null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choose Track");
        dialog.setHeaderText("Choose your track. Available tracks are in the " + dir + " folder. " +
                "To add a track, put it in said folder.");
        dialog.setContentText("Enter track name:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    /**
     * Replaces the grid with the GameManager's current grid. The stage is displayed when the grid has been
     * replaced.
     *
     * @param manager Game to take the grid from.
     */
    @Override
    public void displayGrid(GameManager manager) {
        assert manager != null;
        Grid gameGrid = manager.getGrid();

        gridPane.getChildren().clear();

        for (int x = 0; x < gameGrid.getWidth(); x++) {
            for (int y = 0; y < gameGrid.getHeight(); y++) {
                //puts a rectangle at each cell with a color based on its state
                Position position = new Position(x, y);
                Cell gameCell = gameGrid.getCell(position);
                Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
                rectangle.setFill(parseCellState(gameCell));

                //adds player ID on top of the rectangle, if there is a player on the cell
                Text text = new Text("");
                if (gameCell.isOccupied()) {
                    text.setText(manager.getID(gameCell.getPlayer()).substring(0, 1));
                }
                rectangle.setOpacity(0.5);

                StackPane stack = new StackPane();
                stack.getChildren().addAll(rectangle, text);

                gridPane.add(stack, x, y);
            }
        }

        primaryStage.show();
    }

    /**
     * Updates the message label with the game's current turn.
     *
     * @param manager Game to take the turn from.
     */
    @Override
    public void turnMessage(GameManager manager) {
        assert manager != null;
        if (!manager.isGameRunning()) return;
        Player player = manager.getCurrentPlayer();
        String messageText;

        if (player.hasCrashed()) {
            messageText = player.getName() + " has crashed.";
        } else {
            messageText = "It's " + player.getName() + ", " + manager.getID(player) + ", turn.";
        }

        message.setText(messageText);
    }

    /**
     * Pauses the game until the unpause button is pressed.
     */
    @Override
    public void pause() {
        try {
            synchronized (pauseLock) {
                isGamePaused = true;
                {
                    while (isGamePaused) {
                        pauseLock.wait();
                    }
                }
            }
        } catch (InterruptedException ignored) {
            //if there is an exception it's better to ignore the pause
        }
    }

    /**
     * Updates the message label with a game over message.
     *
     * @param manager Game to take the results from.
     */
    @Override
    public void gameOverMessage(GameManager manager) {
        assert manager != null;
        if (manager.isGameRunning()) return;

        Player winner = manager.getWinner();
       if (winner != null) {
           message.setText("The winner is " + winner.getName() + "!");
       } else {
           message.setText("Nobody won the game.");
       }

       unpauseButton.setText("Exit Game");
    }

    /**
     * Displays an error message as an alert.
     *
     * @param message Message displayed.
     */
    @Override
    public void errorMessage(String message) {
        if (message != null) {
            message = "";
        }
        showAlert("Error", message);
    }

    /**
     * Creates an alert.
     *
     * @param title Alert's title.
     * @param content Alert's content,
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Translates a cell state to a color.
     *
     * @param cell Cell to examine.
     * @return Color representing the cell's state.
     */
    private Color parseCellState(Cell cell) {
        if (cell.isOccupied()) {
            Player player = cell.getPlayer();
            if (player.hasCrashed()) {
                return Color.RED;
            } else {
                return Color.BLUE;
            }
        }

        return switch (cell.getState()) {
            case END -> Color.GREEN;
            case OFFTRACK -> Color.BLACK;
            default -> Color.LIGHTGRAY;
        };
    }
}

