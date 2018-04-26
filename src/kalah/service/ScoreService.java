package kalah.service;

import kalah.model.Board;
import kalah.model.House;
import kalah.model.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * ScoreService service, scores the given Kalah board.
 */
public class ScoreService {

    private final Board board;

    private final int numPlayers;

    private final List<Score> scores;

    private final List<Score> winners;

    public ScoreService(Board board, int numPlayers) {
        this.board = board;
        this.numPlayers = numPlayers;
        this.scores = new ArrayList<>();
        this.winners = computeScores();
    }

    private List<Score> computeScores() {
        List<Score> winners = new ArrayList<>();
        winners.add(new Score(-1, -1));
        for (int playerNum = 1; playerNum <= numPlayers; playerNum++) {
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
