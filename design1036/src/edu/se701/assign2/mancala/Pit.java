package edu.se701.assign2.mancala;

public class Pit {
    public final String owner;
    public final int pitNumber;
    public int stones;
    public Pit nextPit;

    public Pit(String owner, int pitNumber) {
        this(owner, pitNumber, 0, null);
    }

    public Pit(String owner, int pitNumber, int d, Pit n) {
        this.stones = d;
        this.nextPit = n;
        this.owner = owner;
        this.pitNumber = pitNumber;
    }

    @Override
    public String toString() {
        return "Pit [owner=" + owner + ", pitNumber=" + pitNumber + ", stones=" + stones + ", nextPit=[" + nextPit.pitNumber + " of " + nextPit.owner + "]]";
    }

    public String getPitDetails() {
        if (this.stones < 10) {
            return " " + this.pitNumber + "[ " + this.stones + "] ";
        }
        return " " + this.pitNumber + "[" + this.stones + "] ";
    }
}
