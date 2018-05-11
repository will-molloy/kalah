package kalah.model;

/**
 * Represents a Store board pit.
 */
public class Store extends Pit {

    Store(int playerNumber, int seedCount) {
        super(playerNumber, seedCount);
    }

    /**
     * A store pit can only be sowed the by player that owns it.
     */
    @Override
    boolean canSow(int playerNumber) {
        return playerNumber == this.playerNumber;
    }

    @Override
    public boolean getsRepeatTurn(int playerNumber) {
        return playerNumber == this.playerNumber;
    }

    /**
     * A store pit cannot be captured.
     */
    @Override
    boolean canCapture(int playerNumber) {
        return false;
    }

}
