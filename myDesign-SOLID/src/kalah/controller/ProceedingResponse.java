package kalah.controller;

abstract class ProceedingResponse implements ControllerResponse {

    @Override
    public final boolean proceed() {
        return true;
    }

}
