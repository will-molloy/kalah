package kalah.model;

public class Score {

    private final int playerNumber;

    private final int score;

    public Score(int playerNumber, int score) {
        this.playerNumber = playerNumber;
        this.score = score;
    }

    public int playerNumber() {
        return playerNumber;
    }

    public int score() {
        return score;
    }
}
