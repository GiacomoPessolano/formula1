package it.unicam.cs.giacomopessolano.formula1.player;

public record Move(int x, int y) {

    Move update(Choice choice) {
        return switch (choice) {
            case UP -> new Move(x - 1, y);
            case DOWN -> new Move(x + 1, y);
            case LEFT -> new Move(x, y - 1);
            case RIGHT -> new Move(x, y + 1);
            case UPLEFT -> new Move(x - 1, y - 1);
            case UPRIGHT -> new Move(x + 1, y - 1);
            case DOWNLEFT -> new Move(x - 1, y + 1);
            case DOWNRIGHT -> new Move(x + 1, y + 1);
            case CENTER -> new Move(x, y);
            default -> null;
        };
    }

}
