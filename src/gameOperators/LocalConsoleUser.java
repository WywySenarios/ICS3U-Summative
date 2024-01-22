package gameOperators;

import java.util.Scanner;

import gameElements.Player;

/**
 *
 * @author pc
 */
public class LocalConsoleUser extends User {

	private final boolean TEXTACKNOWLEDGEMENT;

    /**
     *
     */
    public Scanner console = new Scanner(System.in);

    /**
     *
     * @param evil_
     * @param DELAY_
     */
    public LocalConsoleUser(boolean evil_, int DELAY_) {
		super("LocalConsole", evil_, DELAY_);

		this.TEXTACKNOWLEDGEMENT = this.DELAY <= 0;

	}
	
    /**
     *
     * @return
     */
    public Scanner getConsole() { // returns a reference to the Scanner, not a duplicate
		return this.console;
	}

    /**
     *
     * @param lane
     * @param evil
     * @param damage
     */
    @Override
	public void entityDamage(int lane, boolean evil, String damage) {
		if (evil) { // evil entity
			printOut("The evil entity at lane " + (lane + 1) + " has changed in health by " + damage + "!", true);
		} else { // good entity
			printOut("The good entity at lane " + (lane + 1) + " has changed in health by " + damage + "!", true);
		}
	}

    /**
     *
     * @param lane
     * @param evil
     */
    @Override
	public void entityDeath(int lane, boolean evil) {
		if (evil) { // evil entity
			printOut("The evil entity at lane " + lane + " has been slain!", true);
		} else { // good entity
			printOut("The good entity at lane " + lane + " has been slain!", true);
		}
	}

    /**
     *
     * @param lane
     * @param evil
     */
    @Override
	public void summonEntity(int lane, boolean evil) {
		if (evil) { // evil entity
			printOut("The evil entity at lane " + lane + " has been placed!", true);
		} else { // good entity
			printOut("The good entity at lane " + lane + " has been placed!", true);
		}
	}

    /**
     *
     * @param evil
     * @param damage
     */
    @Override
	public void playerDamage(boolean evil, String damage) {
		if (evil) { // evil Player
			printOut("The evil PLAYER has received " + damage + " damage!", true);
		} else { // good Player
			printOut("The good PLAYER has received " + damage + " damage!", true);
		}
	}

    /**
     *
     * @param evil
     */
    @Override
	public void summonPlayer(boolean evil) {
		// TODO Auto-generated method stub

	}

    /**
     *
     */
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

    /**
     *
     * @return
     */
    public String getCommand() {
		return getCommand(null);
	}

    /**
     *
     * @param message
     * @return
     */
    public String getCommand(String message) {
		// clearConsole();
		printOut("getCommand() has been called. Message: " + message, false);
		System.out.println("Enter your input: ");
		return console.nextLine();
	}

    /**
     *
     * @param inventoryIndex
     * @param evil
     */
    @Override
	public void inventoryRemoveCard(int inventoryIndex, boolean evil) {
	}

    /**
     *
     * @param inventoryIndex
     * @param evil
     */
    @Override
	public void inventoryAddCard(int inventoryIndex, boolean evil) {
	}

    /**
     *
     * @param evil
     */
    @Override
	public void playerDeath(boolean evil) {
		if (evil) {
			System.out.println("The EVIL Player has died!");
		} else {
			System.out.println("The GOOD Player has died!");
		}
		
		if (this.evil == evil) {
			System.out.println("You lose! SKILL ISSUE KEK");
		} else {
			System.out.println("You won! YAYYYYYY!");
		}
	}

    /**
     *
     */
    @Override
	public void pregame() { // empty; nothing is needed.
	}

}
