package ics3u.summative;

public class Player {

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
}
