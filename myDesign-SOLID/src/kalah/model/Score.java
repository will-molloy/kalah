package kalah.model;

public class Score implements Comparable<Score> {

    private final int playerNumber;

    private final int score;

    public Score(int playerNumber, int score) {
        this.playerNumber = playerNumber;
        this.score = score;
    }

    public int player() {
        return playerNumber;
    }

    public int value() {
        return score;
    }

    public boolean isGreaterThan(Score that) {
        return this.score > that.score;
    }

    @Override
    public int compareTo(Score that) {
        return that.score - this.score;
    }

}
