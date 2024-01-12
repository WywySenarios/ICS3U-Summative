package gameElements;

import gameOperators.Server;

public class Board extends Data {

	public static String fileLocation = "C:\\Java\\ICS3U Summative";
	public Player evilPlayer;
	public Player goodPlayer;
	public Entity[] evilEntities = new Entity[5];
	private int[] evilAttacks = new int[5];
	private int[] evilSelection = { 0, 1, 2, 3, 4 }; // added
	public Entity[] goodEntities = new Entity[5];
	private int[] goodAttacks = new int[5];
	private int[] goodSelection = { 0, 1, 2, 3, 4 }; // added
	public Environment[] environments = new Environment[5];
	private Deck evilDeck;
	private Deck goodDeck;
	public Server server;

	public Board(boolean experimentalDeck, String evilDeckName, String goodDeckName, String evilUsername,
			String evilName, String goodUsername, String goodName) {
		super("Board", findFilePath(experimentalDeck));
		this.evilDeck = new Deck(this, fileLocation + "\\DeckData\\" + evilDeckName + ".JSON", true);
		this.goodDeck = new Deck(this, fileLocation + "\\DeckData\\" + goodDeckName + ".JSON", false);

		Card[] evilPlayerInventory = new Card[4];
		for (int i = 0; i < 4; i++) {
			evilPlayerInventory[i] = this.evilDeck.drawCard();
		}
		this.evilPlayer = new Player(evilUsername, evilName, evilPlayerInventory,
				this.isolateStringArray(evilName + "\\type"));

		Card[] goodPlayerInventory = new Card[4];
		for (int i = 0; i < 4; i++) {
			goodPlayerInventory[i] = this.goodDeck.drawCard();
		}
		this.goodPlayer = new Player(goodUsername, goodName, goodPlayerInventory,
				this.isolateStringArray(goodName + "\\type"));
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

	public void preturns() {
		/*
		 * Preturns handle: Card drawing. And literally nothing else lmao
		 */

		String[] args = new String[1];
		args[0] = "inventory";

		if (evilPlayer.insertCard(evilDeck.drawCard())) {
			// broadcast that a new Card has been added to the Evil Player's inventory
			server.updatePlayer(args, true);
		}

		if (goodPlayer.insertCard(goodDeck.drawCard())) {
			// broadcast that a new Card has been added to the Good Player's inventory
			server.updatePlayer(args, false);
		}
	}

	public void fight() {
		Move temp;
		// good Cards fight first

		// Entities fight first, then environments.
		// each lane is calculated individually.
		for (int i = 0; i < 5; i++) {
			// good Entity does its move
			try {
				temp = goodEntities[i].moves[goodAttacks[i]];
				if (temp instanceof ChoiceMove) {
					((ChoiceMove) temp).move(goodEntities[i], this, goodSelection[i]);
				} else {
					((NoChoiceMove) temp).move(goodEntities[i], this);
				}
			} catch (NullPointerException e) {
				// this happens when there is no good Entity in the lane
			}

			// evil Entity does its move
			try {
				temp = evilEntities[i].moves[evilAttacks[i]];
				if (temp instanceof ChoiceMove) {
					((ChoiceMove) temp).move(evilEntities[i], this, evilSelection[i]);
				} else {
					((NoChoiceMove) temp).move(evilEntities[i], this);
				}
			} catch (NullPointerException e) {
				// this happens when there is no evil Entity in the lane
			}

			// environments trigger

			// kill necessary entities
			try {
				if (goodEntities[i].health < 0) {
					goodEntities[i] = null;
					server.updateEntity(null, i);
				}
			} catch (NullPointerException e) {
				// this happens when there is no good Entity in the lane
			}

			try {
				if (evilEntities[i].health < 0) {
					evilEntities[i] = null;
					server.updateEntity(null, i + 5);
				}
			} catch (NullPointerException e) {
				// this happens when there is no evil Entity in the lane
			}
		}
	}

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

			// attack (how fun!)

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

				givenEntity.health -= Integer
						.parseInt(statusEffects[i].substring(statusEffects[i].charAt(statusEffects[i].indexOf(":")),
								statusEffects[i].charAt(statusEffects[i].indexOf("/"))));
				statusEffects[i] = effectName + ':' + effectType + effectPotentcy + (effectDuration - 1);
			}
		}
	}

	public boolean addServer(Server server_) { // returns true upon successful server change.
		if (this.server == null) { // if there is not a server registered on this Board yet,
			this.server = server_;
			return true;
		} else {
			return false;
		}
	}

	public boolean placeCard(int inventorySlot, int lane, boolean evil) throws Exception, ClassCastException { // returns
																												// true
																												// upon
		// successful Card placement
		// the ClassCastException is for if the currentCard.get() returns something
		// invalid and the given exception occurs.

		Card currentCard; // this stores the Card that will be placed
		if (evil) { // evil Player is placing a Card
			currentCard = evilPlayer.inventory[inventorySlot];
		} else { // good Player is placing a Card
			currentCard = goodPlayer.inventory[inventorySlot];
		}

		// place the Card
		String[] args;
		// args = new String[1];
		// args[0] = "";
		// server.updatePlayer(args, evil);
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
				evilPlayer.inventory[inventorySlot] = null;
			} else {
				goodPlayer.inventory[inventorySlot] = null;
			}

			// broadcast changes
			args = new String[1];
			args[0] = "inventory";
			server.updatePlayer(args, evil);

			args = new String[1];
			args[0] = "place";
			if (evil) {
				server.updateEntity(args, lane);
			} else {
				server.updateEntity(args, lane + 5);
			}
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
				args = new String[1];
				args[0] = "inventory";
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
			args = new String[1];
			args[0] = "inventory";
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
