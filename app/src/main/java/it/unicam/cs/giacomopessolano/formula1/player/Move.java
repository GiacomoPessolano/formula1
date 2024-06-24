package it.unicam.cs.giacomopessolano.formula1.player;

public record Move(int x, int y) {

    public Move update(Direction choice) {
        return new Move(x + choice.x(),y + choice.y());
    }

}
