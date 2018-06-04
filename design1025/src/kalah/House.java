package kalah;

public class House {
    private int houseSize;
    private int array[];

    House(int houseSize) {
        this.houseSize = houseSize;
        array = new int[houseSize];
    }

    House(int[] array) {
        this.array = array;
        this.houseSize = array.length;
    }

    int GetHouseLength() {
        return this.houseSize;
    }

    void setHouse(int index, int value) {
        this.array[index] = value;
    }

    void ClearOfHouse(int index) {
        array[index] = 0;
    }

    int GetSeedsOfHouse(int index) {
        return array[index];
    }

    void IncrementOfHouse(int index) {
        this.array[index]++;
    }
}
