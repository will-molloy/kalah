package kalah.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Kalah board.
 */
public class Board {

    private final int numHouses;

    private final int numPlayers;

    // Player Number -> House Number -> House
    private final Map<Integer, Map<Integer, House>> housesMap;

    // Player Number -> Store
    private final Map<Integer, Store> storeMap;

    /**
     * Initialises and stores board pieces into maps for efficient look ups and connects the pieces by initialising
     * their next and opposite pieces.
     */
    public Board(int numHouses, int numInitialSeeds, int numPlayers) {
        this.numHouses = numHouses;
        this.numPlayers = numPlayers;
        housesMap = new HashMap<>();
        storeMap = new HashMap<>();
        for (int playerNum = 1; playerNum <= numPlayers; playerNum++) {
            Map<Integer, House> houses = new HashMap<>();
            for (int houseNum = 1; houseNum <= numHouses; houseNum++) {
                houses.put(houseNum, new House(playerNum, houseNum, numInitialSeeds));
            }
            this.housesMap.put(playerNum, houses);
            this.storeMap.put(playerNum, new Store(playerNum, 0));
        }
        connectPieces();
    }

    /**
     * Connects each pieces next piece and opposite piece.
     * - Next piece: in player order: houses -> store -> next players houses, and so on (wraps around).
     * - Opposite piece: house with number (N) -> next players house with number (num_houses + 1 - N).
     * For stores the opposite piece is set to the next players store (wraps around).
     */
    private void connectPieces() {
        for (int playerNumber = 1; playerNumber <= numPlayers; playerNumber++) {
            for (int houseNumber = 1; houseNumber <= numHouses; houseNumber++) {
                if (houseNumber == numHouses) {
                    getHouse(playerNumber, houseNumber).initNextPiece(getStore(playerNumber));
                } else {
                    getHouse(playerNumber, houseNumber)
                            .initNextPiece(getHouse(playerNumber, houseNumber + 1));
                }
                getHouse(playerNumber, houseNumber)
                        .initOppositePiece(getHouse(nextPlayer(playerNumber), numHouses + 1 - houseNumber));
            }
            getStore(playerNumber).initNextPiece(getHouse(nextPlayer(playerNumber), 1));
            getStore(playerNumber).initOppositePiece(getStore(nextPlayer(playerNumber)));
        }
    }

    private House getHouse(int playerNumber, int houseNumber){
        return housesMap.get(playerNumber).get(houseNumber);
    }

    public Store getStore(int playerNumber) {
        return storeMap.get(playerNumber);
    }

    /**
     * Sows the board starting from the given piece; this consists of removing seeds from the starting piece and adding
     * seeds one by one to the next pieces. The last piece sowed is captured if possible. Returns the last piece sowed.
     */
    public Piece sow(int houseNumber, int playerNumber) {
        Piece piece = getHouse(playerNumber, houseNumber);
        int seedsToSow = piece.getCountAndRemoveSeeds();
        while (seedsToSow > 0) {
            piece = piece.next();
            seedsToSow -= piece.sowSeedIfPlayerCan(playerNumber);
        }
        if (piece.canCapture(playerNumber)) {
            int seedsCaptured = piece.capture();
            getStore(playerNumber).sowSeedsIfPlayerCan(seedsCaptured, playerNumber);
        }
        return piece;
    }

    public int nextPlayer(int playerNumber) {
        return playerNumber % numPlayers + 1;
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
        return getHouse(playerNumber, houseNumber).isEmpty();
    }

    public boolean housesAreEmpty(int playerNumber) {
        for (House h : getHouses(playerNumber)) {
            if (!h.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
