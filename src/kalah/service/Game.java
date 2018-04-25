package kalah.service;

import kalah.error.KalahException;
import kalah.model.Board;
import kalah.model.House;
import kalah.model.Outcome;
import kalah.model.Piece;

public class Game {

    private final Board board;

    private int currentTurnsPlayer;

    private int numPlayers;

    public Game(int numHouses, int numInitialSeeds, int numPlayers) {
        if (numHouses < 1 || numInitialSeeds < 1 || numPlayers < 2) {
            throw new KalahException("Not enough houses, seeds, or players.");
        }
        this.board = new Board(numHouses, numInitialSeeds, numPlayers);
        this.numPlayers = numPlayers;
        currentTurnsPlayer = 1;
    }

    public Outcome move(int houseNumber) {
        Outcome outcome = validateMove(houseNumber);
        if (!outcome.equals(Outcome.VALID)) {
            return outcome;
        }
        Piece piece = board.getHouse(houseNumber, currentTurnsPlayer);
        int seeds = piece.removeSeeds();

        if (seeds == 13){
            System.out.println("HELP");
        }

        while (seeds > 0) {
            piece = piece.next();
            seeds -= piece.addSeedsIfPlayerCan(1, currentTurnsPlayer);
        }

        if (piece instanceof House) { // drop it..!
            House oppositeHouse = board.getOppositeHouse((House)piece);
            if (((House)piece).canCapture(currentTurnsPlayer, oppositeHouse)) {
                int amount = oppositeHouse.capture() + piece.capture();
                board.getStoreFor(currentTurnsPlayer).addSeedsIfPlayerCan(amount, currentTurnsPlayer);
            }
            nextPlayer();
        }

        if (board.housesAreEmpty(currentTurnsPlayer)){
            return Outcome.GAME_OVER;
        }

        return Outcome.VALID;
    }

    private Outcome validateMove(int houseNumber) {
        return board.houseIsEmpty(houseNumber, currentTurnsPlayer) ? Outcome.EMPTY_HOUSE : Outcome.VALID;
    }

    private void nextPlayer() {
        currentTurnsPlayer = (currentTurnsPlayer % numPlayers) + 1;
    }

    public int getCurrentTurnsPlayer() {
        return currentTurnsPlayer;
    }

    public Board getBoard() {
        return board;
    }
}
