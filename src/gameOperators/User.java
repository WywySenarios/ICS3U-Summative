package gameOperators;

import java.util.Scanner;

import gameElements.Deck;
import gameElements.Entity;
import gameElements.Environment;
import gameElements.Player;

public abstract class User implements UI, UserUpdates{

	private String uiType;
	public boolean evil;
	public String gameStatus;
	public Scanner console = new Scanner(System.in);

	public Player evilPlayer;
	public Player goodPlayer;
	public Entity[] evilEntities = new Entity[5];
	public int[] evilAttacks = new int[5];
	public Entity[] goodEntities = new Entity[5];
	public int[] goodAttacks = new int[5];
	public Environment[] environments = new Environment[5];
	public Deck evilDeck;
	public Deck goodDeck;

	public Object lastReceivedObject;

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
	
	// VARIOUS ACCESSORS:

	public String getUiType() {
		return uiType;
	}
	
	public boolean getEvil() {
		return this.evil;
	}
	
	public String getGameStatus() {
		return this.gameStatus;
	}
	
	public Scanner getConsole() { // returns a reference to the Scanner, not a duplicate
		return this.console;
	}
	
	public Player getPlayer(boolean evil) { // returns a reference to the Player, not a duplicate
		if (evil) {
			return this.evilPlayer;
		} else {
			return this.goodPlayer;
		}
	}
	
	public Entity[] getEntities(boolean evil) { // returns a reference to the Array and Entities, not duplicates
		if (evil) {
			return this.evilEntities;
		} else {
			return this.goodEntities;
		}
	}
	
	public int[] getAttacks(boolean evil) { // returns a reference to the Array, not a duplicate
		if (evil) {
			return this.evilAttacks;
		} else {
			return this.goodAttacks;
		}
	}
	
	public Environment[] getEnvironments() { // returns a reference to the Array and Environments, not duplicates
		return this.environments;
	}
	
	public Deck getDeck(boolean evil) {
		if (evil) {
			return this.evilDeck;
		} else {
			return this.goodDeck;
		}
	}

	// end: VARIOUS ACCESORS

	public String getCommand(String message) {
		return null;
	}

	public void updateEntity(String[] args, int entityUpdated, boolean evil) {
		
		switch (args[0]) {
		case "damage":
			this.entityDamage(entityUpdated, evil, args[1]);
			break;
		case "kill":
			this.entityDeath(entityUpdated, evil);
			break;
		case "place":
			this.summonEntity(entityUpdated, evil);
			break;
		case "pregame":
			break;
		}

		// push changes

		if (evil) {
			this.evilEntities[entityUpdated] = (Entity) this.lastReceivedObject;
		} else {
			this.goodEntities[entityUpdated] = (Entity) this.lastReceivedObject;
		}
	}

	public void updatePlayer(String[] args, boolean evil) {
		switch (args[0]) {
		case "pregame":
			break;
		case "inventory":
			break;
		case "damage":
			this.playerDamage(evil, args[1]);
			break;
		}

		// push changes
		if (evil) {
			this.evilPlayer = (Player) lastReceivedObject;
		} else {
			this.goodPlayer = (Player) lastReceivedObject;
		}
	}

	public void updateGameStatus(String[] args) {
		switch (args[0]) {
		case "end":
			this.gameEnd();
			break;
		}
	}

}