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

	public int nextAvailableInventorySlot() {
		for (int i = 0; i < 10; i++) {
			try {
				if (inventory[i] == null) {
					return i;
				}
			} catch (Exception e) {
				return -1;
			}
		}
		
		return -1;
	}
}
