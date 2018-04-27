package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.service.GameService;
import kalah.service.MoveOutcome;
import kalah.view.KalahObserver;
import kalah.view.ascii.AsciiObserver;

import static kalah.service.MoveOutcome.GAME_OVER;
import static kalah.service.MoveOutcome.INVALID_EMPTY_HOUSE;

/**
 * This class is the starting point for a Kalah implementation using the test infrastructure.
 * Represents the Kalah entry point and controller. Wires the game and view. Notifies the view of game updates.
 */
public class Kalah {

    public static void main(String... args) {
        new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        GameService game = new GameService(6, 4, 2);
        KalahObserver observer = new AsciiObserver(game, io);
        while (true) {
            String input = observer.nextMove();
            if (input.equals("q")) {
                observer.gameQuit();
                break;
            } else {
                MoveOutcome outcome = game.validateAndMakeMove(Integer.parseInt(input));
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
