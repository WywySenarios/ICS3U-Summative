package gameElements;

import gameOperators.Server;

/**
 * This class handles all the actions of the game, except for the main game loop. This class does NOT handle user input OR output.
 * @author pc
 */
public class Board extends Data {

    /**
     * This stores the file location
     */
    public static String fileLocation = "C:\\Java\\ICS3U Summative";

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
	private int[] evilAttacks = new int[5];
	private int[] evilSelection = { 0, 1, 2, 3, 4 };

    /**
     *
     */
    public Entity[] goodEntities = new Entity[5];
	private int[] goodAttacks = new int[5];
	private int[] goodSelection = { 0, 1, 2, 3, 4 };

    /**
     *
     */
    public Environment[] environments = new Environment[5];
	private Deck evilDeck;
	private Deck goodDeck;

    /**
     * Stores which server this Board is linked to.
     */
    public Server server;

    /**
     *
     */
    public boolean gameEnd = false;

    /**
     *
     * @param experimentalDeck denotes whether or not this Board should draw Card data from CData or experimental CData files.
     * @param evilDeckName this parameter is currently effectively useless.
     * @param goodDeckName this parameter is currently effectively useless.
     * @param evilUsername
     * @param evilName denotes the ID of the Player (e.g. pls1)
     * @param goodUsername
     * @param goodName denotes the ID of the Player (e.g. plw1)
     */
    public Board(boolean experimentalDeck, String evilDeckName, String goodDeckName, String evilUsername,
			String evilName, String goodUsername, String goodName) {
		super("Board", findFilePath(experimentalDeck));
		this.evilDeck = new Deck(this, fileLocation + "\\DeckData\\" + evilDeckName + ".JSON", true);
		this.goodDeck = new Deck(this, fileLocation + "\\DeckData\\" + goodDeckName + ".JSON", false);

		this.evilPlayer = new Player(evilUsername, evilName, this.isolateStringArray(evilName + "\\type"));
		for (int i = 0; i < 4; i++) { // fill player inventory (5-1) times to counteract the preturn Card draw
			evilPlayer.insertCard(this.evilDeck.drawCard());
		}

		this.goodPlayer = new Player(goodUsername, goodName, this.isolateStringArray(goodName + "\\type"));
		for (int i = 0; i < 4; i++) { // fill player inventory (5-1) times to counteract the preturn Card draw
			goodPlayer.insertCard(this.goodDeck.drawCard());
		}

		if (!(this.goodPlayer.validInventory() && this.goodPlayer.validInventory())) {
			throw new NullPointerException("BOARD CLASS HAS FUCKING FAILED ME");
		}
	}

	private static String findFilePath(boolean experimentalDeck) {
		// this function is a workaround to the requirement of using the "super" keyword
		// in the first line of the constructor.
		if (experimentalDeck) {
			return fileLocation + "\\ExampleCData.JSON";
		} else {
			return fileLocation + "\\CData.JSON";
		}
	}

    /**
     * This method handles pre-turn actions like drawing cards.
     */
    public void preturns() {
		/*
		 * Preturns handle: Card drawing. And literally nothing else lmao
		 */

		String[] args = { "inventory", "add", "" };

		Card temp = evilDeck.drawCard();
		Card temp2 = goodDeck.drawCard();

		int evilPlayerCardDrawStatus = evilPlayer.insertCard(temp);
		int goodPlayerCardDrawStatus = goodPlayer.insertCard(temp2);

		if (evilPlayerCardDrawStatus != -1) { // upon successful Card insertion,
			if (!this.evilPlayer.validInventory()) {
				throw new NullPointerException("DRAWING A CARD FAILED ME");
			}
			args[2] = "" + evilPlayerCardDrawStatus;

			// broadcast that a new Card has been added to the Evil Player's inventory
			server.updatePlayer(args, true);
		}

		if (goodPlayerCardDrawStatus != -1) { // upon successful Card insertion,
			if (!this.evilPlayer.validInventory()) {
				throw new NullPointerException("DRAWING A CARD FAILED ME");
			}
			args[2] = "" + goodPlayerCardDrawStatus;

			// broadcast that a new Card has been added to the Good Player's inventory
			server.updatePlayer(args, false);
		}
	}

    /**
     * This method handles cards fighting each other.
     */
    public void fight() {
		Move temp;

		// Entities fight first, then environments.
		// each lane is calculated individually.
		for (int i = 0; i < 5; i++) {
			// evil Entity does its move
			try {
				temp = evilEntities[i].moves[evilAttacks[i]];
				if (temp instanceof ChoiceMove) {
					((ChoiceMove) temp).move(evilEntities[i], this, evilSelection[i]);
				} else {
					((NoChoiceMove) temp).move(evilEntities[i], this);
				}

				if (this.gameEnd) {
					return;
				}
			} catch (NullPointerException e) {
				// this happens when there is no evil Entity in the lane
			}

			// good Entity does its move
			try {
				temp = goodEntities[i].moves[goodAttacks[i]];
				if (temp instanceof ChoiceMove) {
					((ChoiceMove) temp).move(goodEntities[i], this, goodSelection[i]);
				} else {
					((NoChoiceMove) temp).move(goodEntities[i], this);
				}

				if (this.gameEnd) {
					return;
				}
			} catch (NullPointerException e) {
				// this happens when there is no good Entity in the lane
			}

			// environments trigger

			// kill necessary entities
			String[] args = { "kill" };
			try {
				if (goodEntities[i].health <= 0) {
					goodEntities[i] = null;
					server.updateEntity(args, i, false);
				}

				if (this.gameEnd) { // this check is just for extra security, if somehow an Entity dying manages to
									// kill a Player
					return;
				}
			} catch (NullPointerException e) { // this happens when there is no good Entity in the lane
			}

			try {
				if (evilEntities[i].health <= 0) {
					evilEntities[i] = null;
					server.updateEntity(args, i, true);
				}

				if (this.gameEnd) { // this check is just for extra security, if somehow an Entity dying manages to
									// kill a Player
					return;
				}
			} catch (NullPointerException e) { // this happens when there is no evil Entity in the lane

			}
		}
	}

    /**
     * This method handles post-fighting actions, like status effects and environmental hazards.
     */
    public void endTurn() {

		/*
		 * Here's the thing with end turns: apply/compute status effects apply/compute
		 * environmental hazards attack kill necessary cards
		 * 
		 * Remember that DRAWING CARDS is reserved for the START OF A TURN Remember that
		 * one lane FINISHES ALL ITS TASKS BEFORE MOVING ON TO THE NEXT LANE
		 */

		for (int i = 0; i < 5; i++) {
			// compute status effects
			try {
				computeStatusEffects(evilEntities[i]);
			} catch (Exception e) {
				// this looks weird, but i'm just trying to handle one specific exception.
				if (!e.getLocalizedMessage()
						.equals("Cannot read field \"statusEffects\" because \"givenEntity\" is null")) {
					throw e;
				}
			}

			try {
				computeStatusEffects(goodEntities[i]);
			} catch (Exception e) {
				// this looks weird, but i'm just trying to handle one specific exception.
				if (!e.getLocalizedMessage()
						.equals("Cannot read field \"statusEffects\" because \"givenEntity\" is null")) {
					throw e;
				}
			}

			// compute environmental hazards
			// apply environment effects
			if (environments[i] != null) {
				// apply environment moves
				for (Move a : environments[i].moves) {
					if (a instanceof ChoiceMove) {
					} else {
					}
				}

				// apply environment abilities
				// for (Ability a : environments[i].abilities) {}
			}
		}

	}

	private void computeStatusEffects(Entity givenEntity) {
		String[] statusEffects = givenEntity.statusEffects;

		String effectName;
		char effectType;
		int effectPotentcy;
		int effectDuration; // in turns
		for (int i = 0; i < statusEffects.length; i++) {
			effectType = statusEffects[i].charAt(statusEffects[i].indexOf(":") + 1);
			switch (effectType) {
			case 't':
				break;
			case 'd':
				effectName = statusEffects[i].substring(0, statusEffects[i].indexOf(":"));
				effectPotentcy = Integer.parseInt(
						statusEffects[i].substring(statusEffects[i].indexOf(":") + 2, statusEffects[i].indexOf("/")));
				effectDuration = Integer.parseInt(statusEffects[i].substring(statusEffects[i].indexOf("/") + 1));

				givenEntity.health -= Integer.parseInt(
						statusEffects[i].substring(statusEffects[i].indexOf(":") + 2, statusEffects[i].indexOf("/")));
				statusEffects[i] = effectName + ':' + effectType + effectPotentcy + (effectDuration - 1);
			}
		}
	}

    /**
     * This method returns true upon succesfully registering a server. The Board may only hold one server at a time.
     * @param server_
     * @return
     */
    public boolean addServer(Server server_) { // returns true upon successful server change.
		if (this.server == null) { // if there is not a server registered on this Board yet,
			this.server = server_;
			return true;
		} else {
			return false;
		}
	}

    /**
     *
     * @param inventorySlot denotes which Card the Player is attempting to play from their inventory.
     * @param lane denotes which lane the Card is being placed in
     * @param evil denotes whether or not the Card being placed is "evil"
     * @return This method returns true upon successful Card placement
     * @throws Exception when attempting to place a Card with a value of "null"
     */
    public boolean placeCard(int inventorySlot, int lane, boolean evil) throws Exception {
		Card currentCard; // this stores the Card that will be placed
		if (evil) { // evil Player is placing a Card
			currentCard = evilPlayer.inventory[inventorySlot];
		} else { // good Player is placing a Card
			currentCard = goodPlayer.inventory[inventorySlot];
		}

		if (currentCard == null) {
			throw new Exception("Attempted to place a Card with a value of \"null\"");
		}

		// place the Card
		String[] args;
		switch (currentCard.getType()) {
		case "en": // the Card is an Entity
			// ensure there's an available slot to place the Entity
			if (evil) {
				if (evilEntities[lane] == null) { // place the Entity
					evilEntities[lane] = (Entity) currentCard.get();
				} else {
					return false;
				}
			} else {
				if (goodEntities[lane] == null) { // place the Entity
					goodEntities[lane] = (Entity) currentCard.get();
				} else {
					return false;
				}
			}

			// take away the Card from the player's inventory
			if (evil) {
				evilPlayer.removeCard(inventorySlot);
			} else {
				goodPlayer.removeCard(inventorySlot);
			}

			// broadcast changes
			args = new String[3];
			args[0] = "inventory";
			args[1] = "remove";
			args[2] = "" + inventorySlot;
			server.updatePlayer(args, evil);

			args = new String[1];
			args[0] = "place";
			server.updateEntity(args, lane, evil);
			break;
		case "sp": // the Card is a special
			Special currentSpecial = (Special) currentCard.get();

			// execute the move
			Move move = (currentSpecial).move;

			if (move == null) { // ensure validity
				throw new Exception("Invalid Card: the Special's \"move\" parameter is null.");
			} else if (move instanceof ChoiceMove) {
				((ChoiceMove) move).move(null, this, lane);
			} else if (move instanceof NoChoiceMove) {
				((NoChoiceMove) move).move(null, this);
			} else {
				throw new Exception("Invalid Card: the Special does not have a valid Move.");
			}

			// take away the Card from the player's inventory if needed
			if (currentSpecial.charges == 0 && currentSpecial.chargeRegen <= 0
					&& (currentSpecial.sacrificial == null || currentSpecial.sacrificial.equals(""))) {
				if (evil) {
					evilPlayer.inventory[inventorySlot] = null;
				} else {
					goodPlayer.inventory[inventorySlot] = null;
				}

				// broadcast changes
				args = new String[3];
				args[0] = "inventory";
				args[1] = "remove";
				args[2] = "" + inventorySlot;
				server.updatePlayer(args, evil);
			}

			// broadcast changes
			// there are no changes to broadcast (that's handled by the Special's move)
			break;
		case "ev":
			// ensure there's an available spot to place the Environment

			if (environments[lane] != null && environments[lane].PERMANENT) {
				return false;
			} else {
				environments[lane] = (Environment) currentCard.get();
			}

			// take away the Card from the player's inventory
			if (evil) {
				evilPlayer.inventory[inventorySlot] = null;
			} else {
				goodPlayer.inventory[inventorySlot] = null;
			}

			// broadcast changes
			args = new String[3];
			args[0] = "inventory";
			args[1] = "remove";
			args[2] = "" + inventorySlot;
			server.updatePlayer(args, evil);

			args = new String[1];
			args[0] = "environment";
			// server.updateEnvironment(args, lane);
			break;
		default:
			throw new Exception("Invalid Card (from Board's perspective): cannot compute Card type.");
		}

		return true;
	}
}
