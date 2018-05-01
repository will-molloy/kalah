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

    int captureIfPlayerCan(int playerNumber) {
        return canCapture(playerNumber) ? getCountAndRemoveSeeds() + oppositePit.getCountAndRemoveSeeds() : 0;
    }

    abstract boolean canCapture(int playerNumber);

    final int getCountAndRemoveSeeds() {
        int amountRemoved = seedCount;
        seedCount = 0;
        return amountRemoved;
    }

    public final int seedCount() {
        return seedCount;
    }

    final boolean isEmpty() {
        return seedCount == 0;
    }

    public abstract boolean getsRepeatTurn(int playerNumber);

}
