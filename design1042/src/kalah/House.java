package kalah;

public class House {
    private int stones;

    House() {
        this.stones = 0;
    }

    public int getStones() {
        return stones;
    }

    public void addStones(int stones) {
        this.stones += stones;
    }

    public boolean isEmpty() {
        return stones == 0;
    }

    public int removeStones() {
        int stones = this.stones;
        this.stones = 0;
        return stones;
    }
}
