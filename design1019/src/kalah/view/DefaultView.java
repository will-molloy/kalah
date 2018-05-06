package kalah.view;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.KalahConstants;
import kalah.model.Model;
import kalah.model.Player;
import kalah.setting.GameSetting;
public class DefaultView implements View {
	private IO io;
	private Model model;
    private String BOARD_BOARDER = "+----+";
    private String BOARD_SEPERATOR = "|    |";
    private String BOARD_SEEDS_P1 = "| %s |";
    private String BOARD_SEEDS_P2 = "| P2 |";
	public DefaultView (Model model, IO io) {
		this.io = io;
		this.model = model;
	}
    public DefaultView(Model model, MockIO mockIO) {
        this.model = model;
        this.io = (IO) mockIO;
	}
	@Override
    public void setBoard(int houseNum) {
    	setLowerLine(houseNum);
    	setUpperLine(houseNum);
    	setBorder(houseNum);
    	setSeperator(houseNum);
    }
    private void setLowerLine(int houseNum) {
    	for (int i = 1; i <= houseNum; i++) {
			BOARD_SEEDS_P1 += " " + i + "[%s] |";
		}
		BOARD_SEEDS_P1 += " P1 |";
    }
    private void setUpperLine(int houseNum) {
    	for (int i = 0; i < houseNum; i++) {
			BOARD_SEEDS_P2 = BOARD_SEEDS_P2 + " " + (houseNum-i) + "[%s] |";
		}
		BOARD_SEEDS_P2 += " %s |";
    }
    private void setBorder(int houseNum) {
    	for (int i = 0; i<houseNum; i++) {
    		BOARD_BOARDER += "-------+";
    	}
    	BOARD_BOARDER += "----+";
    }
    private void setSeperator(int houseNum) {
    	for (int i = 0; i<houseNum-1; i++) {
    		BOARD_SEPERATOR += "-------+";
    	}
    	BOARD_SEPERATOR += "-------|    |";
    }
	@Override
	public void printBoard() {
		Player p1 = model.getPlayers().get(0);
		Player p2 = model.getPlayers().get(1);
		String[] argsP1 = pintLowerLine(p2, p1, GameSetting.houseNum);
		String[] argsP2 = pintUpperLine(p1, p2, GameSetting.houseNum);
		String lowerLine = String.format(BOARD_SEEDS_P1, (Object[]) argsP1);
		String upperLine = String.format(BOARD_SEEDS_P2, (Object[]) argsP2);
		io.println(BOARD_BOARDER);
		io.println(upperLine);
		io.println(BOARD_SEPERATOR);
		io.println(lowerLine);
		io.println(BOARD_BOARDER);
	}
	private String[] pintLowerLine(Player p2, Player p1, int houseNum) {
		String[] args = new String[houseNum + 1];
		args[0] = preProcess(p2.getStore().getSeeds());
		for (int i = 1; i <= houseNum; i++) {
			args[i] = preProcess(p1.getHouses().get("" + i).getSeeds());
		}
		return args;
	}
	private String[] pintUpperLine(Player p1, Player p2, int houseNum) {
		String[] args = new String[houseNum + 1];
		for (int i = 0; i < houseNum; i++) {
			args[i] = preProcess(p2.getHouses().get("" + (houseNum - i)).getSeeds());
		}
		args[houseNum] = preProcess(p1.getStore().getSeeds());
		return args;
	}
	private String preProcess(int seeds) {
		return (seeds < 10 ? " " + seeds : "" + seeds);
	}
	@Override
	public void printGameOver() {
		printGameEnd();
		for (Player player : model.getPlayers()) {
            io.println(String.format(KalahConstants.PLAYER_SCORE, player.getId(), player.getScore()));
        }
        int winner = model.getWinner();
        if (winner == -1) {
            io.println(KalahConstants.OUTPUT_TIE);
        } else {
            io.println(String.format(KalahConstants.WIN, winner));
        }
	}
    private void printGameEnd() {
        printBoard();
        io.println(KalahConstants.GAME_OVER);
        printBoard();
    }
	@Override
	public void printQuit() {
        io.println(KalahConstants.GAME_OVER);
        printBoard();
	}
	@Override
	public void printHouseIsEmpty() {
        io.println(KalahConstants.HOUSE_EMPTY);
	}
	@Override
	public int getInput(Player player) {
        String prompt = String.format(KalahConstants.GETINPUT_PROMPT, player.getId());
        int input;
        input = io.readInteger(prompt, 1, GameSetting.houseNum, KalahConstants.INPUT_QUIT, KalahConstants.QUIT);
        return input;
	}
}
