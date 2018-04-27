package kalah.view.ascii;

import com.qualitascorpus.testsupport.IO;
import kalah.service.GameService;
import kalah.view.KalahObserver;

/**
 * Ascii implementation of a Kalah Observer.
 */
public class AsciiObserver implements KalahObserver {

    private final AsciiPrinter asciiPrinter;

    public AsciiObserver(GameService game, IO io) {
        this.asciiPrinter = new AsciiPrinter(game, io);
    }

    @Override
    public String nextMove() {
        asciiPrinter.printBoard();
        return asciiPrinter.printPrompt();
    }

    @Override
    public void emptyHouse() {
        asciiPrinter.printEmptyHouse();
    }

    @Override
    public void gameQuit() {
        asciiPrinter.printGameOver();
        asciiPrinter.printBoard();
    }

    @Override
    public void gameOver() {
        asciiPrinter.printBoard();
        asciiPrinter.printGameOver();
        asciiPrinter.printBoard();
        asciiPrinter.printScoresAndWinner();
    }

}
