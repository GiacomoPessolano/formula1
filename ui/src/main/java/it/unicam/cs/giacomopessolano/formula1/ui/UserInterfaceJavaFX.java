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

public class UserInterfaceJavaFX implements UserInterface {

    private static final int CELL_SIZE = 15;

    private final Stage primaryStage;
    private GridPane gridPane = new GridPane();
    private Label message = new Label();
    private final Button unpauseButton = new Button("Play next turn.");
    private final Object pauseLock = new Object();
    private boolean isGamePaused = false;

    public UserInterfaceJavaFX(Stage stage) {
        this.primaryStage = stage;

        unpauseButton.setOnAction(press -> {
            synchronized (pauseLock) {
                isGamePaused = false;
                pauseLock.notify();
            }
        });

        // Initialize UI components
        BorderPane root = new BorderPane();
        root.setTop(message);
        root.setCenter(gridPane);
        root.setBottom(unpauseButton);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Formula 1 Game");
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

        // Update grid pane
        gridPane.getChildren().clear(); // Clear previous grid cells

        for (int x = 0; x < gameGrid.getWidth(); x++) {
            for (int y = 0; y < gameGrid.getHeight(); y++) {
                Position position = new Position(x, y);
                Cell gameCell = gameGrid.getCell(position);
                Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
                rectangle.setFill(parseCellState(gameCell));

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

    @Override
    public void turnMessage(GameManager manager) {
        if (!manager.isGameRunning()) return;
        Player player = manager.getCurrentPlayer();
        String messageText;

        if (player.hasCrashed()) {
            messageText = player.getName() + " has crashed.";
        } else {
            messageText = "It's " + player.getName() + ", " + manager.getID(player) + ", turn.";
        }

        // Update message label
        message.setText(messageText);
    }

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
        } catch (InterruptedException e ) {
            return;
        }
    }

    @Override
    public void gameOverMessage(GameManager manager) {
        if (manager.isGameRunning()) return;

        Player winner = manager.getWinner();
       if (winner != null) {
           message.setText("The winner is " + winner.getName() + "!");
       } else {
           message.setText("Nobody won the game.");
       }

       unpauseButton.setText("Exit Game");
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
    /*
    private void displayChange(GridPane newGridPane, Label newMessage) {
        BorderPane root = new BorderPane();
        this.gridPane = newGridPane;
        this.message = newMessage;

        root.setTop(newMessage);
        root.setCenter(newGridPane);
        root.setBottom(unpauseButton);
        Scene scene = new Scene(root, CELL_SIZE * gridPane.getMaxWidth(),
                CELL_SIZE * gridPane.getMaxHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/

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

