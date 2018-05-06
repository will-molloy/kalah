package kalah;
import com.qualitascorpus.testsupport.IO;
public class IOManager {
	IO io;
	public IOManager(IO io)
	{
		this.io = io;
	}
	public int promptAction(int playerNum, int length)
	{
		int houseID = io.readInteger("Player P" + playerNum + "'s turn - Specify house number or 'q' to quit: ", 1, (length - 1), -1, "q");
		return houseID;
	}
	void displayGameOver(AbstractBoard abstractBoard)
	{
		io.println("Game over");
		printBoard(abstractBoard);
		printResult(abstractBoard);
	}
	void displayBoard(AbstractBoard abstractBoard)
	{
		printBoard(abstractBoard);
	}
	void displayQuit(AbstractBoard abstractBoard)
	{
		io.println("Game over");
		printBoard(abstractBoard);
	}
	private void printResult(AbstractBoard abstractBoard)
	{
		int player1Sum = 0;
		for(int i = 0; i < abstractBoard.length; i ++)
		{
			player1Sum += abstractBoard.getPit(i);
		}
		int player2Sum = 0;
		for(int i = abstractBoard.length; i < (abstractBoard.length*2); i ++)
		{
			player2Sum += abstractBoard.getPit(i);
		}
		io.println("\tplayer 1:" + player1Sum);
		io.println("\tplayer 2:" + player2Sum);
		if(player2Sum == player1Sum)
		{
			io.println("A tie!");
		}
		else
		{
			int winner = (player2Sum > player1Sum) ? 2 : 1;
			io.println("Player " + winner + " wins!");
		}
	}
	private void printBoard(AbstractBoard abstractBoard)
	{
		String seperatorTopBottom = "+----+";
		String seperatorMiddle = "|    |";
		for(int i = 0; i < abstractBoard.length - 1; i++)
		{
			seperatorTopBottom += "-------+";
			seperatorMiddle += "-------+";
		}
		seperatorTopBottom += "----+";
		seperatorMiddle = seperatorMiddle.substring(0, seperatorMiddle.length() - 1);
		seperatorMiddle += "|    |";
		String p2Line = "| " + String.format("%2s", abstractBoard.getPit(abstractBoard.length - 1)) + " |";
		for(int i = abstractBoard.length; i<abstractBoard.length*2 -1; i++)
		{
			p2Line = "| " + (i - abstractBoard.length + 1) + "[" + String.format("%2s", abstractBoard.getPit(i)) + "] " + p2Line;
		}
		p2Line = "| P2 " + p2Line;
		String p1Line = "| " + String.format("%2s", abstractBoard.getPit(abstractBoard.length * 2 - 1));
		for(int i = 0; i<abstractBoard.length - 1; i++)
		{
			p1Line = p1Line + " | " + (i + 1) + "[" + String.format("%2s", abstractBoard.getPit(i)) + "]";
		}
		p1Line = p1Line + " | P1 |";
		io.println(seperatorTopBottom);
		io.println(p2Line);
		io.println(seperatorMiddle);
		io.println(p1Line);
		io.println(seperatorTopBottom);
	}
	public void displayEmptyHouse() {
		io.println("House is empty. Move again.");
	}
}
