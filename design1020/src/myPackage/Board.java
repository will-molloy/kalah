package myPackage;
import java.util.ArrayList;
import java.util.Collections;
import com.qualitascorpus.testsupport.IO;
public class Board {
	private Player playerOne;
	private Player playerTwo;
	private Player currentPlayer;
	private IPrinter printer;
	private IRule captureRule;
	private IRule houseIsEmptyRule;
	public Board(Printer printerArg) {
		playerOne=new Player(1);
		playerTwo=new Player(2);
		currentPlayer=getPlayer(Variables.bottomPlayerIdentifier);
		printer=printerArg;
		captureRule=new RuleCapture();
		houseIsEmptyRule=new RuleHouseIsEmpty();
	}
	 void initializeBoard(){
		playerOne.initialize();
		playerTwo.initialize();
	}
	boolean isATie(){
		return currentPlayer.totalSeedCount()==getOtherPlayer().totalSeedCount();
	}
	public boolean hasCurrentPlayerLost(){
		return currentPlayer.areHousesEmpty();
	}
	public int getCurrentPlayerNumber(){
		return currentPlayer.getPlayerNumber();
	}
	 Player getPlayer(int i){
		if (i==1) {
			return playerOne;
		} else {
			return playerTwo;
		}
	}
	public void exeuteNextTurn(int i,IO io){
		if(houseIsEmptyRule.apply(currentPlayer, i, printer)){
			return;
		}
		SeedOverFlowAndHouseNumber seedOverFlowAndHouseNumber=currentPlayer.takeTurnAndGiveExcessSeeds(i);
		if (seedOverFlowAndHouseNumber.endsInEmpty()) {
			captureRule.apply(this, seedOverFlowAndHouseNumber, null);
			changeCurrentPlayer();
			return;
		}
		if (!seedOverFlowAndHouseNumber.oneMoreMove()) {
			Player bufferLocalPlayer=getOtherPlayer();
			int overFlow=seedOverFlowAndHouseNumber.getOverFlow();
			while(overFlow>0){
				if (bufferLocalPlayer.equals(currentPlayer)) {
					seedOverFlowAndHouseNumber=bufferLocalPlayer.addSeedsFromOtherPlayerPT(overFlow);
					overFlow=seedOverFlowAndHouseNumber.getOverFlow();
					if (seedOverFlowAndHouseNumber.endsInEmpty()) {
						captureRule.apply(this, seedOverFlowAndHouseNumber, null);
					}
					bufferLocalPlayer=getOtherPlayer();
				} else {
					overFlow=bufferLocalPlayer.addSeedsFromOtherPlayer(overFlow);
					bufferLocalPlayer=currentPlayer;
				}
			}
			changeCurrentPlayer();
		}
	}
	int getOpposingNumber(int arg){
		ArrayList<Integer> intList=new ArrayList<Integer>();
		for(int i=0;i<Variables.numberOfHousesAllowed;i++){
			intList.add(i, i+1);
		}
		int index=intList.indexOf(arg);
		Collections.reverse(intList);
		return intList.get(index);
	}
	 Player getOtherPlayer(){
		if (currentPlayer==playerOne) {
			return playerTwo;
		} else {
			return playerOne;
		}
	}
	 Player getCurrentPlayer(){
		return currentPlayer;
	}
	private void changeCurrentPlayer(){
		if (currentPlayer==playerOne) {
			currentPlayer=playerTwo;
		} else {
			currentPlayer=playerOne;
		}
	}
}
