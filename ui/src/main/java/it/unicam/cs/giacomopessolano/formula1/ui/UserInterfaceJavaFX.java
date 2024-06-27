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
import  it.unicam.cs.giacomopessolano.formula1.player.Position;

import javafx.application.Platform;
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

    private static final int CELL_SIZE = 15;

    private final Stage primaryStage;
    private Label messageLabel = new Label();
    private GridPane gridPane = new GridPane();
    //private final Object pauseLock = new Object();
    boolean isPaused = false;

    public UserInterfaceJavaFX(Stage stage) {
        this.primaryStage = stage;

        messageLabel.setText("");

        Button unpauseButton = new Button();
        unpauseButton.setText("Next Turn");

        BorderPane root = new BorderPane();
        root.setTop(messageLabel);
        root.setCenter(gridPane);
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
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
    }

    @Override
    public void turnMessage(GameManager manager) {
    }

    @Override
    public void pause() {
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

