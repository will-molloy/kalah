package kalah.view;

import com.qualitascorpus.testsupport.IO;
import kalah.model.Board;
import kalah.model.House;
import kalah.model.Store;
import kalah.service.Game;

import java.util.*;

import static java.lang.String.valueOf;
import static kalah.util.MathUtil.getDigitLength;
import static kalah.util.StringFormatter.formatInteger;
import static kalah.util.StringFormatter.repeatString;

// make super class! OBSERVER PATTERN. Update method triggers printing. Kalah(Controller) class simply wires Model and view.

/**
 * Renders Kalah board board in ASCII (stdout).
 */
public class AsciiView {

    private static final int ADDITIONAL_CHARACTERS_PER_HOUSE = 4; // i.e. " [] "
    private static final int ADDITIONAL_CHARACTERS_PER_STORE = 2; // i.e. " 10 ". Two additional spaces.
    private static final char PLAYER_SYMBOL = 'P';
    private static final char HOUSE_OPEN_BRACE = '[';
    private static final char HOUSE_CLOSE_BRACE = ']';
    private static final char VERTICAL_SYMBOL = '|';
    private static final char HORIZONTAL_SYMBOL = '-';
    private static final char CORNER_SYMBOL = '+';
    private static final char GAP_SYMBOL = ' ';

    private final Board board;
    private final Game game;
    private final IO io;
    private final int maxSeedCountLength;
    private final int maxHouseNumberLength;

    public AsciiView(Game game, IO io) {
        this.game = game;
        this.board = game.getBoard();
        this.io = io;
        maxSeedCountLength = getDigitLength(2 * 6 * 4);
        maxHouseNumberLength = getDigitLength(6);
    }

    public String printPrompt() {
        return io.readFromKeyboard("Player " + PLAYER_SYMBOL +
                game.getCurrentTurnsPlayer() +
                "'s turn - Specify house number or 'q' to quit: ");
    }

    public void printBoard() {
        io.println(boardOuter());
        printPlayers();
        io.println(boardOuter());
    }

    private void printPlayers() {
        boolean reverse = true;
        for (int i = board.numPlayers(); i > 0; i--) {
            List<House> houses = board.getHousesFor(i);
            Store store = board.getStoreFor((i % board.numPlayers()) + 1);
            if (reverse) {
                io.println(reverseHouseOrder(houses, store, i));
            } else {
                io.println(forwardHouseOrder(houses, store, i));
            }
            reverse = !reverse;
            if (i > 1) {
                io.println(boardMiddle());
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

    private String boardOuter() {
        return outerDash() +
                centerDash() +
                outerDash();
    }

    private String outerDash() {
        return CORNER_SYMBOL +
                repeatString(valueOf(HORIZONTAL_SYMBOL), maxSeedCountLength + ADDITIONAL_CHARACTERS_PER_STORE) +
                CORNER_SYMBOL;
    }

    private String centerDash() {
        StringBuilder builder = new StringBuilder();
        String houseDash = repeatString(valueOf(HORIZONTAL_SYMBOL),
                maxSeedCountLength + maxHouseNumberLength + ADDITIONAL_CHARACTERS_PER_HOUSE) +
                CORNER_SYMBOL;
        return builder.append(repeatString(houseDash, 6))
                .deleteCharAt(builder.length() - 1)
                .toString();
    }

    private String formatPlayerNumber(int playerNumber) {
        return VERTICAL_SYMBOL +
                repeatString(valueOf(GAP_SYMBOL), maxSeedCountLength - 1) + // -1; PLAYER_SYMBOL takes a space
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
                formatInteger(house.getHouseNumber(), maxHouseNumberLength) +
                HOUSE_OPEN_BRACE +
                formatInteger(house.seedCount(), maxSeedCountLength) +
                HOUSE_CLOSE_BRACE + GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    private String formatStore(Store store) {
        return valueOf(VERTICAL_SYMBOL) + GAP_SYMBOL +
                formatInteger(store.seedCount(), maxSeedCountLength) +
                GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    private String boardMiddle() {
        return middleOuter() +
                centerDash() +
                middleOuter();
    }

    private String middleOuter() {
        return VERTICAL_SYMBOL +
                repeatString(valueOf(GAP_SYMBOL), maxSeedCountLength + ADDITIONAL_CHARACTERS_PER_STORE) +
                VERTICAL_SYMBOL;
    }

    public void printGameOver() {
        io.println("Game over");
    }

    public void printEmptyHouse() {
        io.println("House is empty. Move again.");
    }

    public void printScores() {
        List<Integer> winners = new ArrayList<>();
        winners.add(-1);
        int currentBest = -1;
        for (int i = 1; i <= board.numPlayers(); i++){
            int score = computeScore(i);
            if (score >= currentBest){
                if (score > currentBest){
                    winners = new ArrayList<>();
                    currentBest = score;
                }
                winners.add(i);
            }
            io.println("\tplayer " + i + ":" + score);
        }
        if (winners.size() > 1) {
            io.println("A tie!");
        } else {
            io.println("Player " + winners.get(0) + " wins!");
        }
    }

    private int computeScore(int playerNumber){
        int total = 0;
        for (House h : board.getHousesFor(playerNumber)){
            total += h.seedCount();
        }
        total += board.getStoreFor(playerNumber).seedCount();
        return total;
    }
}
