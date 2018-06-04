package kalah.service;

import kalah.model.Board;
import kalah.model.House;
import kalah.model.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Scores a Kalah board.
 */
public class ScoreService {

    private final Board board;

    ScoreService(Board board) {
        this.board = board;
    }

    public Score getScore(int playerNumber) {
        int total = 0;
        for (House h : board.getHouses(playerNumber)) {
            total += h.seedCount();
        }
        total += board.getStore(playerNumber).seedCount();
        return new Score(playerNumber, total);
    }

    List<Score> computeAndGetWinners() {
        List<Score> winners = new ArrayList<>();
        winners.add(new Score(-1, -1));
        for (Score score : getScores()) {
            if (score.isGreaterThanOrEquals(winners.get(0))) {
                if (score.isGreaterThan(winners.get(0))) {
                    winners = new ArrayList<>();
                }
                winners.add(score);
            }
        }
        return winners;
    }

    private List<Score> getScores() {
        List<Score> scores = new ArrayList<>();
        for (int playerNum = 1; playerNum <= board.getNumPlayers(); playerNum++) {
            Score score = getScore(playerNum);
            scores.add(score);
        }
        return scores;
    }

}
