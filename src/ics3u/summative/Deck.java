package ics3u.summative;

import org.json.simple.JSONArray;
import java.util.Random;

public class Deck extends Data {

	private Card[] originalDeck = new Card[40];
	private Card[] currentDeck;
	private Board thisBoard;
	private final Random RNG = new Random();

	public Deck(Board board_, String path_) throws java.lang.NullPointerException {
		super("Deck", path_);

		// mark which board this is on
		thisBoard = board_;

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

		/*
		 * // debug printing System.out.println(Arrays.toString(potentialCards));
		 * System.out.println(Arrays.toString(amountOfCards)); // END debug printing
		 */

		// create Decks
		int currentIndex = 0;
		Card currentCard;
		for (int a = 0; a < potentialCards.length; a++) {
			// initialize the Card
			switch (potentialCards[a].substring(0, 2)) {
			case "en":
				currentCard = new Card(potentialCards[a], thisBoard.isolateString(potentialCards[a] + "\\name"),
						thisBoard.isolateStringArray(potentialCards[a] + "\\type"),
						thisBoard.isolateInt(potentialCards[a] + "\\cost"),
						thisBoard.isolateString(potentialCards[a] + "\\rarity"),
						thisBoard.isolateInt(potentialCards[a] + "\\health"),
						thisBoard.isolateInt(potentialCards[a] + "\\hpr"),
						thisBoard.isolateInt(potentialCards[a] + "\\shield"),
						thisBoard.isolateBoolean(potentialCards[a] + "\\aggressive"),
						this.isolateMoves(potentialCards[a]), this.isolateAbilities(potentialCards[a]));
				break;
			case "sp":
				currentCard = new Card(potentialCards[a], thisBoard.isolateString(potentialCards[a] + "\\name"),
						thisBoard.isolateStringArray(potentialCards[a] + "\\type"),
						thisBoard.isolateInt(potentialCards[a] + "\\cost"),
						thisBoard.isolateString(potentialCards[a] + "\\rarity"),
						thisBoard.isolateInt(potentialCards[a] + "\\charges"),
						thisBoard.isolateInt(potentialCards[a] + "\\chargeRegen"),
						thisBoard.isolateString(potentialCards[a] + "\\sacrificial"),
						this.isolateMove(potentialCards[a]), this.isolateAbilities(potentialCards[a]));
				break;
			case "ev":
				currentCard = new Card(potentialCards[a], thisBoard.isolateString(potentialCards[a] + "\\name"),
						thisBoard.isolateStringArray(potentialCards[a] + "\\type"),
						thisBoard.isolateInt(potentialCards[a] + "\\cost"),
						thisBoard.isolateString(potentialCards[a] + "\\rarity"), this.isolateMoves(potentialCards[a]),
						this.isolateAbilities(potentialCards[a]),
						thisBoard.isolateBoolean(potentialCards[a] + "\\permanent"));
				break;
			default: // in the case that an actually invalid Card ID is given,
				throw new NullPointerException("Invalid Card ID");
			}

			// fill all the required positions of the deck
			for (int b = 0; b < amountOfCards[a]; b++) {
				originalDeck[currentIndex++] = currentCard;
			}
		}

		this.currentDeck = this.originalDeck;
	}

	public void addCard(String card_) {
	}

	public void addCards(String[] cards_) {
	}

	private Move isolateMove(String cardID) {
		return null;
	}

	// private Ability isolateAbility(String cardID) {return null;}

	private Move[] isolateMoves(String cardID) {
		return null;
	}

	private Ability[] isolateAbilities(String cardID) {
		return null;
	}
	
	// this is a DEBUG function---this function lets me know what's REALLY inside a given deck.
	public String originalDeckToString() {
		String output = "[";
		for (Card i : originalDeck) {
			output += i.id + ", ";
		}
		
		return output.substring(0, output.length() - 2) + "]";
	}
	
	// this is a DEBUG function---this function lets me know what's REALLY inside a given deck.
	public String currentDeckToString() {
		String output = "[";
		for (Card i : currentDeck) {
			output += i.id + ", ";
		}
		
		return output.substring(0, output.length() - 2) + "]";
	}

	public Card drawCard() {
		int randomNumber = RNG.nextInt() % 40;
		if (randomNumber > 0) {
			return currentDeck[randomNumber];
		} else {
			return currentDeck[randomNumber * -1];
		}
	}
}
