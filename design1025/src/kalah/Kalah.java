package kalah;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
public class Kalah
{
	public static void main(String[] args)
	{
		new Kalah().play(new MockIO());
	}
	public void play(IO io)
	{
		Player p1,p2 ;
		int houseSize ;
		houseSize = 6 ;
		House house1 = new House(houseSize+1) ;
		House house2 = new House(houseSize+1) ;
		Store store1, store2 ;
		store1 = new Store() ;
		store2 = new Store() ;
		p1 = new Player("Player P1", house1 , 4 , store1) ;
		p2 = new Player("Player P2" , house2 , 4 , store2 ) ;
		Board board = new Board() ;
		MancalaGame mancalaGame = new MancalaGame(p1,p2,board) ;
		mancalaGame.StartGame(io);
	}
}
