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

import it.unicam.cs.giacomopessolano.formula1.exceptions.GameOverException;
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

public class UserInterfaceJavaFX implements UserInterface {

    private static final int CELL_SIZE = 15;

    private final Stage primaryStage;
    private GridPane gridPane = new GridPane();
    private Label message = new Label();

    public UserInterfaceJavaFX(Stage stage) {
        this.primaryStage = stage;
    }

    @Override
    public String chooseTrack(String dir) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choose Track");
        dialog.setHeaderText("Choose your track. Available tracks are in the " + dir + " folder. " +
                "To add a track, put it in said folder.");
        dialog.setContentText("Enter track name:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    @Override
    public void displayGrid(GameManager manager) {
        Grid gameGrid = manager.getGrid();
        GridPane newGridPane = new GridPane();

        for (int x = 0; x < gameGrid.getWidth(); x++) {
            for (int y = 0; y < gameGrid.getHeight(); y++) {
                Position position = new Position(x, y);
                Cell gameCell = gameGrid.getCell(position);
                Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
                rectangle.setFill(parseCellState(gameCell));

                Text text = new Text("");
                if (gameCell.isOccupied()) {
                    text = new Text(manager.getID(gameCell.getPlayer()).substring(0, 1));
                }
                rectangle.setOpacity(0.5);

                StackPane stack = new StackPane();
                stack.getChildren().addAll(rectangle, text);

                newGridPane.add(stack, x, y);
            }
        }
        this.gridPane = newGridPane;

        displayChange(gridPane, message);
    }

    @Override
    public void turnMessage(GameManager manager) {
        if (!manager.isGameRunning()) return;
        Player player = manager.getCurrentPlayer();
        String message;

        if (player.hasCrashed()) {
            message = player.getName() + " has crashed.";
        } else {
            message = "It's " + player.getName() + ", " + manager.getID(player) + " , turn.";
        }
        Label label = new Label(message);
        displayChange(gridPane, label);

    }

    @Override
    public void pause() {
        //todo maybe
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            return;
        }
    }

    @Override
    public void gameOverMessage(GameManager manager) {
        if (manager.isGameRunning()) return;

        Player winner = manager.getWinner();
        String title = "Game Over";
       if (winner != null) {
           showAlert(title, "The winner is " + winner.getName() + "!");
       } else {
           showAlert(title, "Nobody won the game.");
       }
    }

    @Override
    public boolean wantToPlayAgainMessage() throws GameOverException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Play Again?");
        alert.setHeaderText(null);
        alert.setContentText("Press OK if you want to play again.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        } else {
            primaryStage.close();
            throw new GameOverException("");
        }
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

    private void displayChange(GridPane newGridPane, Label newMessage) {
        BorderPane root = new BorderPane();
        this.gridPane = newGridPane;
        this.message = newMessage;

        root.setTop(newMessage);
        root.setCenter(newGridPane);
        Scene scene = new Scene(root, CELL_SIZE * gridPane.getMaxWidth(),
                CELL_SIZE * gridPane.getMaxHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Color parseCellState(Cell cell) {
        if (cell.isOccupied()) {
            return Color.RED;
        }

        return switch (cell.getState()) {
            case END -> Color.GREEN;
            case OFFTRACK -> Color.BLACK;
            default -> Color.LIGHTGRAY;
        };
    }
}

