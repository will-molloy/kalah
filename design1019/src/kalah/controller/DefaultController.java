package kalah.controller;
import kalah.KalahConstants;
import kalah.controller.Controller;
import kalah.model.House;
import kalah.model.Model;
import kalah.model.Player;
import kalah.setting.GameSetting;
import kalah.view.View;
public class DefaultController implements Controller {
	private Model model;
	private View view;
	public DefaultController(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	@Override
	public void gameStart() {
		Player p1 = model.getPlayers().get(0);
		Player p2 = model.getPlayers().get(1);
		view.setBoard(GameSetting.houseNum);
		while (true) {
			Player currentPlayer;
			if(p1.getMoveFlag()) {
				currentPlayer = p1;
			} else {
				currentPlayer = p2;
			}
			boolean finishFlag = false;
			finishFlag = currentPlayer.isAllHouseEmpty();
			if (finishFlag) {
		        view.printGameOver();
				break;
			}
            view.printBoard();
            int userInput = view.getInput(currentPlayer);
            if (userInput == KalahConstants.INPUT_QUIT) {
                view.printQuit();
                return;
            }
            int seedsInSelectedHouse = currentPlayer.getHouses().get("" + userInput).getSeeds();
            if (seedsInSelectedHouse == 0) {
                view.printHouseIsEmpty();
                continue;
            }
            doMove(currentPlayer, userInput, -1, GameSetting.houseNum, true);
		}
	}
	private void doMove(Player p, int startPos, int seedNum, int houseNum, boolean startFlag) {
		House currentHouse;
		House oppositeHouse;
		if (startFlag) {
			currentHouse = p.getHouses().get(""+startPos);
			if (seedNum == -1) {
				seedNum = currentHouse.getSeeds();
			}
			currentHouse.clear();
		} else {
			currentHouse = p.getHouses().get("1");
			oppositeHouse = currentHouse.getOppositeHouse();
			currentHouse.add(1);
			seedNum = seedNum - 1;
			if (seedNum == 0) {
				lastSeed(p, currentHouse, oppositeHouse);
			}
		}
		for (int i = 1; i <= seedNum; i++) {
			if ((startPos + i) <= houseNum) {
				currentHouse = p.getHouses().get(""+(startPos+i));
				oppositeHouse = currentHouse.getOppositeHouse();
				currentHouse.add(1);
				if (i == seedNum) {
					lastSeed(p, currentHouse, oppositeHouse);
				}
			} else if ((startPos + i) == houseNum + 1) {
				if (i != seedNum) {
					if (p.getMoveFlag()) {
						p.getStore().add(1);
						doMove(p.getOpponent(), 1, seedNum-i, houseNum, false);
						return;
					} else {
						doMove(p.getOpponent(), 1, seedNum-i+1, houseNum, false);
						return;
					}
				} else {
					if (p.getMoveFlag()) {
						p.getStore().add(1);
						return;
					} else {
						doMove(p.getOpponent(), 1, 1, houseNum, false);
						return;
					}
				}
			} else {
				return;
			}
		}
	}
	private void lastSeed(Player p, House currentHouse, House oppositeHouse) {
		if (p.getMoveFlag() && currentHouse.getSeeds() == 1
				&& oppositeHouse.getSeeds() > 0) {
			capture(p, currentHouse, oppositeHouse);
		}
		if (p.getMoveFlag()) {
			changePlayer(p);
			return;
		} else {
			samePlayer(p);
			return;
		}
	}
	private void changePlayer(Player p) {
		p.setMoveFlag(false);
		p.getOpponent().setMoveFlag(true);
	}
	private void samePlayer(Player p) {
		p.setMoveFlag(true);
		p.getOpponent().setMoveFlag(false);
	}
	private void capture(Player p, House currentHouse, House oppositeHouse) {
		p.getStore().add(oppositeHouse.getSeeds()+1);
		currentHouse.clear();
		oppositeHouse.clear();
	}
}
