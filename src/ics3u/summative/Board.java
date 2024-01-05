package ics3u.summative;

public class Board extends Data {

	public static String fileLocation = "C:\\Java\\ICS3U Summative";
	private Player evilPlayer;
	private Player goodPlayer;
	private Entity[] evilEntities = new Entity[5];
	private Entity[] goodEntities = new Entity[5];
	private Environment[] environments = new Environment[5];
	private Deck evilDeck;
	private Deck goodDeck;

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

	public void receiveAttack(int damage, String statusEffects[], int lane, boolean evil) {

		/*
		 * Attacking an entity consist of two primary parts: 1) applying the given
		 * attack, kill the Entity if necessary, 2) applying status effects, applying
		 * environment effects, applying abilities kill the Entity if necessary.
		 * 
		 * REMEMBER THAT REGENERATION ONLY APPLIES AT THE START OF A TURN
		 */

		if (evil) {
			if (goodEntities[lane] == null) { // if the given lane is empty,

			} else { // if the given lane is occupied,
				// COMPUTE GIVEN ATTACK

				// apply given damage
				goodEntities[lane].health -= damage;

				// kill Entity if necessary

				if (goodEntities[lane].isAlive()) { // STEP 2
					// apply status effects
					for (String i : statusEffects) {
						
					}

					// apply environment effects
					if (environments[lane] != null) {
						// apply environment moves
						for (Move i : environments[lane].moves) {}
						
						// apply environment abilities
						for (Ability i : environments[lane].abilities) {}
					}

					// kill Entity if necessary
					if (!goodEntities[lane].isAlive()) {
						goodEntities[lane] = null;
					}

				} else {
					goodEntities[lane] = null;
				}
			}
		} else {
		}
	}

	public void receiveAttack(int damage, int lane, boolean evil) {
		receiveAttack(damage, new String[0], lane, evil);
	}

	public void receiveAttack(String[] statusEffects, int lane, boolean evil) {
		receiveAttack(0, statusEffects, lane, evil);
	}
}
