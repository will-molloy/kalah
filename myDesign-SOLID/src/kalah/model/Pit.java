package kalah.model;

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

    public abstract boolean getsRepeatTurn(int playerNumber);

    public final int seedCount() {
        return seedCount;
    }

    final boolean isEmpty() {
        return seedCount == 0;
    }

}
