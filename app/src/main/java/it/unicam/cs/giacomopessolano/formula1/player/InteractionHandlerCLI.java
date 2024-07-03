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

import java.util.List;
import java.util.Scanner;

/**
 * Implementation of InteractionHandler that uses System.in as the channel for user input.
 */
public class InteractionHandlerCLI implements InteractionHandler {

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