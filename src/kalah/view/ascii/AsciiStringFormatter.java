package kalah.view.ascii;

import kalah.model.House;
import kalah.model.Score;
import kalah.model.Store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.valueOf;
import static kalah.util.MathUtil.isSameParity;
import static kalah.util.StringFormatter.repeatString;
import static kalah.util.StringFormatter.rightAlignInteger;

/**
 * Formats strings for an Ascii Observer.
 */
public class AsciiStringFormatter {

    private static final int ADDITIONAL_CHARACTERS_PER_HOUSE = 4; // i.e. " [] "
    private static final int ADDITIONAL_CHARACTERS_PER_STORE = 2; // i.e. " 10 ". Two additional spaces.
    private static final char PLAYER_SYMBOL = 'P';
    private static final char HOUSE_OPEN_BRACE = '[';
    private static final char HOUSE_CLOSE_BRACE = ']';
    private static final char VERTICAL_SYMBOL = '|';
    private static final char HORIZONTAL_SYMBOL = '-';
    private static final char CORNER_SYMBOL = '+';
    private static final char GAP_SYMBOL = ' ';

    private final int maxSeedCountLength;

    private final int maxHouseNumberLength;

    AsciiStringFormatter(int maxSeedCountLength, int maxHouseNumberLength) {
        this.maxSeedCountLength = maxSeedCountLength;
        this.maxHouseNumberLength = maxHouseNumberLength;
    }

    String playersPits(int playerNum, int numPlayers, List<House> houses, Store store) {
        return isSameParity(playerNum, numPlayers) ? reverseHouseOrder(houses, store, playerNum) :
                forwardHouseOrder(houses, store, playerNum);
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

    String boardTopAndBottomLine(int numHouses) {
        return boardTopAndBottomOuter() +
                boardInner(numHouses) +
                boardTopAndBottomOuter();
    }

    private String boardTopAndBottomOuter() {
        return CORNER_SYMBOL +
                repeatString(valueOf(HORIZONTAL_SYMBOL), maxSeedCountLength + ADDITIONAL_CHARACTERS_PER_STORE) +
                CORNER_SYMBOL;
    }

    private String boardInner(int numHouses) {
        StringBuilder builder = new StringBuilder();
        String houseDash = repeatString(valueOf(HORIZONTAL_SYMBOL),
                maxSeedCountLength + maxHouseNumberLength + ADDITIONAL_CHARACTERS_PER_HOUSE) +
                CORNER_SYMBOL;
        return builder.append(repeatString(houseDash, numHouses))
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

    String boardMiddleLine(int numHouses) {
        return boardMiddleOuter() +
                boardInner(numHouses) +
                boardMiddleOuter();
    }

    private String boardMiddleOuter() {
        return VERTICAL_SYMBOL +
                repeatString(valueOf(GAP_SYMBOL), maxSeedCountLength + ADDITIONAL_CHARACTERS_PER_STORE) +
                VERTICAL_SYMBOL;
    }

    String nextMovePrompt(int playersTurn) {
        return "Player " +
                PLAYER_SYMBOL +
                playersTurn +
                "'s turn - Specify house number or 'q' to quit: ";
    }

    String score(Score score) {
        return "\tplayer " + score.getPlayerNumber() + ":" + score.getScore();
    }

    String winners(List<Score> winners) {
        return winners.size() > 1 ? "A tie!" : "Player " + winners.get(0).getPlayerNumber() + " wins!";
    }

    String gameOver() {
        return "Game over";
    }

    String invalidEmptyHouse() {
        return "House is empty. Move again.";
    }
}
