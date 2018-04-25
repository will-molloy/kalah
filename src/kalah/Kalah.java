package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.model.MoveOutcome;
import kalah.service.Game;
import kalah.view.AsciiView;

import static kalah.model.MoveOutcome.EMPTY_HOUSE;
import static kalah.model.MoveOutcome.GAME_OVER;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {

    public static void main(String... args) {
        new Kalah().play(new MockIO());
    }

    // MVC!
    public void play(IO io) {
        Game game = new Game(6, 4, 2);
        AsciiView asciiView = new AsciiView(game, io);
        while (true) {
            asciiView.printBoard();
            String input = asciiView.printPrompt();
            if (input.equals("q")) {
                asciiView.printGameOver();
                asciiView.printBoard();
                break;
            } else {
                int houseNumber = Integer.parseInt(input);
                MoveOutcome outcome = game.move(houseNumber);
                if (outcome.equals(EMPTY_HOUSE)) {
                    asciiView.printEmptyHouse();
                } else if (outcome.equals(GAME_OVER)) {
                    asciiView.printBoard();
                    asciiView.printGameOver();
                    asciiView.printBoard();
                    asciiView.printScores();
                    break;
                }
            }
        }
    }
}
