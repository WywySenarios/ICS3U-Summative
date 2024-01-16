package gameOperators;

import gameElements.Player;

public class LocalConsoleUser extends User {

	public LocalConsoleUser(boolean evil_) {
		super("console", evil_);
	}

	@Override
	protected void entityDamage(int lane, boolean evil, int damage) {
		if (evil) { // evil entity
			printOut("The evil entity at lane " + lane + " has changed in health by " + damage + "!",
					true);
		} else { // good entity
			printOut("The good entity at lane " + lane + " has changed in health by " + damage + "!",
					true);
		}
	}

	@Override
	protected void killEntity(int lane, boolean evil) {
		if (evil) { // evil entity
			printOut("The evil entity at lane " + lane + " has been slain!", true);
		} else { // good entity
			printOut("The good entity at lane " + lane + " has been slain!", true);
		}
	}

	@Override
	protected void summonEntity(int lane, boolean evil) {
		if (evil) { // evil entity
			printOut("The evil entity at lane " + lane + " has been placed!", true);
		} else { // good entity
			printOut("The good entity at lane " + lane + " has been placed!", true);
		}
	}

	@Override
	protected void playerDamage(boolean evil, int damage) {
		if (evil) { // evil Player
			printOut("The evil PLAYER has received " + damage + " damage!", true);
		} else { // good Player
			printOut("The good PLAYER has received " + damage + " damage!", true);
		}
	}

	@Override
	protected void summonPlayer(boolean evil) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void gameEnd() {
		printOut("The game has ended!", true);

	}

	private void clearConsole() {
		System.out.println("\n\n\n\n\n\n\n\n\n");
	}

	private void printOut(String message, boolean acknowledgement) {
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

		System.out.println("Evil Player: " + evilPlayer.toString());

		// goodPlayer
		System.out.println("Good Player: " + goodPlayer.toString());

		String laneOutput;
		for (int i = 0; i < 5; i++) {
			laneOutput = "Lane" + (i + 1) + ":\n\tEvil: ";
			
			if (evilEntities[i] == null) {
				laneOutput += "N/A";
			} else {
				laneOutput += evilEntities[i].toString();
			}

			laneOutput += "\n\tGood: ";

			if (goodEntities[i] == null) {
				laneOutput += "N/A";
			} else {
				laneOutput += goodEntities[i].toString();
			}
			
			System.out.println(laneOutput);
		}

		String inventoryOutput = "YOUR INVENTORY:\n";

		for (int i = 0; i < currentPlayer.inventory.length; i++) {
			try {
				inventoryOutput += "\t" + (i) + ") " + currentPlayer.inventory[i].toString() + "\n";
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

		System.out.println(message);

		if (acknowledgement) {
			System.out.println("\nAcknowledged...");
			console.nextLine();
		}
	}

	public String getCommand() {
		return getCommand("N/A");
	}

	public String getCommand(String message) {
		// clearConsole();
		printOut("getCommand() has been called. Message: " + message, false);
		System.out.println("Enter your input: ");
		return console.nextLine();
	}

}
