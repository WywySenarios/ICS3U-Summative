package gameOperators;

import java.util.Scanner;

import gameElements.Card;
import gameElements.Deck;
import gameElements.Entity;
import gameElements.Environment;
import gameElements.Player;

public class User implements UI {

	private String uiType;
	protected Server server;
	protected boolean evil;
	private String gameStatus;
	private Scanner console = new Scanner(System.in);

	private Player evilPlayer;
	private Player goodPlayer;
	private Entity[] evilEntities = new Entity[5];
	private int[] evilAttacks = new int[5];
	private Entity[] goodEntities = new Entity[5];
	private int[] goodAttacks = new int[5];
	private Environment[] environments = new Environment[5];
	private Deck evilDeck;
	private Deck goodDeck;

	public User(String uiType_, boolean evil_) {
		this.uiType = uiType_;
		this.evil = evil_;
		this.gameStatus = "ongoing";
	}

	/*
	 * the functions from the UI interface are mainly focused on changing the
	 * current displayed information so that GUI systems can show animations for
	 * changing the stuff one at a time.
	 */

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

		switch (uiType) {
		case "console":
			switch(args[0]) {
			case "kill":
				if (entityUpdated < 5) {
					printOut("The entity at lane " + entityUpdated + " has been slain!", true);
				} else {
					printOut("The entity at lane " + (entityUpdated - 5) + " has been slain!", true);
				}
				break;
			case "damage": 
				if (entityUpdated < 5) {
					printOut("The entity at lane " + entityUpdated + " has changed in health by " + (finalEntity.health - originalEntity.health) + "!", true);
				} else {
					printOut("The entity at lane " + (entityUpdated - 5) + " has changed in health by " + (finalEntity.health - originalEntity.health) + "!", true);
				}
				break;
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

		int damageTaken = finalPlayer.health - originalPlayer.health;

		switch (uiType) {
		case "console":
			switch (args[0]) {
			case "damage":
				if (evil) {
					printOut(finalPlayer.username + "(the evil player) has received " + damageTaken + " damage!", true);
				} else {
					printOut(finalPlayer.username + "(the evil player) has received " + damageTaken + " damage!", true);
				}
				break;
			}
			console.nextLine();
			break;
		}
	}

	public void updateGameStatus(String[] args) {
		switch (uiType) {
		case "console":
			switch (args[0]) {
			case "end":
				printOut("The game has ended! " + args[1] + " has won the game!", true);
				break;
			}
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

		for (int i = 0; i < 5; i++) {
			System.out.println("Lane " + (i + 1) + ":\n\tEvil: " + evilEntities[i].toString() + "\n\tGood: "
					+ goodEntities[i].toString());
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
	
	public String getUiType() {
		return uiType;
	}
	
	public String getCommand() {
		clearConsole();
		System.out.println("Enter your input: ");
		return console.nextLine();
	}
}
