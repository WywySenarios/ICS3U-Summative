package gameOperators;

import gameElements.Card;
import gameElements.Player;

public class LocalConsoleUser extends User {

	private final boolean TEXTACKNOWLEDGEMENT;
	private final int DELAY;

	public LocalConsoleUser(boolean evil_, int delay) {
		super("LocalConsole", evil_);

		this.DELAY = delay;
		this.TEXTACKNOWLEDGEMENT = this.DELAY <= 0;

	}

	@Override
	public void entityDamage(int lane, boolean evil, String damage) {
		if (evil) { // evil entity
			printOut("The evil entity at lane " + (lane + 1) + " has changed in health by " + damage + "!", true);
		} else { // good entity
			printOut("The good entity at lane " + (lane + 1) + " has changed in health by " + damage + "!", true);
		}
	}

	@Override
	public void entityDeath(int lane, boolean evil) {
		if (evil) { // evil entity
			printOut("The evil entity at lane " + lane + " has been slain!", true);
		} else { // good entity
			printOut("The good entity at lane " + lane + " has been slain!", true);
		}
	}

	@Override
	public void summonEntity(int lane, boolean evil) {
		if (evil) { // evil entity
			printOut("The evil entity at lane " + lane + " has been placed!", true);
		} else { // good entity
			printOut("The good entity at lane " + lane + " has been placed!", true);
		}
	}

	@Override
	public void playerDamage(boolean evil, String damage) {
		if (evil) { // evil Player
			printOut("The evil PLAYER has received " + damage + " damage!", true);
		} else { // good Player
			printOut("The good PLAYER has received " + damage + " damage!", true);
		}
	}

	@Override
	public void summonPlayer(boolean evil) {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameEnd() {
		printOut("The game has ended!", true);

	}

	private void clearConsole() {
		System.out.println("\n\n\n\n\n\n\n\n\n");
	}

	private void printOut() {
		/*
		 * GameStatus: Evil player: Good player:
		 * 
		 * Lane 1: \tGood Card: \tEvil Card:
		 * 
		 * [etc].
		 * 
		 * YOUR INVENTORY:
		 * 
		 * YOUR MOVES:
		 */

		Player currentPlayer;
		int[] currentAttacks;
		clearConsole();
		if (this.evil) {
			currentPlayer = this.evilPlayer;
			currentAttacks = this.evilAttacks;
			System.out.print("EvilUser;");
		} else {
			currentPlayer = this.goodPlayer;
			currentAttacks = this.goodAttacks;
			System.out.print("GoodUser;");
		}
		System.out.println(" GameStatus: " + this.gameStatus + "\n");

		// print out players
		// evilPlayer

		System.out.println("Evil Player: " + evilPlayer);

		// goodPlayer
		System.out.println("Good Player: " + goodPlayer);

		String laneOutput;
		for (int i = 0; i < 5; i++) {
			laneOutput = "Lane" + (i + 1) + ":\n\tEvil: " + evilEntities[i];
			laneOutput += "\n\tGood: " + goodEntities[i];

			System.out.println(laneOutput);
		}

		String inventoryOutput = "YOUR INVENTORY:\n";

		for (int i = 0; i < currentPlayer.inventory.length; i++) {
			try {
				inventoryOutput += "\t" + (i) + ") " + currentPlayer.inventory[i] + "\n";
			} catch (NullPointerException e) {
				inventoryOutput += "\t" + (i) + ") null\n";
			}
		}

		System.out.println(inventoryOutput);

		String movesOutput = "YOUR MOVES: [";

		for (int i : currentAttacks) {
			movesOutput += i + ", ";
		}

		movesOutput = movesOutput.substring(0, movesOutput.length() - 2) + "]\n";

		System.out.println(movesOutput);
	}
	
	private void printOut(String message, boolean acknowledgement) {
		this.printOut();

		System.out.println(message);

		if (acknowledgement) {
			if (TEXTACKNOWLEDGEMENT) {
				System.out.println("\nAcknowledged...");
				console.nextLine();
			} else {
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public String getCommand() {
		return getCommand(null);
	}

	public String getCommand(String message) {
		// clearConsole();
		printOut("getCommand() has been called. Message: " + message, false);
		System.out.println("Enter your input: ");
		return console.nextLine();
	}

	@Override
	public void inventoryRemoveCard(Card givenCard) {
	}

	@Override
	public void inventoryAddCard(Card givenCard) {
	}

	@Override
	public void playerDeath(boolean evil) {
	}

	@Override
	public void pregame() { // empty; nothing is needed.
	}

}
