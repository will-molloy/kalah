package kalah.service;

import kalah.error.KalahException;
import kalah.model.*;

import java.util.List;

import static kalah.service.MoveOutcome.*;

/**
 * Intermediary service between the Kalah model and controller. Maintains state of a Kalah game. Takes player move
 * requests from the controller and sends modification requests to the Kalah model. Contains methods for reading
 * current game state.
 */
public class GameService {

    private final Board board;

    private final ScoreService scoreService;

    private final int numHouses;

    private final int numInitialSeeds;

    private final int numPlayers;

    private int currentTurnsPlayer;

    /**
     * Initialises a game of Kalah. Throws KalahException if there aren't enough houses, seeds, or players. Player 1
     * will move first.
     */
    public GameService(int numHouses, int numInitialSeeds, int numPlayers) {
        this.numHouses = numHouses;
        this.numInitialSeeds = numInitialSeeds;
        this.numPlayers = numPlayers;
        if (numHouses < 1 || numInitialSeeds < 1 || numPlayers < 2) {
            throw new KalahException(String.format(("Not enough houses (%d), seeds (%d), or players (%d)."),
                    numHouses, numInitialSeeds, numPlayers));
        }
        this.board = new Board(numHouses, numInitialSeeds, numPlayers);
        this.scoreService = new ScoreService(board, numPlayers);
        currentTurnsPlayer = 1;
    }

    /**
     * If valid, retrieves the given house for the current turns player and sows the board and then determines if the
     * next player should get a turn (they do if the last piece sowed was a house, otherwise the current player gets
     * a repeat turn). Returns the outcome of the move.
     */
    public MoveOutcome validateAndMakeMove(int houseNumber) {
        MoveOutcome outcome = validateMove(houseNumber);
        if (!outcome.equals(VALID)) {
            return outcome;
        }
        Piece endingPiece = board.sow(houseNumber, currentTurnsPlayer);
        if (endingPiece instanceof House) {
            currentTurnsPlayer = board.nextPlayer(currentTurnsPlayer);
        }
        return validateBoard();
    }

    private MoveOutcome validateMove(int houseNumber) {
        return board.houseIsEmpty(houseNumber, currentTurnsPlayer) ? INVALID_EMPTY_HOUSE : VALID;
    }

    private MoveOutcome validateBoard() {
        return board.housesAreEmpty(currentTurnsPlayer) ? GAME_OVER : VALID;
    }

    public int currentTurnsPlayer() {
        return currentTurnsPlayer;
    }

    public int totalNumSeeds() {
        return numHouses * numInitialSeeds * numPlayers;
    }

    public int maxHouseNumber() {
        return numHouses;
    }

    public int numPlayers() {
        return numPlayers;
    }

    public Store getStore(int playerNumber) {
        return board.getStore(playerNumber);
    }

    public List<House> getHouses(int playerNumber) {
        return board.getHouses(playerNumber);
    }

    public List<Score> getScores() {
        return scoreService.computeAndGetScores();
    }

    public List<Score> getWinners() {
        return scoreService.computeAndGetWinners();
    }
}
