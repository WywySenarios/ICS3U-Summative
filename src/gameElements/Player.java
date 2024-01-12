package gameElements;

public class Player {

	public String username;
	public String name;
	public Card[] inventory = new Card[10];
	public String[] type;
	public int health = 1000;

	public Player(String username_, String name_, Card[] inventory_, String[] type_) {
		this.username = username_;
		this.name = name_;
		this.inventory = inventory_;
		this.type = type_;
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
}
