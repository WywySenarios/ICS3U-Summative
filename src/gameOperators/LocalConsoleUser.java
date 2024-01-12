package gameOperators;

import gameElements.Card;
import gameElements.Entity;
import gameElements.Player;

public class LocalConsoleUser extends User {

	public LocalConsoleUser(boolean evil_) {
		super("console", evil_);
	}

	public void updateEntity(String[] args, int entityUpdated) {
		Entity originalEntity = null;
		Entity finalEntity = null;
		if (entityUpdated < 0) {
		} else if (entityUpdated < 5) { // evil entity
			originalEntity = this.evilEntities[entityUpdated];
			this.evilEntities[entityUpdated] = server.b.evilEntities[entityUpdated];
			finalEntity = this.evilEntities[entityUpdated];
		} else if (entityUpdated < 10) { // good entity
			originalEntity = this.goodEntities[entityUpdated - 5];
			this.evilEntities[entityUpdated - 5] = server.b.evilEntities[entityUpdated - 5];
			finalEntity = this.evilEntities[entityUpdated];
		}
		switch (args[0]) {
		case "kill":
			if (entityUpdated < 5) { // evil entity
				printOut("The evil entity at lane " + entityUpdated + " has been slain!", true);
			} else { // good entity
				printOut("The good entity at lane " + (entityUpdated - 5) + " has been slain!", true);
			}
			break;
		case "damage":
			if (entityUpdated < 5) { // evil entity
				printOut("The evil entity at lane " + entityUpdated + " has changed in health by "
						+ (finalEntity.health - originalEntity.health) + "!", true);
			} else { // good entity
				printOut("The good entity at lane " + (entityUpdated - 5) + " has changed in health by "
						+ (finalEntity.health - originalEntity.health) + "!", true);
			}
			break;
		}

	}

	public void updatePlayer(String[] args, boolean evil) {
		Player originalPlayer;
		Player finalPlayer;
		if (evil) {
			originalPlayer = evilPlayer;
			this.evilPlayer = server.b.evilPlayer;
			finalPlayer = this.evilPlayer;
		} else {
			originalPlayer = goodPlayer;
			this.goodPlayer = server.b.goodPlayer;
			finalPlayer = this.evilPlayer;
		}

		switch (args[0]) {
		case "damage":
			int damageTaken = finalPlayer.health - originalPlayer.health;
			
			if (evil) {
				printOut(finalPlayer.username + "(the evil player) has received " + damageTaken + " damage!", true);
			} else {
				printOut(finalPlayer.username + "(the evil player) has received " + damageTaken + " damage!", true);
			}
			break;
		}
		console.nextLine();

	}

	public void updateGameStatus(String[] args) {
		switch (args[0]) {
		case "end":
			printOut("The game has ended! " + args[1] + " has won the game!", true);
			break;
		}
	}

	private void clearConsole() {
		System.out.println("\n\n\n\n\n\n\n\n\n");
	}

	private void printOut(String message, boolean acknowledgement) {
		/*
		 * GameStatus: Good player: Evil player:
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

		String laneOutput;
		for (int i = 0; i < 5; i++) {
			laneOutput = "Lane" + (i + 1) + ":\n\tEvil: ";
			try {
				laneOutput += evilEntities[i].toString();
			} catch (NullPointerException e) {
				laneOutput += "N/A";
			}
			
			laneOutput += "\n\tGood: ";
			
			try {
				laneOutput += goodEntities[i].toString();
			} catch (NullPointerException e) {
				laneOutput += "N/A";
			}
			System.out.println(laneOutput);
		}

		String inventoryOutput = "YOUR INVENTORY: [";

		for (Card i : currentPlayer.inventory) {
			inventoryOutput += i.toString() + ", ";
		}

		inventoryOutput = inventoryOutput.substring(0, inventoryOutput.length() - 2) + "]\n";

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
		//clearConsole();
		printOut("getCommand() has been called. Message: " + message, false);
		System.out.println("Enter your input: ");
		return console.nextLine();
	}

}
