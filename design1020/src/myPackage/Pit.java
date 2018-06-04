package myPackage;

import java.util.ArrayList;

public abstract class Pit {
    protected ArrayList<Seed> seedBank;

    protected Pit() {
        seedBank = new ArrayList<Seed>();
        initializeSeeds();
    }

    public void addSeed(Seed seedArg) {
        this.seedBank.add(seedArg);
    }

    public int seedCount() {
        return seedBank.size();
    }

    protected void initializeSeeds() {
        removeAllSeeds();
        for (int i = 1; i <= getInitialNumberOfSeeds(); i++) {
            seedBank.add(new Seed());
        }
    }

    public void removeAllSeeds() {
        seedBank = new ArrayList<Seed>();
    }

    protected abstract int getInitialNumberOfSeeds();
}
