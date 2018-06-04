package kalah.model;

public class Store extends Pit {

    Store(int playerNumber, int seedCount) {
        super(playerNumber, seedCount);
    }

    @Override
    boolean canSow(int playerNumber) {
        return playerNumber == this.playerNumber;
    }

    @Override
    public boolean getsRepeatTurn(int playerNumber) {
        return playerNumber == this.playerNumber;
    }

    @Override
    boolean canCapture(int playerNumber) {
        return false;
    }

}
