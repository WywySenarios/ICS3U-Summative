package gameElements;

import gameOperators.Server;

public class Board extends Data {

	public static String fileLocation = "C:\\Java\\ICS3U Summative";
	public Player evilPlayer;
	public Player goodPlayer;
	public Entity[] evilEntities = new Entity[5];
	private int[] evilAttacks = new int[5];
	private int[] evilSelection = {0,1,2,3,4}; // added
	public Entity[] goodEntities = new Entity[5];
	private int[] goodAttacks = new int[5];
	private int[] goodSelection = {0,1,2,3,4}; // added
	public Environment[] environments = new Environment[5];
	private Deck evilDeck;
	private Deck goodDeck;
	public Server server;

	public Board(boolean experimentalDeck, String evilDeckName, String goodDeckName, String evilUsername,
			String evilName, String goodUsername, String goodName) {
		super("Board", findFilePath(experimentalDeck));
		this.evilDeck = new Deck(this, fileLocation + "\\DeckData\\" + evilDeckName + ".JSON");
		this.goodDeck = new Deck(this, fileLocation + "\\DeckData\\" + goodDeckName + ".JSON");

		Card[] evilPlayerInventory = new Card[5];
		for (int i = 0; i < 5; i++) {
			evilPlayerInventory[i] = this.evilDeck.drawCard();
		}
		this.evilPlayer = new Player(evilUsername, evilName, evilPlayerInventory,
				this.isolateStringArray(evilName + "\\type"));

		Card[] goodPlayerInventory = new Card[5];
		for (int i = 0; i < 5; i++) {
			goodPlayerInventory[i] = this.evilDeck.drawCard();
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

		int nextSlot = evilPlayer.nextAvailableInventorySlot();
		if (nextSlot != -1) {
			evilPlayer.inventory[nextSlot] = evilDeck.drawCard();
		}

		nextSlot = goodPlayer.nextAvailableInventorySlot();
		if (nextSlot != -1) {
			goodPlayer.inventory[nextSlot] = goodDeck.drawCard();
		}
	}

	public void fight() {
		Move temp;
		// good Cards fight first
		
		
		// Entities fight first, then environments.
		// each lane is calculated individually.
		for (int i = 0; i < 5; i++) {
			// good Entity does its move
			temp = goodEntities[i].moves[goodAttacks[i]];
			if (temp instanceof ChoiceMove) {
				((ChoiceMove) temp).move(goodEntities[i], this, goodSelection[i]);
			} else {
				((NoChoiceMove) temp).move(goodEntities[i], this);
			}
			
			// evil Entity does its move
			temp = evilEntities[i].moves[evilAttacks[i]];
			if (temp instanceof ChoiceMove) {
				((ChoiceMove) temp).move(evilEntities[i], this, evilSelection[i]);
			} else {
				((NoChoiceMove) temp).move(evilEntities[i], this);
			}
			
			// environments trigger
			
			// kill necessary entities
			if (goodEntities[i].health < 0) {
				goodEntities[i] = null;
				server.updateEntity(null, i);
			}
			
			if (evilEntities[i].health < 0) {
				evilEntities[i] = null;
				server.updateEntity(null, i + 5);
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
				if (! e.getLocalizedMessage().equals("Cannot read field \"statusEffects\" because \"givenEntity\" is null")) {
					throw e;
				}
			}
			
			try {
				computeStatusEffects(goodEntities[i]);
			} catch (Exception e) {
				// this looks weird, but i'm just trying to handle one specific exception.
				if (! e.getLocalizedMessage().equals("Cannot read field \"statusEffects\" because \"givenEntity\" is null")) {
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
				//for (Ability a : environments[i].abilities) {}
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
}
