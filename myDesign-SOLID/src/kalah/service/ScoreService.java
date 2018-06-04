package kalah.service;

import kalah.model.Board;
import kalah.model.House;
import kalah.model.Score;

import java.util.ArrayList;
import java.util.List;

class ScoreService {

    private final Board board;

    ScoreService(Board board) {
        this.board = board;
    }

    List<Score> getScores() {
        List<Score> scores = new ArrayList<>();
        for (int playerNum = 1; playerNum <= board.getNumPlayers(); playerNum++) {
            scores.add(getScore(playerNum));
        }
        return scores;
    }

    private Score getScore(int playerNumber) {
        int total = 0;
        for (House h : board.getHouses(playerNumber)) {
            total += h.seedCount();
        }
        total += board.getStore(playerNumber).seedCount();
        return new Score(playerNumber, total);
    }

}
