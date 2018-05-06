package kalah;
import java.io.IOException;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
public class Kalah {
    private int  currentPlayer  =  0;
    private KalahBoard  board;
    private Player  []  players;
    private IO io;
    public Kalah()  {
        board  =  new KalahBoard();
        board.setUpForPlay();
        players  =  new Player[2];
        players[0]  =  new Player("P1",  0);
        players[1]  =  new Player("P2",  1);
        currentPlayer  =  0;
    }
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		this.io=io;
		displayBoard();
		boolean gameQuit=false;
		boolean gameOver=false;
	        while  (!gameQuit&&!gameOver)  {
	           int HouseNum;
			try {
				HouseNum = players[currentPlayer].selectAMove(board,io);
				KalahBoard.NextStep  nextStep  =  board.doTheMove(currentPlayer, HouseNum);
					switch(nextStep){
					case GoAgain:
						displayBoard();
						break;
					case ZeroSeedInHouseGoAgain:
						io.println("House is empty. Move again.");
						displayBoard();
						break;
					case SwitchPlayer:
						if  (currentPlayer  ==  0)
							currentPlayer  =  1;
						else
							currentPlayer  =  0;
						displayBoard();
						break;
					case Quit:
						gameQuit=true;
						io.println("Game over");
						displayBoard();
						break;
					case GameOver:
						gameOver=true;
						io.println("Game over");
						displayBoard();
						board.emptyStonesIntoMancalas();
						io.println("\tplayer 1:"+board.stonesInMancala(0));
						io.println("\tplayer 2:"+board.stonesInMancala(1));
						if  (board.stonesInMancala(0)  > board.stonesInMancala(1))
							io.println("Player 1 wins!");
						else if (board.stonesInMancala(0)  <  board.stonesInMancala(1))
							io.println("Player 2 wins!");
						else
							io.println("A tie!");
						break;
					default:
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	   private  void  displayBoard()  {
	        String  mancalaHousesFiller  =  " | ";
	        String  mancalaLineFiller ="+----+-------+-------+-------+-------+-------+-------+----+";
	        io.println(mancalaLineFiller);
	        io.print("| "+ players[1].getName()+mancalaHousesFiller);
	        int stonesInHouse;
	        String stonesInHousePrint="";
	        for  (int  i  =  board.playingHouses;  i  >=  1;  i--)  {
	        	stonesInHouse=board.stonesInHouse(1,  i);
	        	if(stonesInHouse<10)
	        		stonesInHousePrint=" "+stonesInHouse;
	        	else
	        		stonesInHousePrint=Integer.toString(stonesInHouse);
	           io.print(i+"["+ stonesInHousePrint + "]" + mancalaHousesFiller);
	        }
	        int stonesInMancala = board.stonesInMancala(0);
	        String stonesInMancalaPrint="";
	        if(stonesInMancala<10)
	        	stonesInMancalaPrint = " "+stonesInMancala;
	        else
	        	stonesInMancalaPrint=Integer.toString(stonesInMancala);
	        io.println(stonesInMancalaPrint +" |");
	        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
	        stonesInMancala = board.stonesInMancala(1);
	        if(stonesInMancala<10)
	        	stonesInMancalaPrint = " "+stonesInMancala;
	        else
	        	stonesInMancalaPrint=Integer.toString(stonesInMancala);
	        io.print("| " + stonesInMancalaPrint +mancalaHousesFiller);
	        for (int  i  =  1;  i  <=  board.playingHouses;  i++){
	        	stonesInHouse=board.stonesInHouse(0,  i);
	        	if(stonesInHouse<10)
	        		stonesInHousePrint=" "+stonesInHouse;
	        	else
	        		stonesInHousePrint=Integer.toString(stonesInHouse);
	        	io.print(i+"["+stonesInHousePrint + "]" + mancalaHousesFiller);
	        }
	        io.println(players[0].getName()+" |");
	        io.println(mancalaLineFiller);
	    }
}
