package kalah.model;

public class Store implements Plantable {

    private int count;

    public Store() {
        this.count = 0;
    }

    @Override
    public void addSeed() {
        count++;
    }

    @Override
    public void removeSeeds() {
        count = 0;
    }

    @Override
    public int seedCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Store{" +
                "count=" + count +
                '}';
    }

    public void addSeeds(int amount) {
        count += amount;
    }
}
