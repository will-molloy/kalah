package kalah.controller;

import kalah.view.KalahObserver;

public class GameOverResponse extends EndingResponse {

    @Override
    public ControllerResponse apply(KalahObserver observer, GameController gameController) {
        observer.gameOver();
        return this;
    }

}