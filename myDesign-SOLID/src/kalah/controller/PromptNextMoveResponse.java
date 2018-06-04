package kalah.controller;

import kalah.view.KalahObserver;

import static java.lang.Integer.parseInt;

public class PromptNextMoveResponse extends ProceedingResponse {

    @Override
    public ControllerResponse apply(KalahObserver observer, GameController gameController) {
        String input = observer.promptNextMove();
        ControllerResponse next = input.equals("q") ? new GameQuitResponse() : gameController.validateAndMakeMove(parseInt(input));
        return next.apply(observer, gameController);
    }

}
