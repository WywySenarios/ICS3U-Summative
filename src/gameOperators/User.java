package gameOperators;

import java.util.Scanner;

import gameElements.Deck;
import gameElements.Entity;
import gameElements.Environment;
import gameElements.Player;

public abstract class User implements UI {

	private String uiType;
	protected Server server;
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

	protected void entityDamage(int lane, boolean evil, int damage) {}

	protected void killEntity(int lane, boolean evil) {}

	protected void summonEntity(int lane, boolean evil) {}

	protected void playerDamage(boolean evil, int damage) {}

	protected void summonPlayer(boolean evil) {}

	protected void gameEnd() {}

	public void updateEntity(String[] args, int entityUpdated, boolean evil) {
		Entity originalEntity = null;
		Entity finalEntity = null;

		if (evil) { // evil entity
			originalEntity = this.evilEntities[entityUpdated];
			this.evilEntities[entityUpdated] = server.b.evilEntities[entityUpdated];
			finalEntity = this.evilEntities[entityUpdated];
		} else {
			originalEntity = this.goodEntities[entityUpdated];
			this.goodEntities[entityUpdated] = server.b.goodEntities[entityUpdated];
			finalEntity = this.goodEntities[entityUpdated];
		}

		switch (args[0]) {
		case "kill":
			this.killEntity(entityUpdated, evil);
			break;
		case "damage":
			this.entityDamage(entityUpdated, evil, (finalEntity.health - originalEntity.health));
			break;
		case "place":
			this.summonEntity(entityUpdated, evil);
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
