package it.unicam.cs.giacomopessolano.formula1.player;

import java.util.List;
import java.util.Scanner;

public class InteractionHandlerCLI implements InteractionHandler {

    @Override
    public Direction takeInput(List<Direction> possibleDirections) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Possible directions: " + possibleDirections);
        String input = "";

        while (true) {
            try {
                input = scanner.nextLine().toUpperCase();
                Direction direction = Direction.valueOf(input);
                if (possibleDirections.contains(direction)) {
                    return direction;
                } else {
                    System.out.println("The direction " + input + " is not possible. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + input + ". Try again.");
            }
        }
    }
}