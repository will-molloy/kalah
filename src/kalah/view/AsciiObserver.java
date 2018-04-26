package kalah.view;

import com.qualitascorpus.testsupport.IO;
import kalah.model.House;
import kalah.model.Score;
import kalah.model.Store;
import kalah.service.GameService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.valueOf;
import static kalah.util.MathUtil.getDigitLength;
import static kalah.util.StringFormatter.repeatString;
import static kalah.util.StringFormatter.rightAlignInteger;

/**
 * Ascii implementation of a Kalah Observer.
 */
public class AsciiObserver implements KalahObserver {

    private static final int ADDITIONAL_CHARACTERS_PER_HOUSE = 4; // i.e. " [] "
    private static final int ADDITIONAL_CHARACTERS_PER_STORE = 2; // i.e. " 10 ". Two additional spaces.
    private static final char PLAYER_SYMBOL = 'P';
    private static final char HOUSE_OPEN_BRACE = '[';
    private static final char HOUSE_CLOSE_BRACE = ']';
    private static final char VERTICAL_SYMBOL = '|';
    private static final char HORIZONTAL_SYMBOL = '-';
    private static final char CORNER_SYMBOL = '+';
    private static final char GAP_SYMBOL = ' ';

    private final GameService game;

    private final IO io;

    private final int maxSeedCountLength;

    private final int maxHouseNumberLength;

    public AsciiObserver(GameService game, IO io) {
        this.game = game;
        this.io = io;
        maxSeedCountLength = getDigitLength(game.totalNumSeeds());
        maxHouseNumberLength = getDigitLength(game.maxHouseNumber());
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
        printScores();
    }

    private void printBoard() {
        io.println(boardTopAndBottomLine());
        printPlayerPieces();
        io.println(boardTopAndBottomLine());
    }

    private String printPrompt() {
        return io.readFromKeyboard("Player " + PLAYER_SYMBOL + game.currentTurnsPlayer() +
                "'s turn - Specify house number or 'q' to quit: ");
    }

    private void printPlayerPieces() {
        boolean reverse = true;
        for (int i = game.numPlayers(); i > 0; i--) {
            List<House> houses = game.getHouses(i);
            Store store = game.getStore((i % game.numPlayers()) + 1);
            if (reverse) {
                io.println(reverseHouseOrder(houses, store, i));
            } else {
                io.println(forwardHouseOrder(houses, store, i));
            }
            reverse = !reverse;
            if (i > 1) {
                io.println(boardMiddleLine());
            }
        }
    }

    private String reverseHouseOrder(List<House> houses, Store store, int playerNumber) {
        List<House> temp = new ArrayList<>(houses);
        Collections.reverse(temp);
        return formatPlayerNumber(playerNumber) +
                formatHouses(temp) +
                formatStore(store);
    }

    private String forwardHouseOrder(List<House> houses, Store store, int playerNumber) {
        return formatStore(store) +
                formatHouses(houses) +
                formatPlayerNumber(playerNumber);
    }

    private String boardTopAndBottomLine() {
        return boardTopAndBottomOuter() +
                boardInner() +
                boardTopAndBottomOuter();
    }

    private String boardTopAndBottomOuter() {
        return CORNER_SYMBOL +
                repeatString(valueOf(HORIZONTAL_SYMBOL), maxSeedCountLength + ADDITIONAL_CHARACTERS_PER_STORE) +
                CORNER_SYMBOL;
    }

    private String boardInner() {
        StringBuilder builder = new StringBuilder();
        String houseDash = repeatString(valueOf(HORIZONTAL_SYMBOL),
                maxSeedCountLength + maxHouseNumberLength + ADDITIONAL_CHARACTERS_PER_HOUSE) +
                CORNER_SYMBOL;
        return builder.append(repeatString(houseDash, game.maxHouseNumber()))
                .deleteCharAt(builder.length() - 1)
                .toString();
    }

    private String formatPlayerNumber(int playerNumber) {
        return VERTICAL_SYMBOL +
                repeatString(valueOf(GAP_SYMBOL), maxSeedCountLength - 1) + // -1; PLAYER_SYMBOL takes a slot
                PLAYER_SYMBOL +
                playerNumber +
                GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    private String formatHouses(List<House> houses) {
        StringBuilder builder = new StringBuilder();
        for (House house : houses) {
            builder.append(formatHouse(house));
        }
        return builder.deleteCharAt(builder.length() - 1)
                .toString();
    }

    private String formatHouse(House house) {
        return GAP_SYMBOL +
                rightAlignInteger(house.getHouseNumber(), maxHouseNumberLength) +
                HOUSE_OPEN_BRACE +
                rightAlignInteger(house.seedCount(), maxSeedCountLength) +
                HOUSE_CLOSE_BRACE + GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    private String formatStore(Store store) {
        return valueOf(VERTICAL_SYMBOL) + GAP_SYMBOL +
                rightAlignInteger(store.seedCount(), maxSeedCountLength) +
                GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    private String boardMiddleLine() {
        return boardMiddleOuter() +
                boardInner() +
                boardMiddleOuter();
    }

    private String boardMiddleOuter() {
        return VERTICAL_SYMBOL +
                repeatString(valueOf(GAP_SYMBOL), maxSeedCountLength + ADDITIONAL_CHARACTERS_PER_STORE) +
                VERTICAL_SYMBOL;
    }

    private void printGameOver() {
        io.println("Game over");
    }

    private void printEmptyHouse() {
        io.println("House is empty. Move again.");
    }

    private void printScores() {
        for (Score s : game.getScores()) {
            io.println("\tplayer " + s.getPlayerNumber() + ":" + s.getScore());
        }
        List<Score> winners = game.getWinners();
        if (winners.size() > 1) {
            io.println("A tie!");
        } else {
            io.println("Player " + winners.get(0).getPlayerNumber() + " wins!");
        }
    }

}
