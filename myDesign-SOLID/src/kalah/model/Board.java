package kalah.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Board {

    final int numPlayers;

    final int numHouses;

    // Player Number -> House Number -> House
    private final Map<Integer, Map<Integer, House>> housesMap;

    // Player Number -> Store
    private final Map<Integer, Store> storeMap;

    Board(int numHouses, int numInitialSeeds, int numPlayers) {
        if (numHouses < 1 || numInitialSeeds < 1 || numPlayers < 2) {
            throw new IllegalArgumentException(String.format(("Not enough houses (%d), seeds (%d), or players (%d)."),
                    numHouses, numInitialSeeds, numPlayers));
        }
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
        connectBoardPits();
    }

    abstract void connectBoardPits();

    public Pit sow(int houseNumber, int playerNumber) {
        Pit pit = getHouse(playerNumber, houseNumber);
        int seedsToSow = pit.getCountAndRemoveSeeds();
        while (seedsToSow > 0) {
            pit = pit.next();
            seedsToSow -= pit.sowSeedIfPlayerCan(playerNumber);
        }
        int seedsCaptured = pit.captureIfPlayerCan(playerNumber);
        getStore(playerNumber).sowSeedsIfPlayerCan(seedsCaptured, playerNumber);
        return pit;
    }

    House getHouse(int playerNumber, int houseNumber) {
        if (!housesMap.containsKey(playerNumber) || !housesMap.get(playerNumber).containsKey(houseNumber)) {
            throw new IllegalArgumentException(String.format("House doesn't exist: player (%d), house (%d).", playerNumber,
                    houseNumber));
        }
        return housesMap.get(playerNumber).get(houseNumber);
    }

    public Store getStore(int playerNumber) {
        if (!storeMap.containsKey(playerNumber)) {
            throw new IllegalArgumentException(String.format("Store doesn't exist: player (%d).", playerNumber));
        }
        return storeMap.get(playerNumber);
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

    public List<House> getHouses(int playerNumber) {
        List<House> houses = new ArrayList<>();
        for (int i = 1; i <= numHouses; i++) {
            houses.add(getHouse(playerNumber, i));
        }
        return houses;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

}
