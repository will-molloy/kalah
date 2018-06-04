package kalah.controller;

import kalah.view.KalahObserver;

public class EmptyHouseResponse extends ProceedingResponse {

    @Override
    public ControllerResponse apply(KalahObserver observer, GameController gameController) {
        observer.invalidEmptyHouse();
        return new PromptNextMoveResponse();
    }

}
