package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.model.ContinuousBoardFactory;
import kalah.service.GameService;
import kalah.service.MoveOutcome;
import kalah.view.KalahObserver;
import kalah.view.ascii.AsciiObserver;

import static java.lang.Integer.*;
import static kalah.service.MoveOutcome.GAME_OVER;
import static kalah.service.MoveOutcome.INVALID_EMPTY_HOUSE;

/**
 * This class is the starting point for a Kalah implementation using the test infrastructure.
 * Represents the Kalah controller. Wires the game and view. Notifies the view of game updates.
 */
public class Kalah {

    private GameService game;

    private KalahObserver observer;

    public static void main(String... args) {
        new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        wireGameAndObserver(io);
        controlGame();
    }

    private void wireGameAndObserver(IO io) {
        game = new GameService(new ContinuousBoardFactory().getBoard(6, 4, 2));
        observer = new AsciiObserver(game, io);
    }

    private void controlGame() {
        while (true) {
            String input = observer.promptNextMove();
            if (input.equals("q")) {
                observer.gameQuit();
                break;
            } else {
                MoveOutcome outcome = game.validateAndMakeMove(parseInt(input));
                if (outcome.equals(INVALID_EMPTY_HOUSE)) {
                    observer.emptyHouse();
                }
                if (outcome.equals(GAME_OVER)) {
                    observer.gameOver();
                    break;
                }
            }
        }
    }

}
