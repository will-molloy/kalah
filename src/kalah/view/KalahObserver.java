package kalah.view;

/**
 * Represents a Kalah observer. Implementors of the following update methods are signing a contract that they will
 * display the Kalah model to the players.
 */
public interface KalahObserver {

    /**
     * Notifies the next player to make a move and returns a string representing their move.
     */
    String nextMove();

    /**
     * Notifies the players the game was quit.
     */
    void gameQuit();

    /**
     * Notifies the players the game is over.
     */
    void gameOver();

    /**
     * Notifies the players their move was invalid (empty house).
     */
    void emptyHouse();
}
