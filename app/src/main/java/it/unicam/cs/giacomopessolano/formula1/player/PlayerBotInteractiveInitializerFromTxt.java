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

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;

import java.io.IOException;

/**
 * Extension of PlayerBotInitializerFromTxt that performs the same actions but supports INTERACTIVE players.
 * Interactive players need an interaction handler.
 */
public class PlayerBotInteractiveInitializerFromTxt  extends PlayerBotInitializerFromTxt {

    /**
     * Defines the channel for user input.
     */
    InteractionHandler interactionHandler;

    /**
     * Constructs a PlayerBotInteractiveInitializerFromTxt with an interaction handler.
     *
     * @param interactionHandler Interaction handler for interactive players.
     */
    public PlayerBotInteractiveInitializerFromTxt(InteractionHandler interactionHandler) {
        this.interactionHandler = interactionHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Strategy parseStrategy(String s) throws IOException {
        return switch (s) {
            case "DUMB" -> new StrategyDumb();
            case "INTERACTIVE" -> new StrategyInteractive(interactionHandler);
            default -> throw new IncorrectConfigurationException("The strategy " + s + " is not supported.");
        };
    }
}
