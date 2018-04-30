package kalah.view.ascii;

import com.qualitascorpus.testsupport.IO;
import kalah.model.House;
import kalah.model.Store;
import kalah.service.GameService;
import kalah.view.KalahObserver;

import java.util.List;

import static kalah.util.MathUtil.getDigitLength;

/**
 * Ascii implementation of a Kalah Observer.
 */
public class AsciiObserver implements KalahObserver {

    private final AsciiFormatter asciiFormatter;

    private final GameService game;

    private final IO io;

    public AsciiObserver(GameService game, IO io) {
        asciiFormatter = new AsciiFormatter(getDigitLength(game.totalNumSeeds()), getDigitLength(game.maxHouseNumber()));
        this.game = game;
        this.io = io;
    }

    @Override
    public String nextMove() {
        printBoard();
        return printPrompt();
    }

    @Override
    public void emptyHouse() {
        printEmptyHouse();
    }

    @Override
    public void gameQuit() {
        printGameOver();
        printBoard();
    }

    @Override
    public void gameOver() {
        printBoard();
        printGameOver();
        printBoard();
        printScoresAndWinner();
    }

    private void printBoard() {
        printBoardOuter();
        printPlayersWithSeparator();
        printBoardOuter();
    }

    private void printBoardOuter() {
        io.println(asciiFormatter.boardTopAndBottomLine(game.maxHouseNumber()));
    }

    private void printPlayersWithSeparator() {
        for (int i = game.numPlayers(); i > 1; i--) {
            printPlayersPieces(i);
            io.println(asciiFormatter.boardMiddleLine(game.maxHouseNumber()));
        }
        printPlayersPieces(1);
    }

    private void printPlayersPieces(int playerNum) {
        List<House> houses = game.getHouses(playerNum);
        Store store = game.getStore((playerNum % game.numPlayers()) + 1);
        io.println(asciiFormatter.playersPieces(playerNum, game.numPlayers(), houses, store));
    }

    private String printPrompt() {
        return io.readFromKeyboard(asciiFormatter.nextMovePrompt(game.currentTurnsPlayer()));
    }

    private void printScoresAndWinner() {
        for (int i = 1; i <= game.numPlayers(); i++) {
            io.println(asciiFormatter.score(game.getScore(i)));
        }
        io.println(asciiFormatter.winners(game.getWinners()));
    }

    private void printGameOver() {
        io.println(asciiFormatter.gameOver());
    }

    private void printEmptyHouse() {
        io.println(asciiFormatter.invalidEmptyHouse());
    }

}
