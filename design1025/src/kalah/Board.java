package kalah;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
public class Board
{
	void PrintBoard ( String playerName , Player p1 , Player p2 , IO  io)
	{
		if ( 0 == playerName.compareTo("GAME_OVER") )
		{
			io.println("Game over");
		}
		io.print("+----");
		for ( int i = 1 ; i < p2.GetNumOfHouses() ; i ++ )
		{
			io.print("+-------");
		}
		io.println("+----+");
		io.print("| " + "P2" + " |");
		for ( int i = p2.GetNumOfHouses() -1 ; i > 0 ; i -- )
		{
			io.print(" "+i+"["+TwoDigits(p2.GetSeedsOfHouse(i))+"]" + " |");
		}
		io.println(" "+TwoDigits(p1.GetScore()) +" |");
		io.print("|    |");
		for ( int i = 1 ; i < p2.GetNumOfHouses() ; i ++ )
		{
			if ( i != p2.GetNumOfHouses() - 1 )
			{
				io.print("-------+");
			}
			else
			{
				io.print("-------");
			}
		}
		io.println("|    |");
		io.print("| " + TwoDigits(p2.GetScore()) + " |");
		for ( int i = 1 ; i < p1.GetNumOfHouses() ; i ++ )
		{
			io.print(" "+i+"["+TwoDigits(p1.GetSeedsOfHouse(i))+"]" + " |");
		}
		io.println(" "+"P1" +" |");
		io.print("+----");
		for ( int i = 1 ; i < p2.GetNumOfHouses() ; i ++ )
		{
			io.print("+-------");
		}
		io.println("+----+");
	}
	String TwoDigits( int num)
	{
		String result ;
		result = String.format("%2d", num) ;
		return result ;
	}
}
