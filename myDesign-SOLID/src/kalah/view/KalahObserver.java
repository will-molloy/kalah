package kalah.view;

import com.qualitascorpus.testsupport.IO;

public abstract class KalahObserver {

    protected final ObservableKalah observable;

    protected final IO io;

    public KalahObserver(ObservableKalah observable, IO io) {
        this.observable = observable;
        this.io = io;
    }

    public abstract String promptNextMove();

    public abstract void gameQuit();

    public abstract void gameOver();

    public abstract void invalidEmptyHouse();

}
