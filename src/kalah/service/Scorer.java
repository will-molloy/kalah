package kalah.service;

import kalah.model.Board;
import kalah.model.House;
import kalah.model.Score;

import java.util.ArrayList;
import java.util.List;

public class Scorer {

    private final Board board;

    private final List<Score> scores;

    private final List<Score> winners;

    public Scorer(Board board) {
        this.board = board;
        this.scores = new ArrayList<>();
        this.winners = computeScores();
    }

    private List<Score> computeScores() {
        List<Score> winners = new ArrayList<>();
        winners.add(new Score(-1, -1));
        for (int playerNum = 1; playerNum <= board.getNumPlayers(); playerNum++) {
            Score score = score(playerNum);
            scores.add(score);
            if (score.isGreaterThanOrEquals(winners.get(0))) {
                if (score.isGreaterThan(winners.get(0))) {
                    winners = new ArrayList<>();
                }
                winners.add(score);
            }
        }
        return winners;
    }

    private Score score(int playerNumber) {
        int total = 0;
        for (House h : board.getHouses(playerNumber)) {
            total += h.seedCount();
        }
        total += board.getStore(playerNumber).seedCount();
        return new Score(playerNumber, total);
    }

    public List<Score> getScores() {
        return scores;
    }

    public List<Score> getWinners() {
        return winners;
    }
}
