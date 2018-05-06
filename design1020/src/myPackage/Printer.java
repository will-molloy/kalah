package myPackage;
import com.qualitascorpus.testsupport.IO;
public class Printer implements IPrinter {
	IO io;
	public Printer(IO ioArg) {
		io=ioArg;
	}
	public  void printHouseIsEmpty(){
		io.println("House is empty. Move again.");
	}
	public void printBoard(Board board){
		Player bottomPlayer=board.getPlayer(Variables.bottomPlayerIdentifier);
		Player topPlayer =board.getPlayer(Variables.topPlayerIdentifier);
		io.println(getTopAndBottomHorizontalBorder());
		io.println(getTopPlayerLine(topPlayer, bottomPlayer));
		io.println(getmidHorizontalBorder());
		io.println(getBottomPlayerLine(topPlayer, bottomPlayer));
		io.println(getTopAndBottomHorizontalBorder());
	}
	public   void printFinishingStatement(Board board){
		io.println("Game over");
		printBoard(board);
		if(board.hasCurrentPlayerLost()){
			io.println("	player "+Variables.bottomPlayerIdentifier+":"+board.getPlayer(Variables.bottomPlayerIdentifier).totalSeedCount());
			io.println("	player "+Variables.topPlayerIdentifier+":"+board.getPlayer(Variables.topPlayerIdentifier).totalSeedCount());
			if (board.isATie()) {
				io.println("A tie!");
			}
			else{
				io.println("Player "+board.getOtherPlayer().getPlayerNumber()+" wins!");
			}
		}
	}
	private   String getTopAndBottomHorizontalBorder(){
		String toReturn="+----+";
		for (int i = 0; i < Variables.numberOfHousesAllowed; i++) {
			toReturn+="-------+";
		}
		toReturn+="----+";
		return toReturn;
	}
	private   String getmidHorizontalBorder(){
		String toReturn="|    |";
		for (int i = 0; i < Variables.numberOfHousesAllowed; i++) {
			if (i==0) {
				toReturn+="-------";
			}
			else{
				toReturn+="+-------";
			}
		}
		toReturn+="|    |";
		return toReturn;
	}
	private   String getPlayerLogo(Player player){
		return "| P"+player.getPlayerNumber()+" |";
	}
	private   String getTopPlayerLine(Player topPlayer,Player bottomPlayer){
		String toReturn=getPlayerLogo(topPlayer);
		for (int i = Variables.numberOfHousesAllowed; i > 0; i--) {
			House house=topPlayer.getHouse(i);
			toReturn+=" "+house.getIdentifierNumber()+"["+paddingForHouse(house.seedCount())+"] |";
		}
		toReturn+=paddingForStore(bottomPlayer.getStore().seedCount())+"|";
		return toReturn;
	}
	private   String getBottomPlayerLine(Player topPlayer,Player bottomPlayer){
		String toReturn="|"+paddingForStore(topPlayer.getStore().seedCount());
		for (int i=1; i < Variables.numberOfHousesAllowed+1; i++) {
			toReturn+="| "+i+"["+paddingForHouse(bottomPlayer.getHouse(i).seedCount())+"] ";
		}
		toReturn+=getPlayerLogo(bottomPlayer);
		return toReturn;
	}
	private   String paddingForHouse(int seedCount){
		if (seedCount>9) {
			return ""+seedCount;
		} else {
			return " "+seedCount;
		}
	}
	private   String paddingForStore(int seedCount){
		if (seedCount>99) {
			return ""+seedCount+" ";
		} else if(seedCount>9) {
			return " "+seedCount+" ";
		}
		else{
			return "  "+seedCount+" ";
		}
	}
}
