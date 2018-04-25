package kalah.model;

public class House extends Piece {

    private final int houseNumber;

    House(int playerNumber, int houseNumber, int seedCount) {
        super(playerNumber, seedCount);
        this.houseNumber = houseNumber;
    }

    @Override
    boolean canAddSeeds(int playerNumber) {
        return true;
    }

    @Override
    public boolean canCapture(int playerNumber) {
        return playerNumber == this.playerNumber && seedCount == 1 && !oppositePiece.isEmpty();
    }

    @Override
    public int capture() {
        return getCountAndRemoveSeeds() + oppositePiece.getCountAndRemoveSeeds();
    }

    public int getHouseNumber() {
        return houseNumber;
    }

}
