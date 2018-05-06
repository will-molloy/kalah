package kalah;
import java.io.IOException;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
public class  Player  {
    Player(String  name,  int  playerNum)  {
        this.name  =  name;
        this.playerNum  =  playerNum;
    }
    public  String  getName()  {
        if  (name  !=  null)
           return  name;
        else
           return  "Computer";
    }
    public  int  getPlayerNum()  {
        return  this.playerNum;
    }
    public  int  selectAMove(KalahBoard board,IO io)   throws IOException  {
    	Boolean validInput=false;
        if  (name  !=  null)  {
        int pitNum;
        String input="";
        System.out.flush();
         if(board.gameOver(playerNum)){
        	  pitNum=-2;
        	  return pitNum;
         }
         else
        	 input= io.readFromKeyboard("Player " + name + "'s turn - Specify house number or 'q' to quit: ");
         String[] inputs = {"1","2","3","4","5","6","q"};
         for(int i=0;i<inputs.length;i++){
        	 if(input.equals(inputs[i])){
        		 validInput=true;
        		 break;
        	 }
         }
         if(input.equals("q")){
        	 pitNum=-1;
        	 return pitNum;
         }
         while(!validInput){
	        	 io.println("Invalid input, please try again.");
	             input= io.readFromKeyboard("Player " + name + "'s turn - Specify house number or 'q' to quit: ");
         }
        pitNum =  Integer.parseInt(input);
        return  pitNum;
        }
    int  bestMove  =  -1;
    int  repeatMove  =  -1;
    int  maxNewStones  =  -1;
    for  (int  pitNum  =  1;  pitNum  <=
          board.playingHouses;  pitNum++)  {
        if  (board.stonesInHouse(playerNum, pitNum)  !=  0)  {
			KalahBoard  testBoard  =  board.makeACopy();
			KalahBoard.NextStep  nextStep  =
				testBoard.doTheMove(playerNum, pitNum);
			if  (nextStep==KalahBoard.NextStep.GoAgain)
				repeatMove  =  pitNum;
			int  newStones  =
                 testBoard.stonesInMancala(playerNum)  -
                 board.stonesInMancala(playerNum);
			if  (newStones  >  maxNewStones) {
                   maxNewStones = newStones;
                   bestMove  =  pitNum;
               }
           }
        }
        if  (maxNewStones  >  1)
			return  bestMove;
        else  if  (repeatMove  !=  -1)
			return  repeatMove;
        else
			return  bestMove;
    }
    String  name;
    int  playerNum;
}
