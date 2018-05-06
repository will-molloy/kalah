package kalah.model;
import java.util.ArrayList;
import java.util.Map;
import kalah.KalahConstants;
import kalah.setting.GameSetting;
public class DefaultModel implements Model {
	private ArrayList<Player> players;
	private int playerNum = 2;
	public DefaultModel () {
		initPlayers();
	}
	private void initPlayers() {
		this.players = new ArrayList<Player>();
		for (int i = 0; i < playerNum; i++) {
			players.add(new Player(i+1, GameSetting.houseNum, GameSetting.beginingSeeds));
		}
		players.get(GameSetting.beginingPlayer-1).setMoveFlag(true);
    	setOpposite(this.players.get(0), this.players.get(1), GameSetting.houseNum);
    	setOpposite(this.players.get(1), this.players.get(0), GameSetting.houseNum);
	}
    private void setOpposite(Player p1, Player p2, int houseNum) {
    	if (p1.getOpponent() == null) {
			p1.setOpponent(p2);
		}
    	 Map<String, House> houses = p1.getHouses();
 		for (String key : houses.keySet()) {
			House house = houses.get(key);
			int i = Integer.parseInt(key);
			if (house.getOppositeHouse() == null) {
				house.setOppositeHouse(p2.getHouses().get(""+(houseNum-i+1)));
			}
		}
    }
	@Override
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	@Override
	public Player getCurrentPlayer() {
		for (Player p : players) {
			if (p.getMoveFlag()) {
				return p;
			}
		}
		return null;
	}
	@Override
	public int getWinner() {
        int score = 0;
        int winningPlayer = KalahConstants.TIE;
        for (Player player : players) {
            int playerScore = player.getScore();
            if (playerScore > score) {
                score = playerScore;
                winningPlayer = player.getId();
            } else if (playerScore == score) {
                winningPlayer = KalahConstants.TIE;
            }
        }
        return winningPlayer;
	}
}
