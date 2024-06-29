package it.unicam.cs.giacomopessolano.formula1.player;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.List;
import java.util.Optional;

public class InteractionHandlerJavaFX implements InteractionHandler {

    @Override
    public Direction takeInput(List<Direction> possibleDirections) {
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

    private String listOfDirections(List<Direction> possibleDirections) {
        StringBuilder result = new StringBuilder();
        for (Direction direction : possibleDirections) {
            result.append(direction.toString()).append("; ");
        }
        return result.toString();
    }

    private void impossibleDirectionAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Direction");
        alert.setHeaderText("Invalid direction entered");
        alert.setContentText("Please enter a valid direction from the given choices.");
        alert.showAndWait();
    }
}
