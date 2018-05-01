package kalah.model;

/**
 * A continuous board factory constructs a board such that pits are linked in numerical order.
 */
public class ContinuousBoardFactory implements BoardFactory {

    @Override
    public Board getBoard(int numHouses, int numInitialSeeds, int numPlayers) {
        return new ContinuousBoard(numHouses, numInitialSeeds, numPlayers);
    }

}
