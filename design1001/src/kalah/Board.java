package kalah;

public class Board extends AbstractBoard {
    public Board(int length) {
        this.length = length;
    }

    public void seed(int startingSeeds) {
        for (int i = 0; i < length * 2; i++) {
            pits.add(startingSeeds);
        }
        pits.set(length - 1, 0);
        pits.set((length * 2) - 1, 0);
    }

    public int getPit(int id) {
        return pits.get(id);
    }

    public void setPit(int id, int value) {
        pits.set(id, value);
    }

    public int nextPit(int id, int activePlayer) {
        int nextPit = (id + 1) % (length * 2);
        if (activePlayer == 1) {
            if (nextPit == (length * 2 - 1)) {
                nextPit = 0;
            }
        }
        if (activePlayer == 2) {
            if (nextPit == (length - 1)) {
                nextPit = length;
            }
        }
        return nextPit;
    }

    public boolean isEmptyPit(int pitNumber) {
        if (getPit(pitNumber) < 1) {
            return true;
        }
        return false;
    }

    public int playerStore(int player) {
        return player * 7 - 1;
    }

    public int playerAndHouseToPit(int player, int house) {
        return length * (player - 1) + house - 1;
    }

    public int pitToPlayer(int pitId) {
        if ((length - 1) < pitId && pitId < (length * 2 - 1)) {
            return 2;
        } else if (pitId < (length - 1)) {
            return 1;
        }
        return -1;
    }

    public int getOppositePit(int pitNumber) {
        return (length * 2 - 2) - pitNumber;
    }

    public boolean isEmptyPlayerRow(int player) {
        boolean allEmpty = true;
        for (int i = length * (player - 1); i < length * player - 1; i++) {
            if (getPit(i) > 0) {
                allEmpty = false;
                break;
            }
        }
        return allEmpty;
    }
}
