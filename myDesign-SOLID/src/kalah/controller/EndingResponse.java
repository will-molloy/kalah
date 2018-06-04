package kalah.controller;

abstract class EndingResponse implements ControllerResponse {

    @Override
    public final boolean proceed() {
        return false;
    }

}
