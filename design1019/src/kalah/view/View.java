package kalah.view;
import kalah.model.Player;
public interface View {
	void setBoard(int houseNum);
    void printBoard();
    void printGameOver();
    void printQuit();
    void printHouseIsEmpty();
    int getInput(Player player);
}
