package kalah.model;

/**
 * A continuous board connects its pits in numerical order.
 */
public class ContinuousBoard extends Board {

    public ContinuousBoard(int numHouses, int numInitialSeeds, int numPlayers) {
        super(numHouses, numInitialSeeds, numPlayers);
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
                    getHouse(playerNumber, houseNumber).initNextPit(getStore(playerNumber));
                } else {
                    getHouse(playerNumber, houseNumber)
                            .initNextPit(getHouse(playerNumber, houseNumber + 1));
                }
                getHouse(playerNumber, houseNumber)
                        .initOppositePit(getHouse(nextPlayer(playerNumber), numHouses + 1 - houseNumber));
            }
            getStore(playerNumber).initNextPit(getHouse(nextPlayer(playerNumber), 1));
            getStore(playerNumber).initOppositePit(getStore(nextPlayer(playerNumber)));
        }
    }

}
