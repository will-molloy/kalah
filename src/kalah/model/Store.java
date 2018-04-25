package kalah.model;

public class Store extends Piece {

    public Store(int playerNumber, int seedCount) {
        super(playerNumber, seedCount);
    }

    @Override
    boolean canSow(int playerNumber) {
        return playerNumber == this.playerNumber;
    }

    @Override
    public boolean canCapture(int playerNumber, House oppositeHouse) {
        return false;
    }

    @Override
    public int capture() {
        return 0;
    }
}
