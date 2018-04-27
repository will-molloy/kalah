package kalah.view.ascii;

import com.qualitascorpus.testsupport.IO;
import kalah.model.House;
import kalah.model.Store;
import kalah.service.GameService;

import java.util.List;

import static kalah.util.MathUtil.getDigitLength;

public class AsciiPrinter {

    private final IO io;

    private final GameService game;

    private final AsciiFormatter asciiFormatter;

    AsciiPrinter(GameService game, IO io) {
        this.io = io;
        this.game = game;
        asciiFormatter = new AsciiFormatter(getDigitLength(game.totalNumSeeds()), getDigitLength(game.maxHouseNumber()));
    }

    void printBoard() {
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

    String printPrompt() {
        return io.readFromKeyboard(asciiFormatter.nextMovePrompt(game.currentTurnsPlayer()));
    }

    void printScoresAndWinner() {
        for (int i = 1; i <= game.numPlayers(); i++) {
            io.println(asciiFormatter.score(game.getScore(i)));
        }
        io.println(asciiFormatter.winners(game.getWinners()));
    }

    void printGameOver() {
        io.println(asciiFormatter.gameOver());
    }

    void printEmptyHouse() {
        io.println(asciiFormatter.invalidEmptyHouse());
    }
}
