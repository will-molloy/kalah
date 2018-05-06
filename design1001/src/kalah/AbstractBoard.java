package kalah;
import java.util.ArrayList;
public abstract class AbstractBoard {
	ArrayList<Integer> pits = new ArrayList<>();
	public int length;
	public abstract void seed(int startingSeeds);
	public abstract int getPit(int id);
	public abstract void setPit(int id, int value);
	public abstract int nextPit(int id, int activePlayer);
	public abstract boolean isEmptyPit(int pitNumber);
	public abstract int playerStore(int player);
	public abstract int playerAndHouseToPit(int player, int house);
	public abstract int pitToPlayer(int pitId);
	public abstract int getOppositePit(int pitNumber);
	public abstract boolean isEmptyPlayerRow(int player);
}
