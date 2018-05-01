package kalah.model;

/**
 * Encapsulates the creation of a Board.
 */
public interface BoardFactory {

    Board getBoard(int numHouses, int numInitialSeeds, int numPlayers);

}
