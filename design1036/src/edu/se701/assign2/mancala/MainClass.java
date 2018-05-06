package edu.se701.assign2.mancala;
import java.util.Scanner;
public class MainClass {
	public static void main (String[] args) {
		MancalaBoard aMancalaBoard = new MancalaBoard("Payal", "Rahul");
		String currentMove;
		Scanner scanner = new Scanner(System.in);
		boolean isSuccessfull = false;
		int i = 0;
		while (i <= 3) {
			try {
				System.out.println(aMancalaBoard.getBoardState());
				if (aMancalaBoard.isPlayer1Turn()) {
					System.out.println("Player 1's turn - Specify house number or 'q' to quit:");
				} else {
					System.out.println("Player 2's turn - Specify house number or 'q' to quit:");
				}
				currentMove = scanner.next();
				if ("q".equalsIgnoreCase(currentMove)) {
					System.out.println("Game over");
					break;
				}
				try {
					isSuccessfull = aMancalaBoard.makeMove(Integer.parseInt(currentMove));
					if (!isSuccessfull) {
						System.out.println(aMancalaBoard.getMessage());
						continue;
					}
				} catch (Exception ae) {
					ae.printStackTrace();
					System.out.println("Invalid input");
					continue;
				}
				if (aMancalaBoard.isGameOver()) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("GAME OVER");
		System.out.println("Player 1 Score: " + aMancalaBoard.getScore("Payal"));
		System.out.println("Player 2 Score: " + aMancalaBoard.getScore("Rahul"));
	}
}
