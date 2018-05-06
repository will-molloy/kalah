package kalah;
public class TurnManager {
	private int activePlayer = 1;
	public void nextPlayer()
	{
		if(activePlayer == 1)
		{
			activePlayer = 2;
		}
		else
		{
			activePlayer = 1;
		}
	}
	public int getActivePlayer()
	{
		return activePlayer;
	}
}
