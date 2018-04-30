package kalah.model;

/**
 * Encapsulates the creation of a Board.
 */
public abstract class BoardFactory {

    final Board board;

    BoardFactory(int numHouses, int numInitialSeeds, int numPlayers) {
        this.board = new Board(numHouses, numInitialSeeds, numPlayers);
    }

    public final Board connectAndGetBoard() {
        connectBoardPieces();
        return board;
    }

    abstract void connectBoardPieces();

}
