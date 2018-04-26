package kalah.model;

/**
 * Represents a Store board piece.
 */
public class Store extends Piece {

    Store(int playerNumber, int seedCount) {
        super(playerNumber, seedCount);
    }

    /**
     * A store piece can only be sowed the by player that owns it.
     */
    @Override
    boolean canSow(int playerNumber) {
        return playerNumber == this.playerNumber;
    }

    /**
     * A store piece cannot be captured.
     */
    @Override
    public boolean canCapture(int playerNumber) {
        return false;
    }

    @Override
    public int capture() {
        return 0;
    }
}
