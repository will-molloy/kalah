package kalah.view;

import com.qualitascorpus.testsupport.IO;
import kalah.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
import static java.util.Collections.reverse;
import static kalah.util.MathUtil.getDigitLength;
import static kalah.util.StringFormatter.formatInteger;
import static kalah.util.StringFormatter.repeatString;

// make super class!

/**
 * Renders Kalah game game in ASCII (stdout).
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

    private final Game game;
    private final IO io;
    private final int maxSeedCountLength;
    private final int maxHouseNumberLength;

    public AsciiView(Game game, IO io) {
        this.game = game;
        this.io = io;
        maxSeedCountLength = getDigitLength(game.getNumHouses() * game.getNumInitialSeeds());
        maxHouseNumberLength = getDigitLength(game.getNumHouses());
    }

    public String printPrompt() {
        return io.readFromKeyboard("Player " + PLAYER_SYMBOL +
                game.getCurrentTurnsPlayer().getPlayerNumber() +
                "'s turn - Specify house number or 'q' to quit: ");
    }

    public void printBoard() {
        io.println(boardOuter());
        printPlayers();
        io.println(boardOuter());
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

    private void printPlayers() {
        boolean reverse = true;
        for (int i = game.getNumPlayers(); i > 0; i--) {
            Player player = game.getPlayer(i);
            if (reverse) {
                io.println(playerReverseHouseOrder(player));
            } else {
                io.println(playerForwardHouseOrder(player));
            }
            if (i > 1) {
                io.println(boardMiddle());
            }
            reverse = !reverse;
        }
    }

    private String centerDash() {
        StringBuilder builder = new StringBuilder();
        String houseDash = repeatString(valueOf(HORIZONTAL_SYMBOL),
                maxSeedCountLength + maxHouseNumberLength + ADDITIONAL_CHARACTERS_PER_HOUSE) +
                CORNER_SYMBOL;
        return builder.append(repeatString(houseDash, game.getNumHouses()))
                .deleteCharAt(builder.length() - 1)
                .toString();
    }

    private String playerReverseHouseOrder(Player player) {
        List<House> reverseHouses = new ArrayList<>(player.getHouses());
        reverse(reverseHouses);
        return formatPlayerNumber(player.getPlayerNumber()) +
                formatHouses(reverseHouses) +
                formatStore(storeToPrint(player));
    }

    /**
     * In the Ascii output a player prints the next players store inline with their houses.
     * Returns the store of the next player.
     */
    private Store storeToPrint(Player player) {
        return game.getPlayer(player.getPlayerNumber() % game.getNumPlayers() + 1).getStore();
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
                formatInteger(house.number(), maxHouseNumberLength) +
                HOUSE_OPEN_BRACE +
                formatInteger(house.seedCount(), maxSeedCountLength) +
                HOUSE_CLOSE_BRACE + GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    private String formatStore(Store store) {
        return valueOf(VERTICAL_SYMBOL) + GAP_SYMBOL +
                formatInteger(store.seedCount(), maxSeedCountLength) +
                GAP_SYMBOL + VERTICAL_SYMBOL;
    }

    private String playerForwardHouseOrder(Player player) {
        return formatStore(storeToPrint(player)) +
                formatHouses(player.getHouses()) +
                formatPlayerNumber(player.getPlayerNumber());
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

    public void printScores(List<Score> scores) {
        List<Score> winners = new ArrayList<>();
        winners.add(new Score(-1,-1));
        for (Score s : scores){
            if (s.score() >= winners.get(0).score()){
                if (s.score() > winners.get(0).score()){
                    winners = new ArrayList<>();
                }
                winners.add(s);
            }
            io.println("\tplayer " +s.playerNumber()+":"+s.score());
        }
        if (winners.size() > 1){
            io.println("A tie!");
        } else {
            io.println("Player " + winners.get(0).playerNumber() + " wins!");
        }
    }
}
