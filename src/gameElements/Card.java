package gameElements;

public class Card {

	private Entity entity;
	private Special special;
	private Environment environment;
	public String id;
	public String name;
	public String[] type;
	public int cost;
	public final String RARITY;

	// Entity constructor
	public Card(String id_, String name_, String[] type_, int cost_, String RARITY_, int health_, int hpr_, int shield_,
			boolean aggressive_, Move[] moves_, Ability[] abilities_) {
		this.id = id_;
		this.name = name_;
		this.type = type_;
		this.cost = cost_;
		this.RARITY = "";
		this.entity = new Entity(health_, hpr_, shield_, aggressive_, moves_, abilities_);
	}

	// Special constructor
	public Card(String id_, String name_, String[] type_, int cost_, String RARITY_, int charges_, int chargeRegen_,
			String sacrificial_, Move move_, Ability[] abilities_) {
		this.id = id_;
		this.name = name_;
		this.type = type_;
		this.cost = cost_;
		this.RARITY = "";
		this.special = new Special(charges_, chargeRegen_, sacrificial_, move_, abilities_);
	}

	// Environment constructor
	public Card(String id_, String name_, String[] type_, int cost_, String RARITY_, Move[] moves_,
			Ability[] abilities_, boolean PERMANENT_) {
		this.id = id_;
		this.name = name_;
		this.type = type_;
		this.cost = cost_;
		this.RARITY = "";
		this.environment = new Environment(moves_, abilities_, PERMANENT_);
	}

	public String getType() {
		if (this.entity != null) {
			return "en";
		} else if (this.special != null) {
			return "sp";
		} else if (this.environment != null) {
			return "ev";
		} else { // invalid card,
			return null;
		}
	}
}
