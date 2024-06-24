package it.unicam.cs.giacomopessolano.formula1.player;

public record Move(int x, int y) implements Cloneable{

    public Move update(Direction choice) {
        return new Move(x + choice.x(),y + choice.y());
    }

    @Override
    public Move clone() {
        try {
            return (Move) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
