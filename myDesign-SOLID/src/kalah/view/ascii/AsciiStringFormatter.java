package kalah.view.ascii;

import kalah.model.House;
import kalah.model.Score;
import kalah.model.Store;

import static java.lang.String.valueOf;
import static kalah.util.MathUtil.digitLength;
import static kalah.util.StringFormatUtil.repeatString;
import static kalah.util.StringFormatUtil.rightAlignInteger;

class AsciiStringFormatter {

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

    AsciiStringFormatter(int totalSeeds, int numHouses) {
        this.maxSeedCountLength = digitLength(totalSeeds);
        this.maxHouseNumberLength = digitLength(numHouses);
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

    public String formatPlayerNumber(int playerNumber) {
        return VERTICAL_SYMBOL +
                repeatString(valueOf(GAP_SYMBOL), maxSeedCountLength - 1) + // -1; PLAYER_SYMBOL takes a slot
                PLAYER_SYMBOL +
                playerNumber +
                GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    public String formatHouses(Iterable<House> houses) {
        StringBuilder builder = new StringBuilder();
        for (House house : houses) {
            builder.append(formatHouse(house));
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    private String formatHouse(House house) {
        return GAP_SYMBOL +
                rightAlignInteger(house.getHouseNumber(), maxHouseNumberLength) +
                HOUSE_OPEN_BRACE +
                rightAlignInteger(house.seedCount(), maxSeedCountLength) +
                HOUSE_CLOSE_BRACE + GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    public String formatStore(Store store) {
        return valueOf(VERTICAL_SYMBOL) + GAP_SYMBOL +
                rightAlignInteger(store.seedCount(), maxSeedCountLength) +
                GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    String boardMiddleLine(int numHouses) {
        return boardMiddleOuter() + boardInner(numHouses) + boardMiddleOuter();
    }

    private String boardMiddleOuter() {
        return VERTICAL_SYMBOL +
                repeatString(valueOf(GAP_SYMBOL), maxSeedCountLength + ADDITIONAL_CHARACTERS_PER_STORE) +
                VERTICAL_SYMBOL;
    }

    String nextMovePrompt(int playersTurn) {
        return "Player " + PLAYER_SYMBOL + playersTurn + "'s turn - Specify house number or 'q' to quit: ";
    }

    String formatScore(Score score) {
        return "\tplayer " + score.player() + ":" + score.value();
    }

    String formatWinner(int playerNum) {
        return "Player " + playerNum + " wins!";
    }

    String aTie() {
        return "A tie!";
    }

    String gameOver() {
        return "GameService over";
    }

    String invalidEmptyHouse() {
        return "House is empty. Move again.";
    }
}
