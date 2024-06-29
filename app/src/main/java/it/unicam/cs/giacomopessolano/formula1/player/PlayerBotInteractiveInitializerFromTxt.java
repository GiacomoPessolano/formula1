package it.unicam.cs.giacomopessolano.formula1.player;

import it.unicam.cs.giacomopessolano.formula1.exceptions.IncorrectConfigurationException;

import java.io.IOException;

public class PlayerBotInteractiveInitializerFromTxt  extends PlayerBotInitializerFromTxt {

    InteractionHandler interactionHandler;

    public PlayerBotInteractiveInitializerFromTxt(InteractionHandler interactionHandler) {
        this.interactionHandler = interactionHandler;
    }

    @Override
    protected Strategy parseStrategy(String s) throws IOException {
        return switch (s) {
            case "DUMB" -> new StrategyDumb();
            case "INTERACTIVE" -> new StrategyInteractive(interactionHandler);
            default -> throw new IncorrectConfigurationException("The strategy " + s + " is not supported.");
        };
    }
}
