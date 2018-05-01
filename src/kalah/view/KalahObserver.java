package kalah.view;

/**
 * Represents a Kalah observer. Implementors of the following update methods are signing a contract that they will
 * display a game of Kalah to the players.
 */
public interface KalahObserver {

    /**
     * Notifies the next player to make a move and returns a string representing their move.
     */
    String promptNextMove();

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
