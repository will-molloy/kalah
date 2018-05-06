package kalah;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		BoardManager bm = new BoardManager(4,7);
		TurnManager tm = new TurnManager();
		IOManager iom = new IOManager(io);
		int returnedPlayerChange;
		while(true)
		{
			iom.displayBoard(bm.getBoard());
			if(bm.isGameOver(tm.getActivePlayer()))
			{
				iom.displayGameOver(bm.getBoard());
				break;
			}
			int result = iom.promptAction(tm.getActivePlayer(), bm.board.length);
			if(result == -1)
			{
				iom.displayQuit(bm.getBoard());
				break;
			}
			returnedPlayerChange = bm.move(tm.getActivePlayer(), result);
			if(returnedPlayerChange == 1)
			{
				tm.nextPlayer();
			}
			else if(returnedPlayerChange == -1)
			{
				iom.displayEmptyHouse();
			}
		}
	}
}