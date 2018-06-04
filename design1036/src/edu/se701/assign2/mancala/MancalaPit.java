package edu.se701.assign2.mancala;

public class MancalaPit extends Pit {
    public MancalaPit(String owner) {
        super(owner, 7);
    }

    public MancalaPit(String owner, int d, Pit n) {
        super(owner, 7, d, n);
    }

    @Override
    public String getPitDetails() {
        if (this.stones < 10) {
            return "  " + this.stones + " ";
        }
        return " " + this.stones + " ";
    }
}
