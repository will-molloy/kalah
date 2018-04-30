package kalah.service;

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

    private int currentTurnsPlayer;

    public GameService(Board board) {
        this.board = board;
        this.scoreService = new ScoreService(board);
        currentTurnsPlayer = 1;
    }

    public MoveOutcome validateAndMakeMove(int houseNumber) {
        MoveOutcome outcome = validateMove(houseNumber);
        if (!outcome.equals(VALID)) {
            return outcome;
        }
        Piece lastPieceSowed = board.sow(houseNumber, currentTurnsPlayer);
        if (!lastPieceSowed.getsRepeatTurn(currentTurnsPlayer)) {
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
        return board.getNumPlayers() * board.getNumInitialSeeds() * board.getNumHouses();
    }

    public int maxHouseNumber() {
        return board.getNumHouses();
    }

    public int numPlayers() {
        return board.getNumPlayers();
    }

    public Store getStore(int playerNumber) {
        return board.getStore(playerNumber);
    }

    public List<House> getHouses(int playerNumber) {
        return board.getHouses(playerNumber);
    }

    public Score getScore(int playerNum) {
        return scoreService.getScore(playerNum);
    }

    public List<Score> getWinners() {
        return scoreService.computeAndGetWinners();
    }
}
