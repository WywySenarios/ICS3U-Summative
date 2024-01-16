package gameOperators;

import java.util.Scanner;

import gameElements.Deck;
import gameElements.Entity;
import gameElements.Environment;
import gameElements.Player;

public abstract class User implements UI {

	private String uiType;
	protected boolean evil;
	protected String gameStatus;
	protected Scanner console = new Scanner(System.in);

	protected Player evilPlayer;
	protected Player goodPlayer;
	protected Entity[] evilEntities = new Entity[5];
	protected int[] evilAttacks = new int[5];
	protected Entity[] goodEntities = new Entity[5];
	protected int[] goodAttacks = new int[5];
	protected Environment[] environments = new Environment[5];
	protected Deck evilDeck;
	protected Deck goodDeck;

	protected Object lastReceivedObject;

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

	public String getUiType() {
		return uiType;
	}

	public String getCommand() {
		return null;
	}

	public String getCommand(String message) {
		return null;
	}

	// display an Entity taking damage
	protected abstract void entityDamage(int lane, boolean evil, int damage);

	// display an Entity dying
	protected abstract void killEntity(int lane, boolean evil);

	// display an Entity being placed or summoned
	protected abstract void summonEntity(int lane, boolean evil);

	// display a Player taking damage
	protected abstract void playerDamage(boolean evil, int damage);

	// display a Player being summoned
	protected abstract void summonPlayer(boolean evil);

	// display game end messages
	protected abstract void gameEnd();

	public void updateEntity(String[] args, int entityUpdated, boolean evil) {
		switch (args[0]) {
		case "kill":
			this.killEntity(entityUpdated, evil);
			break;
		case "damage":
			int healthDifference = ((Entity) this.lastReceivedObject).health;
			if (evil) {
				healthDifference -= this.evilEntities[entityUpdated].health;
			} else {
				healthDifference -= this.goodEntities[entityUpdated].health;
			}
			this.entityDamage(entityUpdated, evil, healthDifference);
			break;
		case "place":
			this.summonEntity(entityUpdated, evil);
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
		Player originalPlayer;
		Player finalPlayer;
		if (evil) {
			originalPlayer = evilPlayer;
			this.evilPlayer = (Player) lastReceivedObject;
			finalPlayer = this.evilPlayer;
		} else {
			originalPlayer = goodPlayer;
			this.goodPlayer = (Player) lastReceivedObject;
			finalPlayer = this.evilPlayer;
		}

		switch (args[0]) {
		case "damage":
			int damageTaken = finalPlayer.health - originalPlayer.health;

			this.playerDamage(evil, damageTaken);
			break;
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