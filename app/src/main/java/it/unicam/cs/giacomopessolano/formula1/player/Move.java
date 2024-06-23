package it.unicam.cs.giacomopessolano.formula1.player;

public record Move(int x, int y) {

    public Move update(Choice choice) {
        return switch (choice) {
            case UP -> new Move(x, y - 1);
            case DOWN -> new Move(x, y + 1);
            case LEFT -> new Move(x - 1, y);
            case RIGHT -> new Move(x + 1, y);
            case UPLEFT -> new Move(x - 1, y - 1);
            case UPRIGHT -> new Move(x + 1, y - 1);
            case DOWNLEFT -> new Move(x - 1, y + 1);
            case DOWNRIGHT -> new Move(x + 1, y + 1);
            case CENTER -> new Move(x, y);
            default -> null;
        };
    }

}
