package gameElements;

import org.json.simple.JSONArray;

import Ability.*;
import Move.*;

import java.util.Random;

public class Deck extends Data implements Duplicable {

	private String[] originalDeck = new String[40];
	private String[] currentDeck = new String[40];
	private Board thisBoard;
	private final Random RNG;
	private boolean evil;

	public Deck(Board board_, String path_, boolean evil_) throws NullPointerException {
		super("Deck", path_);

		this.evil = evil_;

		// mark which board this is on
		this.thisBoard = board_;

		// create and populate arrays storing the contents of the given file
		JSONArray JSONContents = this.isolateJSONArray("contents"); // you could say that it's "optimized" to just run
																	// .get() instead of .isolateObject(), but I don't
																	// really care at all.

		String[] potentialCards = new String[JSONContents.size()];
		long[] amountOfCards = new long[JSONContents.size()];
		for (int i = 0; i < JSONContents.size(); i++) {
			potentialCards[i] = (String) JSONContents.get(i);
			amountOfCards[i] = (long) this.isolateObject(potentialCards[i]);
		}

		// ensure validity
		int sum = 0;
		for (long i : amountOfCards) {
			sum += i;
		}

		if (sum != 40) {
			throw new NullPointerException("Invalid deck size.");
		} // END ensure validity

		// create Decks
		int currentIndex = 0;
		for (int a = 0; a < potentialCards.length; a++) {
			// loop through the necessary
			for (int b = 0; b < amountOfCards[a]; b++) {
				this.originalDeck[currentIndex++] = potentialCards[a];
			}
		}

		// copy originalDeck over to currentDeck
		for (int i = 0; i < this.currentDeck.length; i++) {
			this.currentDeck[i] = this.originalDeck[i];
		}

		// get a new RNG
		this.RNG = new Random();
	}

	private Deck(Board board_, String path_, boolean evil_, String[] originalDeck_, String[] currentDeck_,
			Random RNG_) {
		super("Deck", path_);

		this.evil = evil_;

		// mark which board this is on
		this.thisBoard = board_;

		// store cardIDs this Deck is holding

		this.originalDeck = originalDeck_;
		this.currentDeck = currentDeck_;

		// get the same RNG as before

		this.RNG = RNG_;
	}

	@Override
	public Object duplicate() {
		// duplicate decks
		String[] outputOriginalDeck = new String[this.originalDeck.length];
		int currentIndex = 0;
		for (String i : this.originalDeck) {
			outputOriginalDeck[currentIndex++] = i;
		}

		String[] outputCurrentDeck = new String[this.originalDeck.length];
		currentIndex = 0;
		for (String i : this.currentDeck) {
			outputCurrentDeck[currentIndex++] = i;
		}

		return new Deck(this.thisBoard, this.PATH, this.evil, outputOriginalDeck, outputCurrentDeck, this.RNG);
	}

	public void addCard(String cardID) {
		String[] temp = currentDeck;
		currentDeck = new String[currentDeck.length + 1];
		int currentIndex = 0;

		// re-populate array with the contents of the old array
		for (String i : temp) {
			currentDeck[currentIndex++] = i;
		}

		// add new card
		currentDeck[currentIndex] = cardID;
	}

	public void addCards(String[] cardIDs) {
		String[] temp = currentDeck;
		currentDeck = new String[currentDeck.length + cardIDs.length];
		int currentIndex = 0;
		// re-populate array with the contents of the old array
		for (String i : temp) {
			currentDeck[currentIndex++] = i;
		}

		// add new cards
		for (String i : cardIDs) {
			currentDeck[currentIndex++] = i;
		}
	}

	private Move createMove(String name, Data container) {
		switch (name) {
		case "AttackDirect":
			return new AttackDirect(container.isolateInt(name + "\\damage"),
					container.isolateStringArray(name + "\\statusEffects"), this.evil);
		case "AttackLane":
			return new AttackLane(container.isolateInt(name + "\\damage"),
					container.isolateStringArray(name + "\\statusEffects"), this.evil);
		case "AttackLeader":
			return new AttackLeader(container.isolateInt(name + "\\damage"), this.evil);
		case "AttackTarget":
			return new AttackTarget(container.isolateInt(name + "\\damage"),
					container.isolateStringArray(name + "\\statusEffects"), this.evil);
		default:
			return null;
		}
	}

	private Move isolateMove(String cardID) {
		Data d = new Data(thisBoard.isolateJSONObject(cardID + "\\move"));

		return createMove(d.getKeys()[0], d);
	}

	private Move[] isolateMoves(String cardID) {
		Data d = new Data(thisBoard.isolateJSONObject(cardID + "\\moves"));
		String[] moveNames = d.getKeys();
		Move[] output = new Move[moveNames.length];
		for (int i = 0; i < output.length; i++) {
			output[i] = createMove(moveNames[i], d);
		}

		return output;
	}

	private Ability createAbility(String name, Data container) {
		switch (name) {
		case "Resistance":
			return new Resistance(container.isolateStringArray(name + "\\resistantTypes"),
					container.isolateInt(name + "\\potency"));
		default:
			return null;
		}
	}

	// private Ability isolateAbility(String cardID) {return null;}

	private Ability[] isolateAbilities(String cardID) {
		Data d = new Data(thisBoard.isolateJSONObject(cardID + "\\abilities"));
		String[] abilityNames = d.getKeys();
		Ability[] output = new Ability[abilityNames.length];

		for (int i = 0; i < output.length; i++) {
			output[i] = createAbility(abilityNames[i], d);
		}

		return output;
	}

	// this is a DEBUG function---this function lets me know what's REALLY inside a
	// given deck.
	public String originalDeckToString() {
		String output = "[";
		for (String i : originalDeck) {
			output += i + ", ";
		}

		return output.substring(0, output.length() - 2) + "]";
	}

	// this is a DEBUG function---this function lets me know what's REALLY inside a
	// given deck.
	public String currentDeckToString() {
		String output = "[";
		for (String i : currentDeck) {
			output += i + ", ";
		}

		return output.substring(0, output.length() - 2) + "]";
	}

	public Card drawCard() {
		int randomNumber = RNG.nextInt() % 40;
		if (randomNumber > 0) {
			return createCard(currentDeck[randomNumber]);
		} else {
			return createCard(currentDeck[randomNumber * -1]);
		}
	}

	private Card createCard(String cardID) {
		switch (cardID.substring(0, 2)) {
		case "en": // entity
			return new Card(cardID, thisBoard.isolateString(cardID + "\\name"),
					thisBoard.isolateStringArray(cardID + "\\type"), thisBoard.isolateInt(cardID + "\\cost"),
					thisBoard.isolateString(cardID + "\\rarity"), thisBoard.isolateInt(cardID + "\\health"),
					thisBoard.isolateInt(cardID + "\\hpr"), thisBoard.isolateInt(cardID + "\\shield"),
					thisBoard.isolateBoolean(cardID + "\\aggressive"), this.isolateMoves(cardID),
					this.isolateAbilities(cardID));
		case "sp": // special
			return new Card(cardID, thisBoard.isolateString(cardID + "\\name"),
					thisBoard.isolateStringArray(cardID + "\\type"), thisBoard.isolateInt(cardID + "\\cost"),
					thisBoard.isolateString(cardID + "\\rarity"), thisBoard.isolateInt(cardID + "\\charges"),
					thisBoard.isolateInt(cardID + "\\chargeRegen"), thisBoard.isolateString(cardID + "\\sacrificial"),
					this.isolateMove(cardID), this.isolateAbilities(cardID));
		case "ev": // environment
			return new Card(cardID, thisBoard.isolateString(cardID + "\\name"),
					thisBoard.isolateStringArray(cardID + "\\type"), thisBoard.isolateInt(cardID + "\\cost"),
					thisBoard.isolateString(cardID + "\\rarity"), this.isolateMoves(cardID),
					this.isolateAbilities(cardID), thisBoard.isolateBoolean(cardID + "\\permanent"));
		default: // in the case that an actually invalid Card ID is given,
			throw new NullPointerException("Invalid Card ID");
		}
	}
}
