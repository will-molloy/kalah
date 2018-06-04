package kalah.view.ascii;

import com.qualitascorpus.testsupport.IO;
import kalah.model.House;
import kalah.model.Score;
import kalah.model.Store;
import kalah.view.KalahObserver;
import kalah.view.ObservableKalah;

import java.util.Collections;
import java.util.List;

import static kalah.util.MathUtil.incrementAndWrap;
import static kalah.util.MathUtil.isSameParity;

public class AsciiObserver extends KalahObserver {

    private final AsciiStringFormatter formatter;

    private final int numHouses;

    public AsciiObserver(ObservableKalah observable, IO io) {
        super(observable, io);
        this.numHouses = observable.getHouses(1).size();
        int totalSeeds = 0;
        for (Score s : observable.getScores()) {
            totalSeeds += s.value();
        }
        formatter = new AsciiStringFormatter(totalSeeds, numHouses);
    }

    @Override
    public String promptNextMove() {
        printBoard();
        return printPrompt();
    }

    @Override
    public void invalidEmptyHouse() {
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
        io.println(formatter.boardTopAndBottomLine(numHouses));
    }

    private void printPlayersWithSeparator() {
        for (int i = observable.numPlayers(); i > 1; i--) {
            printPlayersPits(i);
            io.println(formatter.boardMiddleLine(numHouses));
        }
        printPlayersPits(1);
    }

    private void printPlayersPits(int playerNum) {
        boolean reverse = isSameParity(playerNum, observable.numPlayers());
        Store store = observable.getStore(incrementAndWrap(playerNum, observable.numPlayers())); // want the next players store in the same line
        List<House> houses = observable.getHouses(playerNum);
        if (reverse) {
            Collections.reverse(houses);
        }
        String formattedStore = formatter.formatStore(store);
        String formattedHouses = formatter.formatHouses(houses);
        String formattedPlayerNumber = formatter.formatPlayerNumber(playerNum);
        io.println(reverse ?
                formattedPlayerNumber + formattedHouses + formattedStore :
                formattedStore + formattedHouses + formattedPlayerNumber);
    }

    private String printPrompt() {
        return io.readFromKeyboard(formatter.nextMovePrompt(observable.currentTurnsPlayer()));
    }

    private void printScoresAndWinner() {
        List<Score> scores = observable.getScores();
        for (Score s : scores) {
            io.println(formatter.formatScore(s));
        }
        Collections.sort(scores);
        Score first = scores.get(0);
        Score second = scores.get(1);
        io.println(first.isGreaterThan(second) ? formatter.formatWinner(first.player()) : formatter.aTie());
    }

    private void printGameOver() {
        io.println(formatter.gameOver());
    }

    private void printEmptyHouse() {
        io.println(formatter.invalidEmptyHouse());
    }

}
