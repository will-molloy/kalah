package kalah.model;

public abstract class Piece {

    final int playerNumber;

    int seedCount;

    Piece oppositePiece;

    private Piece nextPiece;

    Piece(int playerNumber, int seedCount) {
        this.playerNumber = playerNumber;
        this.seedCount = seedCount;
    }

    public abstract boolean canCapture(int playerNumber);

    public abstract int capture();

    final void initNextPiece(Piece piece) {
        if (this.nextPiece == null) {
            this.nextPiece = piece;
        }
    }

    final void initOppositePiece(Piece piece) {
        if (this.oppositePiece == null) {
            this.oppositePiece = piece;
        }
    }

    public final Piece next() {
        return nextPiece;
    }

    public final int addSeedsIfPlayerCan(int amount, int playerNumber) {
        if (canAddSeeds(playerNumber)) {
            seedCount += amount;
            return amount;
        } else {
            return 0;
        }
    }

    abstract boolean canAddSeeds(int playerNumber);

    public final int addSeedIfPlayerCan(int playerNumber) {
        return addSeedsIfPlayerCan(1, playerNumber);
    }

    public final int seedCount() {
        return seedCount;
    }

    final boolean isEmpty() {
        return seedCount == 0;
    }

    public final int getCountAndRemoveSeeds() {
        int amountRemoved = seedCount;
        seedCount = 0;
        return amountRemoved;
    }

}
