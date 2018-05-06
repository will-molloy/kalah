package kalah;
public class BoardManager {
	AbstractBoard board;
	public BoardManager(int startingSeeds, int length)
	{
		board = new Board(length);
		board.seed(startingSeeds);
	}
	public int move(int activePlayer, int house) {
		int stashedSeeds = 0;
		int pitNumber = board.playerAndHouseToPit(activePlayer, house);
		if(board.isEmptyPit(pitNumber))
		{
			return -1;
		}
		stashedSeeds = board.getPit(pitNumber);
		board.setPit(pitNumber, 0);
		int incrementalPitNumber = pitNumber;
		boolean lastPitWasEmpty = false;
		while(stashedSeeds > 0)
		{
			lastPitWasEmpty = false;
			incrementalPitNumber = board.nextPit(incrementalPitNumber, activePlayer);
			if(board.getPit(incrementalPitNumber) == 0)
			{
				lastPitWasEmpty = true;
			}
			board.setPit(incrementalPitNumber, board.getPit(incrementalPitNumber) + 1);
			stashedSeeds--;
		}
		if(activePlayer == board.pitToPlayer(incrementalPitNumber) && lastPitWasEmpty)
		{
			capture(incrementalPitNumber, activePlayer);
		}
		if(incrementalPitNumber == board.playerStore(activePlayer))
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	private void capture(int pitNumber, int player) {
		if(board.getPit(board.getOppositePit(pitNumber)) == 0)
		{
			return;
		}
		int sum = board.getPit(pitNumber);
		sum += board.getPit(board.getOppositePit(pitNumber));
		board.setPit(pitNumber, 0);
		board.setPit(board.getOppositePit(pitNumber), 0);
		int store = board.length*player -1;
		board.setPit(store, board.getPit(store) + sum);
	}
	boolean isGameOver(int player)
	{
		return board.isEmptyPlayerRow(player);
	}
	public AbstractBoard getBoard()
	{
		return board;
	}
}