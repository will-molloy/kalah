package myPackage;

public class Store extends Pit {
    public Store() {
        super();
    }

    public void addSeeds(int seedCount) {
        for (int i = 0; i < seedCount; i++) {
            addSeed(new Seed());
        }
    }

    @Override
    protected int getInitialNumberOfSeeds() {
        return Variables.initialSeedsInAStore;
    }
}
