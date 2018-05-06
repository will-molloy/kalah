package kalah.model;
import java.util.ArrayList;
public interface Model {
	ArrayList<Player> getPlayers();
	Player getCurrentPlayer();
	int getWinner();
}
