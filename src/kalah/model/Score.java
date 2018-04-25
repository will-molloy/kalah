package kalah.model;

public class Score implements Comparable<Score> {

    private final int playerNumber;

    private final int score;

    public Score(int playerNumber, int score) {
        this.playerNumber = playerNumber;
        this.score = score;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getScore() {
        return score;
    }

    public boolean isGreaterThanOrEquals(Score that) {
        return compareTo(that) >= 0;
    }

    public boolean isGreaterThan(Score that) {
        return compareTo(that) > 0;
    }

    @Override
    public int compareTo(Score that) {
        return this.score - that.score;
    }

}
