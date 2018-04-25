package kalah.model;

public class House extends Piece {

    private final int houseNumber;

    public House(int playerNumber, int houseNumber, int seedCount) {
        super(playerNumber, seedCount);
        this.houseNumber = houseNumber;
    }

    public boolean isEmpty() {
        return seedCount == 0;
    }

    @Override
    boolean canSow(int playerNumber) {
        return true;
    }

    @Override
    public boolean canCapture(int playerNumber, House oppositeHouse) {
        return playerNumber == this.playerNumber && seedCount == 1 && oppositeHouse.seedCount > 0;
    }

    @Override
    public int capture() {
        return removeSeeds();
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    int getPlayerNumber() {
        return playerNumber;
    }
}
