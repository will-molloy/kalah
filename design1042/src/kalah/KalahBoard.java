package kalah;

public class KalahBoard {
    public static final int playingHouses = 6, totalHouses = 2 * (playingHouses + 1);
    House[] Houses;

    KalahBoard() {
        Houses = new House[totalHouses];
        for (int HouseNum = 0; HouseNum < totalHouses; HouseNum++)
            Houses[HouseNum] = new House();
    }

    public void setUpForPlay() {
        for (int HouseNum = 0; HouseNum < totalHouses; HouseNum++)
            if (!isAMancala(HouseNum))
                Houses[HouseNum].addStones(4);
    }

    public int stonesInMancala(int playerNum) {
        return Houses[getMancala(playerNum)].getStones();
    }

    public int stonesInHouse(int playerNum, int HouseNum) {
        return Houses[getHouseNum(playerNum, HouseNum)].getStones();
    }

    private int getHouseNum(int playerNum, int HouseNum) {
        return playerNum * (playingHouses + 1) + HouseNum;
    }

    private int getMancala(int playerNum) {
        return otherPlayerNum(playerNum) * (playingHouses + 1);
    }

    private boolean isAMancala(int HouseNum) {
        return HouseNum % (playingHouses + 1) == 0;
    }

    public KalahBoard makeACopy() {
        KalahBoard newBoard = new KalahBoard();
        for (int HouseNum = 0; HouseNum < totalHouses;
             HouseNum++)
            newBoard.Houses[HouseNum].addStones(this.Houses[HouseNum].getStones());
        return newBoard;
    }

    public NextStep doTheMove(int currentPlayerNum, int chosenHouseNum) {
        if (chosenHouseNum == -1)
            return NextStep.Quit;
        if (chosenHouseNum == -2)
            return NextStep.GameOver;
        int HouseNum = getHouseNum(currentPlayerNum, chosenHouseNum);
        int stones = Houses[HouseNum].removeStones();
        if (stones == 0)
            return NextStep.ZeroSeedInHouseGoAgain;
        while (stones != 0) {
            HouseNum++;
            if (HouseNum == totalHouses)
                HouseNum = 0;
            if (HouseNum != getMancala(otherPlayerNum(currentPlayerNum))) {
                Houses[HouseNum].addStones(1);
                stones--;
            }
        }
        if (HouseNum == getMancala(currentPlayerNum))
            return NextStep.GoAgain;
        if (ownerOf(HouseNum) == currentPlayerNum && Houses[HouseNum].getStones() == 1 && Houses[oppositeHouseNum(HouseNum)].getStones() != 0) {
            stones = Houses[oppositeHouseNum(HouseNum)].removeStones();
            Houses[getMancala(currentPlayerNum)].addStones(stones);
            stones = Houses[HouseNum].removeStones();
            Houses[getMancala(currentPlayerNum)].addStones(stones);
        }
        return NextStep.SwitchPlayer;
    }

    private int ownerOf(int HouseNum) {
        return HouseNum / (playingHouses + 1);
    }

    private int oppositeHouseNum(int HouseNum) {
        return totalHouses - HouseNum;
    }

    private int otherPlayerNum(int playerNum) {
        if (playerNum == 0)
            return 1;
        else
            return 0;
    }

    public boolean gameOver(int player) {
        int stones = 0;
        for (int HouseNum = 1; HouseNum <= playingHouses; HouseNum++)
            stones += Houses[getHouseNum(player, HouseNum)].getStones();
        if (stones == 0)
            return true;
        return false;
    }

    public void emptyStonesIntoMancalas() {
        for (int player = 0; player < 2; player++)
            for (int HouseNum = 1; HouseNum <= playingHouses; HouseNum++) {
                int stones = Houses[getHouseNum(player, HouseNum)].removeStones();
                Houses[getMancala(player)].addStones(stones);
            }
    }

    public enum NextStep {
        GoAgain, ZeroSeedInHouseGoAgain, SwitchPlayer, Quit, GameOver
    }
}

