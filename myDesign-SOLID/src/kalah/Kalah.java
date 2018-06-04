package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.controller.ControllerResponse;
import kalah.controller.PromptNextMoveResponse;
import kalah.model.Board;
import kalah.model.ContinuousBoard;
import kalah.service.GameService;
import kalah.view.KalahObserver;
import kalah.view.ascii.AsciiObserver;

/**
 * This class is the starting point for a Kalah implementation using the test infrastructure.
 */
public class Kalah {

    public static void main(String... args) {
        new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        Board board = new ContinuousBoard(6, 4, 2);
        GameService gameService = new GameService(board, 1);
        KalahObserver observer = new AsciiObserver(gameService, io);
        ControllerResponse response = new PromptNextMoveResponse();
        while (response.proceed()) {
            response = response.apply(observer, gameService);
        }
    }

}
