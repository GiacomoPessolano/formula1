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

package it.unicam.cs.giacomopessolano.formula1.player;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of InteractionHandler that uses JavaFX's TextInputDialog as the channel for user input.
 */
public class InteractionHandlerJavaFX implements InteractionHandler {

    /**
     * Lets the player choose out of a list of possible moves. If the input is incorrect, it cycles
     * until it is acceptable.
     *
     * @param possibleDirections Possible choices of movement.
     * @return Direction chosen.
     */
    @Override
    public Direction takeInput(List<Direction> possibleDirections) {
        assert possibleDirections != null;
        assert !possibleDirections.isEmpty();

        while (true) {
            try {
                String directions = listOfDirections(possibleDirections);
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Choose Direction");
                dialog.setHeaderText("Choose a direction. Here is your choices: " + directions);
                dialog.setContentText("Enter a direction: ");
                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    String input = result.get().trim().toUpperCase();
                    Direction direction = Direction.valueOf(input);

                    if (possibleDirections.contains(direction)) {
                        return direction;
                    } else {
                        throw new IllegalArgumentException("Invalid direction entered.");
                    }
                } else {
                    throw new IllegalArgumentException("No direction entered.");
                }
            } catch (IllegalArgumentException e) {
                impossibleDirectionAlert();
            }
        }
    }

    /**
     * Translates the list of directions into a String.
     *
     * @param possibleDirections Possible choices of movement.
     * @return String containing each possible movement choice.
     */
    private String listOfDirections(List<Direction> possibleDirections) {
        StringBuilder result = new StringBuilder();
        for (Direction direction : possibleDirections) {
            result.append(direction.toString()).append("; ");
        }
        return result.toString();
    }

    /**
     * Sends an alert if the input given by the player is incorrect.
     */
    private void impossibleDirectionAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Direction");
        alert.setHeaderText("Invalid direction entered");
        alert.setContentText("Please enter a valid direction from the given choices.");
        alert.showAndWait();
    }
}
