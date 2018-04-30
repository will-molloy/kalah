package kalah.model;

/**
 * A continuous board factory constructs a board such that pits are linked in numerical order.
 */
public class ContinuousBoardFactory extends BoardFactory {

    private final int numPlayers;

    private final int numHouses;

    public ContinuousBoardFactory(int numHouses, int numInitialSeeds, int numPlayers) {
        super(numHouses, numInitialSeeds, numPlayers);
        this.numPlayers = numPlayers;
        this.numHouses = numHouses;
    }

    /**
     * Connects each pits next pit and opposite pit.
     * - Next pit: in player order: houses -> store -> next players houses, and so on (wraps around).
     * - Opposite pit: house with number (N) -> next players house with number (num_houses + 1 - N).
     * For stores the opposite pit is set to the next players store (wraps around).
     */
    @Override
    void connectBoardPits() {
        for (int playerNumber = 1; playerNumber <= numPlayers; playerNumber++) {
            for (int houseNumber = 1; houseNumber <= numHouses; houseNumber++) {
                if (houseNumber == numHouses) {
                    board.getHouse(playerNumber, houseNumber).initNextPit(board.getStore(playerNumber));
                } else {
                    board.getHouse(playerNumber, houseNumber)
                            .initNextPit(board.getHouse(playerNumber, houseNumber + 1));
                }
                board.getHouse(playerNumber, houseNumber)
                        .initOppositePit(board.getHouse(board.nextPlayer(playerNumber), numHouses + 1 - houseNumber));
            }
            board.getStore(playerNumber).initNextPit(board.getHouse(board.nextPlayer(playerNumber), 1));
            board.getStore(playerNumber).initOppositePit(board.getStore(board.nextPlayer(playerNumber)));
        }
    }

}
