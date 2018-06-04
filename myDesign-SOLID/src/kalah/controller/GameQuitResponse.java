package kalah.controller;

import kalah.view.KalahObserver;

public class GameQuitResponse extends EndingResponse {

    @Override
    public ControllerResponse apply(KalahObserver observer, GameController gameController) {
        observer.gameQuit();
        return this;
    }

}
