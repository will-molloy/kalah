package kalah.model;

import static kalah.util.MathUtil.incrementAndWrap;

public class ContinuousBoard extends Board {

    public ContinuousBoard(int numHouses, int numInitialSeeds, int numPlayers) {
        super(numHouses, numInitialSeeds, numPlayers);
    }

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
                        .initOppositePit(getHouse(incrementAndWrap(playerNumber, numPlayers), numHouses + 1 - houseNumber));
            }
            getStore(playerNumber).initNextPit(getHouse(incrementAndWrap(playerNumber, numPlayers), 1));
            getStore(playerNumber).initOppositePit(getStore(incrementAndWrap(playerNumber, numPlayers)));
        }
    }

}
