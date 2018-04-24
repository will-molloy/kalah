package kalah.model;

import kalah.model.errors.IllegalMoveException;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final int playerNumber;

    private final List<House> houses;

    private final Store store;

    public Player(int playerNumber, int numHouses, int initialNumSeeds) {
        this.playerNumber = playerNumber;
        houses = new ArrayList<>();
        for (int i = 1; i <= numHouses; i++) {
            houses.add(new House(i, initialNumSeeds));
        }
        store = new Store();
    }

    public House getHouse(int houseNumber) throws IllegalMoveException {
        for (House h : houses) {
            if (h.number() == houseNumber) {
                return h;
            }
        }
        throw new IllegalMoveException(String.format("Invalid house number: %d", houseNumber));
    }

    public Plantable getNext(Plantable plantable) {
        if (plantable instanceof Store){
            return getHouse(1);
        } else {
            House house = (House)plantable;
            int nextNumber = house.number() + 1;
            if (nextNumber > houses.size()){
                return store;
            }
            return getHouse(nextNumber);
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public List<House> getHouses() {
        return houses;
    }

    public Store getStore() {
        return store;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerNumber=" + playerNumber +
                ", houses=" + houses +
                ", store=" + store +
                '}';
    }

    public House getOppositeHouse(House house) {
        return getHouse(houses.size() - house.number() + 1);
    }

    public void capture(House house, House oppositeHouse) {
        store.addSeeds(house.seedCount() + oppositeHouse.seedCount());
        house.removeSeeds(); // would prefer it starts on 0...
        oppositeHouse.removeSeeds();
    }

    public boolean housesAreEmpty() {
        for (House h : houses){
            if (h.seedCount() > 0){
                return false;
            }
        }
        return true;
    }

    public int seedCount() {
        int count = 0;
        for (House h : houses){
            count += h.seedCount();
        }
        count += store.seedCount();
        return count;
    }
}
