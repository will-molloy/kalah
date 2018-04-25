package kalah.view;

public interface KalahObserver {

    String nextMove();

    void gameQuit();

    void gameOver();

    void emptyHouse();
}
