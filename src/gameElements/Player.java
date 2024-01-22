package gameElements;

import java.util.Arrays;

public class Player implements Duplicable {

	public String username;
	public String playerID;
	public Card[] inventory = new Card[0];
	public String[] type;
	public int health = 1000;
	public String status;

	public Player(String username_, String playerID_, String[] type_) {
		this.username = username_;
		this.playerID = playerID_;
		this.type = type_;
	}

	@SuppressWarnings("unused")
	private Player(String username_, String name_, Card[] inventory_, String[] type_, int health_, String status_) {
		this(username_, name_, type_);
		this.inventory = inventory_;
		this.health = health_;
		this.status = status_;
	}

	@Override
	public Object duplicate() {
		// duplicate inventory
		if (!this.validInventory()) {
			throw new NullPointerException("Found an invalid inventory when trying to duplicate a Player.");
		}

		Card[] outputInventory = new Card[this.inventory.length];
		int currentIndex = 0;
		for (Card i : this.inventory) {
			if (i == null) {
				throw new NullPointerException("Found an invalid inventory when trying to duplicate a Player.");
			}
			outputInventory[currentIndex++] = (Card) i.duplicate();
		}

		// duplicate type
		String[] outputType = new String[this.type.length];
		currentIndex = 0;
		for (String i : this.type) {
			outputType[currentIndex++] = i;
		}

		return new Player(this.username, this.playerID, outputInventory, outputType, this.health, this.status);
	}

	public boolean isAlive() {
		if (this.health > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int insertCard(Card newCard) { // returns the index that was updated
		if (newCard == null) {
			throw new NullPointerException("Attempted to insert a null value into a Player's inventory");
		}

		if (inventory.length == 10) {
			return -1;
		} else {
			// repopulate array
			Card[] temp = inventory;
			inventory = new Card[inventory.length + 1];

			int currentIndex = 0;
			for (Card i : temp) {
				inventory[currentIndex++] = i;
			}

			inventory[currentIndex] = newCard;
			return currentIndex;
		}
	}

	public void removeCard(int index) {
		Card[] temp = inventory;
		this.inventory = new Card[this.inventory.length - 1];

		int currentIndex = 0;
		for (int i = 0; i < index; i++) {
			this.inventory[currentIndex++] = temp[i];
		}

		for (int i = index + 1; i < this.inventory.length + 1; i++) {
			this.inventory[currentIndex++] = temp[i];
		}

		if (!this.validInventory()) {
			System.out.println("WE HAVE A PROBLEM");
		}
	}

	public boolean validInventory() {
		// returns false if a "null" value is found for any of the Player's Cards
		for (Card i : this.inventory) {
			if (i == null) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		String output = "Player[";

		output += "username=" + this.username + "][";
		output += "id=" + this.playerID + "][";
		output += "type=" + Arrays.toString(this.type) + "][";
		output += "health=" + this.health + "][";
		output += "status=" + status + "]";

		return output;
	}
}
