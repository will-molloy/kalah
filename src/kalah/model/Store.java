package kalah.model;

public class Store extends Piece {

    Store(int playerNumber, int seedCount) {
        super(playerNumber, seedCount);
    }

    @Override
    boolean canAddSeeds(int playerNumber) {
        return playerNumber == this.playerNumber;
    }

    @Override
    public boolean canCapture(int playerNumber) {
        return false;
    }

    @Override
    public int capture() {
        return 0;
    }
}
