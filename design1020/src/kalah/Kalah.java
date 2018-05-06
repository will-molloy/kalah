package kalah;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import myPackage.Board;
import myPackage.Printer;
public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		Printer printer=new Printer(io);
		Board board=new Board(printer);
		printer.printBoard(board);
		String input="";
		input=io.readFromKeyboard("Player P"+board.getCurrentPlayerNumber()+"'s turn - Specify house number or 'q' to quit: ");
		while (!board.hasCurrentPlayerLost() & !input.equals("q")) {
			board.exeuteNextTurn(Integer.parseInt(input),io);
			printer.printBoard(board);
			if (!isGameFinished(input, board)) {
				input=io.readFromKeyboard("Player P"+board.getCurrentPlayerNumber()+"'s turn - Specify house number or 'q' to quit: ");
			}
		}
		printer.printFinishingStatement( board);
	}
	private boolean isGameFinished(String input,Board board){
		if (input.equals("q")||board.hasCurrentPlayerLost()) {
			return true;
		}
		return false;
	}
}
