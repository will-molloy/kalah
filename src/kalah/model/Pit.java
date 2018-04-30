package kalah.model;

/**
 * Represents a board pit. Pits can be sowed; hence a pit is stateful of their seed count. Pits are connected
 * on a board; hence pits are stateful of their next and opposite pits which can only be set once.
 */
public abstract class Pit {

    final int playerNumber;

    int seedCount;

    Pit oppositePit;

    private Pit nextPit;

    Pit(int playerNumber, int seedCount) {
        this.playerNumber = playerNumber;
        this.seedCount = seedCount;
    }

    abstract boolean canCapture(int playerNumber);

    abstract int capture();

    final void initNextPit(Pit pit) {
        if (this.nextPit == null) {
            this.nextPit = pit;
        }
    }

    final void initOppositePit(Pit pit) {
        if (this.oppositePit == null) {
            this.oppositePit = pit;
        }
    }

    final Pit next() {
        return nextPit;
    }

    /**
     * Template method which first checks this pit can be sowed by the player before proceeding.
     */
    final int sowSeedsIfPlayerCan(int amount, int playerNumber) {
        if (canSow(playerNumber)) {
            seedCount += amount;
            return amount;
        } else {
            return 0;
        }
    }

    abstract boolean canSow(int playerNumber);

    final int sowSeedIfPlayerCan(int playerNumber) {
        return sowSeedsIfPlayerCan(1, playerNumber);
    }

    public final int seedCount() {
        return seedCount;
    }

    final boolean isEmpty() {
        return seedCount == 0;
    }

    final int getCountAndRemoveSeeds() {
        int amountRemoved = seedCount;
        seedCount = 0;
        return amountRemoved;
    }

    public abstract boolean getsRepeatTurn(int playerNumber);
}
