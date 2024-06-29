package it.unicam.cs.giacomopessolano.formula1.player;

import java.util.List;

@FunctionalInterface
public interface InteractionHandler {

    Direction takeInput(List<Direction> choices);

}
