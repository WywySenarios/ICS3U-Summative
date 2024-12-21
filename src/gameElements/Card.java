package gameElements;

/**
 *
 * @author pc
 */
public abstract class Card implements Duplicable {

	/**
	 * cardType refers to what this Card may deploy. For example, a Special is "sp", an Entity is "en", and an Environment is "ev".
	 */
	
	public final String cardType;
	
	/**
	 * Stores game recognized Card ID.
	 */
	public String id;

	public String name;

	/**
	 * Stores the types this Card holds, like "wywy", "stickman", or "electric". The first type is ALWAYS a major type if this Card has a major type.
	 */
	public String[] type;

	public int cost;

	public final String RARITY;

	public boolean inInventory;

	// Entity constructor

	/**
	 * Generic Card constructor
	 * 
	 * @param id_
	 */
	public Card(String cardType_, String id_, String name_, String[] type_, int cost_, String RARITY_, boolean inInventory_) {
		this.cardType = cardType_;
		this.id = id_;
		this.name = name_;
		this.type = type_;
		this.cost = cost_;
		this.RARITY = "";
		this.inInventory = inInventory_;
	}

	public abstract String toString();
}
