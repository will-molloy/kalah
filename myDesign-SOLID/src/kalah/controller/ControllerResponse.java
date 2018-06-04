package kalah.controller;

import kalah.view.KalahObserver;

public interface ControllerResponse {

    ControllerResponse apply(KalahObserver observer, GameController gameController);

    boolean proceed();

}
