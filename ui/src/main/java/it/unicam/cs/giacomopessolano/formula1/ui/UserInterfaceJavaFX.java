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
import  it.unicam.cs.giacomopessolano.formula1.player.Position;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Optional;

public class UserInterfaceJavaFX implements UserInterface {

    private static final int CELL_SIZE = 20;

    private final Stage primaryStage;
    private final Label messageLabel = new Label();;
    private final GridPane grid = new GridPane();;
    //private final Object pauseLock = new Object();
    boolean isPaused = false;

    public UserInterfaceJavaFX(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Formula 1 Game");

        Button nextTurnButton = new Button("Next Turn");

        //nextTurnButton.setOnAction(e -> unpauseGame());

        BorderPane root = new BorderPane();
        root.setTop(messageLabel);
        root.setBottom(nextTurnButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
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
        Grid grid = manager.getGrid();
        int width = grid.getWidth();
        int height = grid.getHeight();

        GridPane gridPane = new GridPane();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                StackPane cellStack = new StackPane();
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                Position position = new Position(x, y);

                if (grid.getCell(position).isOccupied()) {
                    Label playerIdLabel = new Label();
                    playerIdLabel.setText(String.valueOf(
                            manager.getID(grid.getCell(position).getPlayer()).charAt(0)));
                    playerIdLabel.setTextFill(Color.BLACK);
                    playerIdLabel.setAlignment(Pos.CENTER);
                    cellStack.getChildren().add(playerIdLabel);
                }
                cellStack.getChildren().add(cell);
                cell.setFill(parseCellState(grid.getCell(position)));

                gridPane.add(cellStack, x, y);
            }
        }

        Scene scene = new Scene(gridPane, width * CELL_SIZE, height * CELL_SIZE);
        primaryStage.setScene(scene);
    }

    @Override
    public void turnMessage(GameManager manager) {
        if (!manager.isGameRunning()) return;
        Player player = manager.getCurrentPlayer();
        String name = player.getName();

        if (player.hasCrashed()) {
            messageLabel.setText(name + " has crashed.");
        } else {
            messageLabel.setText("It's " + name + ", " + manager.getID(player) + " , turn.");
        }
    }

    @Override
    public void pause() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return;
        }
        /*synchronized (pauseLock) {
            isPaused = true;
            while (isPaused) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }*/
    }

    @Override
    public void gameOverMessage(GameManager manager) {
        if (manager.isGameRunning()) return;

        Player winner = manager.getWinner();
        String title = "Winner";
       if (winner != null) {
           showAlert(title, "The winner is " + winner.getName() + "!");
       } else {
           showAlert(title, "Nobody won the game.");
       }
    }

    @Override
    public boolean wantToPlayAgainMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Play Again?");
        alert.setHeaderText(null);
        alert.setContentText("Press OK if you want to play again.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        } else {
            primaryStage.close();
            return false;
        }
    }

    @Override
    public void errorMessage(String message) {
        showAlert("Error", message);
    }

    /*
    private void unpauseGame() {
        synchronized (pauseLock) {
            isPaused = false;
            pauseLock.notifyAll();
        }
    }*/

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

