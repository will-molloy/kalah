package kalah.service;

import kalah.error.KalahException;
import kalah.model.Board;
import kalah.model.House;
import kalah.model.MoveOutcome;
import kalah.model.Piece;

import static kalah.model.MoveOutcome.*;

public class Game {

    private final Board board;

    private int currentTurnsPlayer;

    public Game(int numHouses, int numInitialSeeds, int numPlayers) {
        if (numHouses < 1 || numInitialSeeds < 1 || numPlayers < 2) {
            throw new KalahException(String.format(("Not enough houses (%d), seeds (%d), or players (%d)."),
                    numHouses, numInitialSeeds, numPlayers));
        }
        this.board = new Board(numHouses, numInitialSeeds, numPlayers);
        currentTurnsPlayer = 1;
    }

    public MoveOutcome move(int houseNumber) {
        MoveOutcome outcome = validateMove(houseNumber);
        if (!outcome.equals(VALID)) {
            return outcome;
        }
        Piece endingPiece = sow(board.getHouse(houseNumber, currentTurnsPlayer));
        if (endingPiece instanceof House) {
            currentTurnsPlayer = board.nextPlayer(currentTurnsPlayer);
        }
        return validateBoard();
    }

    private MoveOutcome validateMove(int houseNumber) {
        return board.houseIsEmpty(houseNumber, currentTurnsPlayer) ? EMPTY_HOUSE : VALID;
    }

    private Piece sow(Piece piece) {
        int seeds = piece.getCountAndRemoveSeeds();
        while (seeds > 0) {
            piece = piece.next();
            seeds -= piece.addSeedIfPlayerCan(currentTurnsPlayer);
        }
        if (piece.canCapture(currentTurnsPlayer)) {
            int amount = piece.capture();
            board.getStore(currentTurnsPlayer).addSeedsIfPlayerCan(amount, currentTurnsPlayer);
        }
        return piece;
    }

    private MoveOutcome validateBoard() {
        return board.housesAreEmpty(currentTurnsPlayer) ? GAME_OVER : VALID;
    }

    public int getCurrentTurnsPlayer() {
        return currentTurnsPlayer;
    }

    public Board getBoard() {
        return board;
    }

}
