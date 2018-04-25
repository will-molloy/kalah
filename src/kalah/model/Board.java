package kalah.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final int numPlayers;
    private final int numHouses;
    private Map<Integer, Map<Integer, House>> housesMap;
    private Map<Integer, Store> storeMap;

    public Board(int numHouses, int numInitialSeeds, int numPlayers) {
        this.numPlayers = numPlayers;
        this.numHouses = numHouses;
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
     * Connects the pieces in a circular linked list.
     * In player order: houses -> store -> next players houses, and so on.
     * The final players store will connect to the first players first house.
     */
    private void connectPieces() {
        for (int i = 1; i <= storeMap.size(); i++) {
            Map<Integer, House> houses = housesMap.get(i);
            for (int j = 1; j < houses.size(); j++) {
                houses.get(j).initNextPiece(houses.get(j + 1));
            }
            houses.get(houses.size()).initNextPiece(storeMap.get(i));
            storeMap.get(i).initNextPiece(housesMap.get((i % storeMap.size() + 1)).get(1));
        }
    }

    public House getOppositeHouse(House house) {
        Map<Integer, House> nextPlayersHouses = housesMap.get(house.getPlayerNumber() % numPlayers() + 1);
        return nextPlayersHouses.get(numHouses() + 1 - house.getHouseNumber());
    }

    public House getHouse(int houseNumber, int playerNumber) {
        return housesMap.get(playerNumber).get(houseNumber);
    }

    public int numPlayers() {
        return numPlayers;
    }

    public int numHouses() {
        return numHouses;
    }

    public List<House> getHousesFor(int playerNumber) {
        Map<Integer, House> playersHouses = housesMap.get(playerNumber);
        List<House> houses = new ArrayList<>();
        for (int i = 1; i <= playersHouses.size(); i++) {
            houses.add(playersHouses.get(i));
        }
        return houses;
    }

    public Store getStoreFor(int playerNumber) {
        return storeMap.get(playerNumber);
    }

    public boolean houseIsEmpty(int houseNumber, int playerNumber) {
        return getHouse(houseNumber, playerNumber).isEmpty();
    }

    public boolean housesAreEmpty(int playerNumber) {
        for (House h : getHousesFor(playerNumber)){
            if (!h.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
