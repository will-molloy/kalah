package kalah.model;

/**
 * A continuous board factory constructs a board such that pieces are linked in numerical order.
 * i.e. House 1 -> House 2 .. -> Store.
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
     * Connects each pieces next piece and opposite piece.
     * - Next piece: in player order: houses -> store -> next players houses, and so on (wraps around).
     * - Opposite piece: house with number (N) -> next players house with number (num_houses + 1 - N).
     * For stores the opposite piece is set to the next players store (wraps around).
     */
    @Override
    void connectBoardPieces() {
        for (int playerNumber = 1; playerNumber <= numPlayers; playerNumber++) {
            for (int houseNumber = 1; houseNumber <= numHouses; houseNumber++) {
                if (houseNumber == numHouses) {
                    board.getHouse(playerNumber, houseNumber).initNextPiece(board.getStore(playerNumber));
                } else {
                    board.getHouse(playerNumber, houseNumber)
                            .initNextPiece(board.getHouse(playerNumber, houseNumber + 1));
                }
                board.getHouse(playerNumber, houseNumber)
                        .initOppositePiece(board.getHouse(board.nextPlayer(playerNumber), numHouses + 1 - houseNumber));
            }
            board.getStore(playerNumber).initNextPiece(board.getHouse(board.nextPlayer(playerNumber), 1));
            board.getStore(playerNumber).initOppositePiece(board.getStore(board.nextPlayer(playerNumber)));
        }
    }

}
