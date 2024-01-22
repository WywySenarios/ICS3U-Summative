package gameOperators;

import gameElements.Deck;
import gameElements.Entity;
import gameElements.Environment;
import gameElements.Player;

/**
 *
 * @author pc
 */
public abstract class User implements UI, UserUpdates {

	private String uiType;

    /**
     *
     */
    public boolean evil;

    /**
     *
     */
    public String gameStatus;

    /**
     *
     */
    public final int DELAY;

    /**
     *
     */
    public Player evilPlayer;

    /**
     *
     */
    public Player goodPlayer;

    /**
     *
     */
    public Entity[] evilEntities = new Entity[5];

    /**
     *
     */
    public int[] evilAttacks = new int[5];

    /**
     *
     */
    public Entity[] goodEntities = new Entity[5];

    /**
     *
     */
    public int[] goodAttacks = new int[5];

    /**
     *
     */
    public Environment[] environments = new Environment[5];

    /**
     *
     */
    public Deck evilDeck;

    /**
     *
     */
    public Deck goodDeck;

    /**
     *
     */
    public Object lastReceivedObject;

    /**
     *
     * @param uiType_
     * @param evil_
     * @param DELAY_
     */
    public User(String uiType_, boolean evil_, int DELAY_) {
		this.uiType = uiType_;
		this.evil = evil_;
		this.gameStatus = "ongoing";
		this.DELAY = DELAY_;
	}

	/*
	 * the functions from the UI interface are mainly focused on changing the
	 * current displayed information so that GUI systems can show animations for
	 * changing the stuff one at a time.
	 */

	// VARIOUS ACCESSORS:

    /**
     *
     * @return
     */

	public String getUiType() {
		return uiType;
	}

    /**
     *
     * @return
     */
    public boolean getEvil() {
		return this.evil;
	}

    /**
     *
     * @return
     */
    public String getGameStatus() {
		return this.gameStatus;
	}

    /**
     *
     * @param evil
     * @return
     */
    public Player getPlayer(boolean evil) { // returns a reference to the Player, not a duplicate
		if (evil) {
			return this.evilPlayer;
		} else {
			return this.goodPlayer;
		}
	}

    /**
     *
     * @param evil
     * @return
     */
    public Entity[] getEntities(boolean evil) { // returns a reference to the Array and Entities, not duplicates
		if (evil) {
			return this.evilEntities;
		} else {
			return this.goodEntities;
		}
	}

    /**
     *
     * @param evil
     * @return
     */
    public int[] getAttacks(boolean evil) { // returns a reference to the Array, not a duplicate
		if (evil) {
			return this.evilAttacks;
		} else {
			return this.goodAttacks;
		}
	}

    /**
     *
     * @return
     */
    public Environment[] getEnvironments() { // returns a reference to the Array and Environments, not duplicates
		return this.environments;
	}

    /**
     *
     * @param evil
     * @return
     */
    public Deck getDeck(boolean evil) {
		if (evil) {
			return this.evilDeck;
		} else {
			return this.goodDeck;
		}
	}

	// end: VARIOUS ACCESORS

    /**
     *
     * @param message
     * @return
     */

	public String getCommand(String message) {
		return null;
	}

    /**
     *
     * @param args
     * @param entityUpdated
     * @param evil
     */
    public void updateEntity(String[] args, int entityUpdated, boolean evil) {
		// push changes

		if (evil) {
			this.evilEntities[entityUpdated] = (Entity) this.lastReceivedObject;
		} else {
			this.goodEntities[entityUpdated] = (Entity) this.lastReceivedObject;
		}

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
	}

    /**
     *
     * @param args
     * @param evil
     */
    public void updatePlayer(String[] args, boolean evil) {
		// push changes
		if (evil) {
			this.evilPlayer = (Player) lastReceivedObject;
		} else {
			this.goodPlayer = (Player) lastReceivedObject;
		}

		switch (args[0]) {
		case "pregame":
			break;
		case "inventory":
			switch (args[1]) {
			case "add":
				this.inventoryAddCard(Integer.parseInt(args[2]), evil);
				break;
			case "remove":
				this.inventoryRemoveCard(Integer.parseInt(args[2]), evil);
				break;
			}
			break;
		case "damage":
			this.playerDamage(evil, args[1]);
			break;
		}
	}

    /**
     *
     * @param args
     */
    public void updateGameStatus(String[] args) {
		switch (args[0]) {
		case "end":
			this.playerDeath(args[1].equals("true"));
			
			this.gameEnd();
			break;
		}
	}

}