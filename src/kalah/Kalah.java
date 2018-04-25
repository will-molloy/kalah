package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.model.MoveOutcome;
import kalah.service.Game;
import kalah.view.AsciiObserver;
import kalah.view.KalahObserver;

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
        KalahObserver observer = new AsciiObserver(game, io);
        while (true) {
            String input = observer.nextMove();
            if (input.equals("q")) {
                observer.gameQuit();
                break;
            } else {
                int houseNumber = Integer.parseInt(input);
                MoveOutcome outcome = game.move(houseNumber);
                if (outcome.equals(EMPTY_HOUSE)) {
                    observer.emptyHouseMoveAgain();
                } else if (outcome.equals(GAME_OVER)) {
                    observer.gameOver();
                    break;
                }
            }
        }
    }
}
