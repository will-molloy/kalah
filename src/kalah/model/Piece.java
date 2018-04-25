package kalah.model;

public abstract class Piece {

    final int playerNumber;

    int seedCount;

    private Piece nextPiece;

    Piece(int playerNumber, int seedCount) {
        this.playerNumber = playerNumber;
        this.seedCount = seedCount;
    }

    public final int addSeedsIfPlayerCan(int amount, int playerNumber) {
        if (canSow(playerNumber)) {
            seedCount += amount;
            return amount;
        } else {
            return 0;
        }
    }

    abstract boolean canSow(int playerNumber);

    abstract boolean canCapture(int playerNumber, House oppositeHouse);

    public abstract int capture();

    public int seedCount() {
        return seedCount;
    }

    public final int removeSeeds() {
        int amountRemoved = seedCount;
        seedCount = 0;
        return amountRemoved;
    }

    final void initNextPiece(Piece piece) {
        if (this.nextPiece == null) {
            this.nextPiece = piece;
        }
    }

    public final Piece next() {
        return nextPiece;
    }

}
