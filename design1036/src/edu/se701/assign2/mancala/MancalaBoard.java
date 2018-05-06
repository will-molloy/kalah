package edu.se701.assign2.mancala;
public class MancalaBoard {
	String player1_Name;
	String player2_Name;
	private boolean isPlayer1Turn;
	boolean isGameOver;
	public MancalaPit Player1Mancala;
	public MancalaPit Player2Mancala;
	String message;
	public Pit[] Player1Pits;
	public Pit[] Player2Pits;
	public MancalaBoard(String player1_Name, String player2_Name) {
		this.player1_Name = player1_Name;
		this.player2_Name = player2_Name;
		this.setPlayer1Turn(true);
		this.isGameOver = false;
		Player1Pits = new Pit[6];
		Player2Pits = new Pit[6];
		this.initialiseBoard();
	}
	private void initialiseBoard(){
		Player1Mancala = new MancalaPit(this.player1_Name, 0, null);
		Pit player1_Pit6 = new Pit(this.player1_Name, 6, 4, Player1Mancala);
		Pit player1_Pit5 = new Pit(this.player1_Name, 5, 4, player1_Pit6);
		Pit player1_Pit4 = new Pit(this.player1_Name, 4, 4, player1_Pit5);
		Pit player1_Pit3 = new Pit(this.player1_Name, 3, 4, player1_Pit4);
		Pit player1_Pit2 = new Pit(this.player1_Name, 2, 4, player1_Pit3);
		Pit player1_Pit1 = new Pit(this.player1_Name, 1, 4, player1_Pit2);
		Player2Mancala = new MancalaPit(this.player2_Name, 0, null);
		Pit player2_Pit6 = new Pit(this.player2_Name, 6, 4, Player2Mancala);
		Pit player2_Pit5 = new Pit(this.player2_Name, 5, 4, player2_Pit6);
		Pit player2_Pit4 = new Pit(this.player2_Name, 4, 4, player2_Pit5);
		Pit player2_Pit3 = new Pit(this.player2_Name, 3, 4, player2_Pit4);
		Pit player2_Pit2 = new Pit(this.player2_Name, 2, 4, player2_Pit3);
		Pit player2_Pit1 = new Pit(this.player2_Name, 1, 4, player2_Pit2);
		Player1Mancala.nextPit = player2_Pit1;
		Player2Mancala.nextPit =player1_Pit1;
		this.Player1Pits[0] = player1_Pit1;
		this.Player1Pits[1] = player1_Pit2;
		this.Player1Pits[2] = player1_Pit3;
		this.Player1Pits[3] = player1_Pit4;
		this.Player1Pits[4] = player1_Pit5;
		this.Player1Pits[5] = player1_Pit6;
		this.Player2Pits[0] = player2_Pit1;
		this.Player2Pits[1] = player2_Pit2;
		this.Player2Pits[2] = player2_Pit3;
		this.Player2Pits[3] = player2_Pit4;
		this.Player2Pits[4] = player2_Pit5;
		this.Player2Pits[5] = player2_Pit6;
	}
	private Pit getPlayer1Pit(int pitNumber) {
		return this.Player1Pits[pitNumber - 1];
	}
	private Pit getPlayer2Pit(int pitNumber) {
		return this.Player2Pits[pitNumber - 1];
	}
	public boolean makeMove(int pitNumber) {
		Pit aPit = null;
		String currentPlayer;
		if (this.isGameOver) {
			this.message="Game Over";
			return false;
		}
		if (pitNumber == 0 || pitNumber > 6) {
			this.message="Invalid House Number; House numbers range from 1 to 6.";
			return false;
		}
		if (this.isPlayer1Turn()) {
			aPit = this.getPlayer1Pit(pitNumber);
		} else {
			aPit = this.getPlayer2Pit(pitNumber);
		}
		currentPlayer = aPit.owner;
		if (aPit.stones == 0) {
			this.message="House is empty. Move again.";
			return false;
		}
		int stonesOnHand = aPit.stones;
		aPit.stones = 0;
		while (true) {
			aPit = aPit.nextPit;
			if (isMancalaPit(aPit) && !currentPlayer.equalsIgnoreCase(aPit.owner)) {
				continue;
			}
			aPit.stones = aPit.stones + 1;
			if (--stonesOnHand == 0) {
				if (!isMancalaPit(aPit)) {
					if (aPit.owner.equals(currentPlayer) && aPit.stones == 1) {
						Pit oppositePit = getOppositePit(aPit.owner, aPit.pitNumber);
						if (oppositePit.stones != 0) {
							Pit mancalaPit = getMancala(currentPlayer);
							mancalaPit.stones = oppositePit.stones + mancalaPit.stones + aPit.stones;
							oppositePit.stones = 0;
							aPit.stones = 0;
						}
					}
					this.setPlayer1Turn(!this.isPlayer1Turn());
				}
				if (0 == getStonesInPits(this.getNextPlayer())) {
					if (this.player1_Name.equalsIgnoreCase(this.getWinner())) {
						this.message = "Player 1 wins!";
					} else if ("Tie".equalsIgnoreCase(this.getWinner())){
						this.message = "A tie!";
					} else {
						this.message = "Player 2 wins!";
					}
					this.isGameOver = true;
				}
				break;
			}
		}
		return true;
	}
	private Pit getOppositePit(String Owner, int pitNumber) throws NullPointerException {
		try {
			if (this.player1_Name.equals(Owner)) {
				return Player2Pits[6 - pitNumber];
			}
			return Player1Pits[6 - pitNumber];
		} catch (IndexOutOfBoundsException ie) {
			return null;
		}
	}
	private boolean isMancalaPit(Pit aPit) {
		if (aPit.pitNumber == 7) {
			return true;
		}
		return false;
	}
	public Pit getMancala(String owner) {
		if (this.player1_Name.equalsIgnoreCase(owner)) {
			return this.Player1Mancala;
		}
		return this.Player2Mancala;
	}
	public String getBoardState() {
		StringBuilder boardState = new StringBuilder();
		boardState.append("+----+-------+-------+-------+-------+-------+-------+----+\n");
		boardState.append("| P2 |");
		for (int i = 5; i >= 0; --i) {
			boardState.append(this.Player2Pits[i].getPitDetails());
			boardState.append("|");
		}
		boardState.append(this.Player1Mancala.getPitDetails() + "|");
		boardState.append("\n");
		boardState.append("|    |-------+-------+-------+-------+-------+-------|    |\n");
		boardState.append("|" + this.Player2Mancala.getPitDetails() + "|");
		for (int i = 0; i <= 5; ++i) {
			boardState.append(this.Player1Pits[i].getPitDetails());
			boardState.append("|");
		}
		boardState.append(" P1 |");
		boardState.append("\n");
		boardState.append("+----+-------+-------+-------+-------+-------+-------+----+");
		return boardState.toString();
	}
	private int getStonesInPits(String playerName) {
		int pitStoneCount = 0;
		Pit[] playerpits;
		if (this.player1_Name.equalsIgnoreCase(playerName)) {
			playerpits = this.Player1Pits;
		} else {
			playerpits = this.Player2Pits;
		}
		for (Pit aPit : playerpits){
			pitStoneCount += aPit.stones;
		}
		return pitStoneCount;
	}
	public int getScore(String playerName) {
		return this.getMancala(playerName).stones + this.getStonesInPits(playerName);
	}
	public String getWinner() {
		if (this.getScore(this.player1_Name) > this.getScore(this.player2_Name)) {
			return this.player1_Name;
		} else if (this.getScore(this.player1_Name) == this.getScore(this.player2_Name)) {
			return "Tie";
		}
		return this.player2_Name;
	}
	public boolean isGameOver() {
		return isGameOver;
	}
	public String getNextPlayer() {
		if (this.isPlayer1Turn()) {
			return this.player1_Name;
		}
		return this.player2_Name;
	}
	public String getMessage() {
		return this.message;
	}
	public boolean isPlayer1Turn() {
		return isPlayer1Turn;
	}
	public void setPlayer1Turn(boolean isPlayer1Turn) {
		this.isPlayer1Turn = isPlayer1Turn;
	}
}
