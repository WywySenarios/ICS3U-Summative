package gameElements;

public class Player implements Duplicable {

	public String username;
	public String name;
	public Card[] inventory;
	public String[] type;
	public int health = 1000;

	public Player(String username_, String name_, Card[] inventory_, String[] type_) {
		this.username = username_;
		this.name = name_;
		this.inventory = inventory_;
		this.type = type_;
	}

	@Override
	public Object duplicate() {
		// duplicate inventory
		Card[] outputInventory = new Card[this.inventory.length];
		int currentIndex = 0;
		for (Card i : this.inventory) {
			outputInventory[currentIndex++] = (Card) i.duplicate();
		}

		// duplicate type
		String[] outputType = new String[this.type.length];
		currentIndex = 0;
		for (String i : this.type) {
			outputType[currentIndex++] = i;
		}

		return new Player(this.username, this.name, outputInventory, outputType);
	}

	public boolean isAlive() {
		if (this.health > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean insertCard(Card newCard) { // returns true upon successful insertion
		if (inventory.length == 10) {
			return false;
		} else {
			// repopulate array
			Card[] temp = inventory;
			inventory = new Card[inventory.length + 1];

			int currentIndex = 0;
			for (Card i : temp) {
				inventory[currentIndex++] = i;
			}

			inventory[currentIndex] = newCard;
			return true;
		}
	}

	public void removeCard(int index) {
		Card[] temp = inventory;
		inventory = new Card[inventory.length - 1];

		int currentIndex = 0;
		for (Card i : temp) {
			if (currentIndex != index) {
				inventory[currentIndex++] = i;
			}
		}
	}
}
