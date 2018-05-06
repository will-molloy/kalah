package myPackage;
public class RuleCapture implements IRule {
	@Override
	public boolean apply(Object objOne, Object objTwo, IPrinter printer) {
		Board board=(Board) objOne;
		Player otherPlayer=board.getOtherPlayer();
		Player currentPlayer=board.getCurrentPlayer();
		SeedOverFlowAndHouseNumber seedOverFlowAndHouseNumber=(SeedOverFlowAndHouseNumber)objTwo;
		int oppositeHouseSeedCount=otherPlayer.giveAllSeedsFromThisHouse(board.getOpposingNumber(seedOverFlowAndHouseNumber.getHouseNumber()));
		if (oppositeHouseSeedCount>0) {
			currentPlayer.getStore().addSeeds(oppositeHouseSeedCount+1);
		}
		else{
			currentPlayer.addASeedAt(seedOverFlowAndHouseNumber.getHouseNumber());
		}
		return false;
	}
}
