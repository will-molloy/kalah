package kalah.model;

public class House implements Plantable {

    private final int number;

    private int seedCount;

    public House(int number, int seedCount) {
        this.number = number;
        this.seedCount = seedCount;
    }

    public int number() {
        return number;
    }

    @Override
    public int seedCount() {
        return seedCount;
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
    public String toString() {
        return "House{" +
                "number=" + number +
                ", seedCount=" + seedCount +
                '}';
    }
}
