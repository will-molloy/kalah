package kalah;
import java.util.Scanner;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
public class MancalaGame
{
	private Player p1 , p2 ;
	private Board board ;
	boolean qQuit = false ;
	MancalaGame ( Player p1 , Player p2  , Board board )
	{
		this.p1 = p1 ;
		this.p2 = p2 ;
		this.board = board ;
	}
	boolean IsGameEnd ( boolean playerStop ,  boolean myTurn )
	{
		return playerStop && myTurn ;
	}
	public static void  CaptureAction ( Player player , Player oppositePlayer , int captureHouse)
	{
		int totalSeeds ;
		totalSeeds = player.GetSeedsOfHouse(captureHouse) + oppositePlayer.GetSeedsOfHouse(player.GetNumOfHouses()-captureHouse) ;
		player.SetClearOfHouse(captureHouse);
		oppositePlayer.SetClearOfHouse(player.GetNumOfHouses()-captureHouse);
		player.AddSeedsToStore(totalSeeds);
		return ;
	}
	void PlayerPlayGame ( Player p1 , Player p2 , IO io )
	{
		int houseNumber ;
		String suppliedInput = io.readFromKeyboard(p1.GetPlayerName()+"'s turn - Specify house number or 'q' to quit: ");
		if ( (int) suppliedInput.compareTo("q") == 0)
		{
			 qQuit = true ;
			 return ;
		}
		houseNumber = Integer.parseInt(suppliedInput) ;
		int startSeeds ;
		startSeeds = p1.GetSeedsOfHouse(houseNumber) ;
		if ( 0 == startSeeds )
		{
			io.println("House is empty. Move again.") ;
			return ;
		}
		int restSeeds ;
		restSeeds = p1.SowSeeds(houseNumber, 0, true) ;
		if ( restSeeds == -1 )
		{
			p1.SetMyTurn(true) ;
			p2.SetMyTurn(false);
		}
		else if ( restSeeds == 0 )
		{
			int lastIndex ;
			lastIndex = p1.GetLastIndex(houseNumber+1, startSeeds) ;
			if ( lastIndex != -1 )
			{
				if ( p1.GetSeedsOfHouse(lastIndex) == 1 && p2.GetSeedsOfHouse(p2.GetNumOfHouses()-lastIndex) != 0 )
				{
					CaptureAction(p1,p2,lastIndex) ;
				}
				p1.SetMyTurn(false);
				p2.SetMyTurn(true);
			}
		}
		while ( restSeeds > 0 )
		{
			restSeeds = p2.SowSeeds(0, restSeeds , false) ;
			if ( restSeeds > 0 )
			{
				startSeeds =  restSeeds ;
				restSeeds = p1.SowSeeds(0, restSeeds , true) ;
				int lastIndex ;
				lastIndex = p1.GetLastIndex(1, startSeeds) ;
				if ( lastIndex != -1 )
				{
					if ( p1.GetSeedsOfHouse(lastIndex) == 1 && p2.GetSeedsOfHouse(p2.GetNumOfHouses()-lastIndex) != 0 )
					{
						CaptureAction(p1,p2,lastIndex) ;
					}
					p1.SetMyTurn(false);
					p2.SetMyTurn(true);
					break ;
				}
				if ( restSeeds == -1 )
				{
					p1.SetMyTurn(true) ;
					p2.SetMyTurn(false);
					break ;
				}
			}
			else
			{
				p1.SetMyTurn(false);
				p2.SetMyTurn(true);
			}
		}
	}
	void StartGame(IO io)
	{
		p1.SetMyTurn(true);
		p2.SetMyTurn(false);
		while (true)
		{
			if ( qQuit == true )
			{
				break ;
			}
			if ( p1.GetMyTurn() == true )
			{
				if ( IsGameEnd ( p1.IsStop() , p1.GetMyTurn() ) == true)
				{
					board.PrintBoard ( "LAST_STEP" , p1,p2  , io ) ;
					break ;
				}
				board.PrintBoard(p1.GetPlayerName(), p1, p2 , io);
				PlayerPlayGame ( p1 , p2  , io ) ;
			}
			else if ( p2.GetMyTurn() == true)
			{
				if ( IsGameEnd ( p2.IsStop() , p2.GetMyTurn() ) == true)
				{
					board.PrintBoard ( "LAST_STEP" , p1,p2  , io ) ;
					break ;
				}
				board.PrintBoard(p2.GetPlayerName(), p1, p2,io);
				PlayerPlayGame (p2,p1 , io ) ;
			}
		}
		board.PrintBoard("GAME_OVER", p1, p2 , io);
		if ( qQuit == false )
		{
			CalculateFinalScore(p1,p2) ;
			io.println("\t" + "player 1:"+p1.GetScore());
			io.println("\t" + "player 2:"+p2.GetScore());
			if ( p1.GetScore() > p2.GetScore())
			{
				io.println("Player 1 wins!");
			}
			else if ( p1.GetScore() < p2.GetScore())
			{
				io.println("Player 2 wins!");
			}
			else
			{
				io.println("A tie!");
			}
		}
	}
	void CalculateFinalScore (Player p1 , Player p2)
	{
		for ( int i = 1 ;  i < p1.GetNumOfHouses() ; i ++ )
		{
			p1.AddSeedsToStore(p1.GetSeedsOfHouse(i));
			p2.AddSeedsToStore(p2.GetSeedsOfHouse(i));
		}
	}
}
