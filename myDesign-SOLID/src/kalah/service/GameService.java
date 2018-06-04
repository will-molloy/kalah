package kalah.service;

import kalah.controller.*;
import kalah.model.*;
import kalah.view.ObservableKalah;

import java.util.List;

import static kalah.util.MathUtil.incrementAndWrap;

public class GameService implements GameController, ObservableKalah {

    private final Board board;

    private final ScoreService scoreService;

    private int currentTurnsPlayer;

    public GameService(Board board, int startingPlayer) {
        this.board = board;
        this.scoreService = new ScoreService(board);
        currentTurnsPlayer = startingPlayer;
    }

    @Override
    public ControllerResponse validateAndMakeMove(int houseNumber) {
        if (board.houseIsEmpty(houseNumber, currentTurnsPlayer)) {
            return new EmptyHouseResponse();
        }
        Pit lastPitSowed = board.sow(houseNumber, currentTurnsPlayer);
        if (!lastPitSowed.getsRepeatTurn(currentTurnsPlayer)) {
            currentTurnsPlayer = incrementAndWrap(currentTurnsPlayer, numPlayers());
        }
        return validateBoard();
    }

    private ControllerResponse validateBoard() {
        return board.housesAreEmpty(currentTurnsPlayer) ? new GameOverResponse() : new PromptNextMoveResponse();
    }

    @Override
    public int currentTurnsPlayer() {
        return currentTurnsPlayer;
    }

    @Override
    public int numPlayers() {
        return board.getNumPlayers();
    }

    @Override
    public Store getStore(int playerNumber) {
        return board.getStore(playerNumber);
    }

    @Override
    public List<House> getHouses(int playerNumber) {
        return board.getHouses(playerNumber);
    }

    @Override
    public List<Score> getScores() {
        return scoreService.getScores();
    }

}
