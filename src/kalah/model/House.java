package kalah.model;

/**
 * Represents a House board pit.
 */
public class House extends Pit {

    private final int houseNumber;

    House(int playerNumber, int houseNumber, int seedCount) {
        super(playerNumber, seedCount);
        this.houseNumber = houseNumber;
    }

    /**
     * Any player can sow any house.
     */
    @Override
    boolean canSow(int playerNumber) {
        return true;
    }

    @Override
    public boolean getsRepeatTurn(int playerNumber) {
        return false;
    }

    @Override
    boolean canCapture(int playerNumber) {
        return playerNumber == this.playerNumber && seedCount == 1 && !oppositePit.isEmpty();
    }

    public int getHouseNumber() {
        return houseNumber;
    }

}
