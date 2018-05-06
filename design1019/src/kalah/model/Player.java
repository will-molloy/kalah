package kalah.model;
import java.util.LinkedHashMap;
import java.util.Map;
public class Player {
	private Player opponent = null;
	private Map<String, House> houses = null;
	private Store store = null;
	private boolean isMyTurn = false;
	private int id;
	public Player(int id, int houseNum, int beginingSeeds) {
		this.id = id;
		houses = new LinkedHashMap<String, House>();
		store = new Store(0);
		for (int i = 1; i <= houseNum; i++) {
			House house = new House(beginingSeeds);
			houses.put(""+i, house);
		}
	}
	public int getId() {
		return this.id;
	}
	public boolean getMoveFlag() {
		return isMyTurn;
	}
	public void setMoveFlag(boolean moveFlag) {
		this.isMyTurn = moveFlag;
	}
	public int getScore() {
    	int result = 0;
		for (int i = 1; i <= houses.size(); i++) {
			result = result + houses.get(""+i).getSeeds();
		}
		result = result + store.getSeeds();
		return result;
	}
	public Map<String, House> getHouses() {
		return this.houses;
	}
	public Store getStore() {
		return this.store;
	}
	public Player getOpponent() {
		return this.opponent;
	}
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
    public boolean isAllHouseEmpty() {
        for (House house : houses.values()) {
            if (house.getSeeds() != 0) {
                return false;
            }
        }
        return true;
    }
}
