package myPackage;

import java.util.ArrayList;

public class Player {
    private final int playerNumber;
    private ArrayList<House> houses;
    private Store store;
    private IRule finishInEmptyRule;

    public Player(int playerNumberArg) {
        playerNumber = playerNumberArg;
        houses = new ArrayList<House>();
        store = new Store();
        finishInEmptyRule = new RuleWillEndAtEmptyHouse();
        initialize();
    }

    public ArrayList<House> getHouses() {
        return houses;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Store getStore() {
        return store;
    }

    public void initialize() {
        initializeHouses();
        initializeStore();
    }

    private void initializeHouses() {
        houses = new ArrayList<House>();
        for (int i = 1; i <= Variables.numberOfHousesAllowed; i++) {
            houses.add(new House(i));
        }
    }

    private void initializeStore() {
        store.initializeSeeds();
    }

    public int totalSeedCount() {
        int toReturn = 0;
        for (House house : houses) {
            toReturn += house.seedCount();
        }
        toReturn += store.seedCount();
        return toReturn;
    }

    public void addASeedAt(int houseNumber) {
        getHouse(houseNumber).addSeed(new Seed());
    }

    public boolean isHouseEmpty(int houseNumber) {
        return getHouse(houseNumber).isEmpty();
    }

    public SeedOverFlowAndHouseNumber takeTurnAndGiveExcessSeeds(int houseNumber) {
        House currentHouse = getHouse(houseNumber);
        int housesPlaceInList = getHousePlaceInList(houseNumber);
        int seedsInHand = currentHouse.seedCount();
        currentHouse.removeAllSeeds();
        House houseToreturn;
        for (int i = housesPlaceInList + 1; i < houses.size(); i++) {
            if (seedsInHand > 0) {
                if (finishInEmptyRule.apply(houses.get(i), seedsInHand, null)) {
                    houseToreturn = houses.get(i);
                    return new SeedOverFlowAndHouseNumber(-1, houseToreturn.getIdentifierNumber());
                }
                houses.get(i).addSeed(new Seed());
                seedsInHand--;
            }
        }
        if (seedsInHand > 0) {
            seedsInHand = fillStoreAndReturnSeeds(seedsInHand);
            if (seedsInHand < 1) {
                return new SeedOverFlowAndHouseNumber(-1, -1);
            }
        }
        return new SeedOverFlowAndHouseNumber(seedsInHand, -1);
    }

    public SeedOverFlowAndHouseNumber addSeedsFromOtherPlayerPT(int seedCount) {
        int firstHouse = getHousePlaceInList(getHouse(1).getIdentifierNumber());
        House houseToreturn;
        for (int i = firstHouse; i < houses.size(); i++) {
            if (seedCount > 0) {
                if (finishInEmptyRule.apply(houses.get(i), seedCount, null)) {
                    houseToreturn = houses.get(i);
                    return new SeedOverFlowAndHouseNumber(-1, houseToreturn.getIdentifierNumber());
                }
                houses.get(i).addSeed(new Seed());
                seedCount--;
            }
        }
        if (seedCount > 0) {
            seedCount = fillStoreAndReturnSeeds(seedCount);
            if (seedCount < 1) {
                return new SeedOverFlowAndHouseNumber(-1, -1);
            }
        }
        return new SeedOverFlowAndHouseNumber(seedCount, -1);
    }

    public int addSeedsFromOtherPlayer(int seedCount) {
        int currentSeedCount = seedCount;
        for (int i = 0; i < houses.size(); i++) {
            if (currentSeedCount > 0) {
                houses.get(i).addSeed(new Seed());
                currentSeedCount--;
            }
        }
        return currentSeedCount;
    }

    private int fillStoreAndReturnSeeds(int seedCount) {
        store.addSeed(new Seed());
        seedCount--;
        return seedCount;
    }

    public int giveAllSeedsFromThisHouse(int houseNumber) {
        House h = getHouse(houseNumber);
        int toReturn = h.seedCount();
        h.removeAllSeeds();
        return toReturn;
    }

    public House getHouse(int houseNumber) {
        for (House house : houses) {
            if (house.getIdentifierNumber() == houseNumber) {
                return house;
            }
        }
        return null;
    }

    @SuppressWarnings("null")
    private int getHousePlaceInList(int houseNumber) {
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getIdentifierNumber() == houseNumber) {
                return i;
            }
        }
        return (Integer) null;
    }

    public boolean areHousesEmpty() {
        for (House house : houses) {
            if (!house.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
