package kalah.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final int numHouses;

    private final int numInitialSeeds;

    private final int numPlayers;

    // Player Number -> House Number -> House
    private Map<Integer, Map<Integer, House>> housesMap;

    // Player Number -> Store
    private Map<Integer, Store> storeMap;

    public Board(int numHouses, int numInitialSeeds, int numPlayers) {
        this.numHouses = numHouses;
        this.numInitialSeeds = numInitialSeeds;
        this.numPlayers = numPlayers;
        housesMap = new HashMap<>();
        storeMap = new HashMap<>();
        for (int i = 1; i <= numPlayers; i++) {
            Map<Integer, House> houses = new HashMap<>();
            for (int j = 1; j <= numHouses; j++) {
                houses.put(j, new House(i, j, numInitialSeeds));
            }
            this.housesMap.put(i, houses);
            this.storeMap.put(i, new Store(i, 0));
        }
        connectPieces();
    }

    /**
     * Connects each pieces next piece and opposite piece.
     * Next piece: in player order: houses -> store -> next players houses, and so on (wraps around).
     * Opposite piece: house with number N -> next players house with number (num_houses + 1 - N).
     * For stores the opposite piece is set to the next store (wraps around).
     */
    private void connectPieces() {
        for (int playerNum = 1; playerNum <= numPlayers; playerNum++) {
            Map<Integer, House> houses = housesMap.get(playerNum);
            for (int houseNum = 1; houseNum <= numHouses; houseNum++) {
                if (houseNum == numHouses) {
                    houses.get(numHouses).initNextPiece(getStore(playerNum));
                } else {
                    houses.get(houseNum).initNextPiece(getHouse(houseNum + 1, playerNum));
                }
                houses.get(houseNum).initOppositePiece(getHouse(numHouses + 1 - houseNum, nextPlayer(playerNum)));
            }
            storeMap.get(playerNum).initNextPiece(getHouse(1, nextPlayer(playerNum)));
            storeMap.get(playerNum).initOppositePiece(getStore(nextPlayer(playerNum)));
        }
    }

    public House getHouse(int houseNumber, int playerNumber) {
        return housesMap.get(playerNumber).get(houseNumber);
    }

    public Store getStore(int playerNumber) {
        return storeMap.get(playerNumber);
    }

    private int nextPlayer(int playerNum) {
        return playerNum % numPlayers + 1;
    }

    public List<House> getHouses(int playerNumber) {
        Map<Integer, House> playersHouses = housesMap.get(playerNumber);
        List<House> houses = new ArrayList<>();
        for (int i = 1; i <= playersHouses.size(); i++) {
            houses.add(playersHouses.get(i));
        }
        return houses;
    }

    public boolean houseIsEmpty(int houseNumber, int playerNumber) {
        return getHouse(houseNumber, playerNumber).isEmpty();
    }

    public boolean housesAreEmpty(int playerNumber) {
        for (House h : getHouses(playerNumber)) {
            if (!h.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int numSeeds() {
        return numPlayers * numHouses * numInitialSeeds;
    }

    public int maxHouseNumber() {
        return numHouses;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}
