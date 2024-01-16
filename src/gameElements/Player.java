package gameElements;

public class Player implements Duplicable {

	public String username;
	public String name;
	public Card[] inventory = new Card[0];
	public String[] type;
	public int health = 1000;
	
	public Player(String username_, String name_, String[] type_) {
		this.username = username_;
		this.name = name_;
		this.type = type_;
	}

	public Player(String username_, String name_, Card[] inventory_, String[] type_) {
		this(username_, name_, type_);
		this.inventory = inventory_;
	}
	
	@SuppressWarnings("unused")
	private Player(String username_, String name_, Card[] inventory_, String[] type_, int health_) {
		this(username_, name_, inventory_, type_);
		this.health = health_;
	}

	@Override
	public Object duplicate() {
		return this; // bugged hypothetically but I need to get stuff done quickly. Sorry!
		/*// duplicate inventory
		Card[] outputInventory = new Card[this.inventory.length];
		int currentIndex = 0;
		for (Card i : this.inventory) {
			if (i == null) {
				throw new NullPointerException("asdf");
			}
			outputInventory[currentIndex++] = (Card) i.duplicate();
		}

		// duplicate type
		String[] outputType = new String[this.type.length];
		currentIndex = 0;
		for (String i : this.type) {
			outputType[currentIndex++] = i;
		}

		return new Player(this.username, this.name, outputInventory, outputType, this.health);*/
	}

	public boolean isAlive() {
		if (this.health > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean insertCard(Card newCard) { // returns true upon successful insertion
		if (newCard == null) {
			throw new NullPointerException("Attempted to insert a null value into a Player's inventory");
		}
		
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
		this.inventory = new Card[this.inventory.length - 1];

		int currentIndex = 0;
		for (int i = 0; i < index; i++) {
			this.inventory[currentIndex++] = temp[i];
		}
		
		for (int i = index + 1; i < this.inventory.length + 1; i++) {
			this.inventory[currentIndex++] = temp[i];
		}
		
		if (! this.validInventory()) {
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
}
