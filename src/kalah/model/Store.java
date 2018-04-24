package kalah.model;

public class Store implements Plantable {

    private int seedCount;

    public Store() {
        this.seedCount = 0;
    }

    @Override
    public void addSeed() {
        seedCount++;
    }

    @Override
    public void removeSeeds() {
        seedCount = 0;
    }

    @Override
    public int seedCount() {
        return seedCount;
    }

    public void addSeeds(int amount) {
        seedCount += amount;
    }

    @Override
    public String toString() {
        return "Store{" +
                "seedCount=" + seedCount +
                '}';
    }
}
