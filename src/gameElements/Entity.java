package gameElements;

/**
 *
 * @author pc
 */
public class Entity extends Card implements HasAbility, Duplicable {

	/**
	 *
	 */
	public int health;

	/**
	 *
	 */
	public int hpr;

	/**
	 *
	 */
	public int shield;

	/**
	*
	*/
	public int attackModifier;

	/**
	 *
	 */
	public boolean aggressive;

	/**
	 *
	 */
	public String[] statusEffects = new String[0];

	/**
	 *
	 */
	public Move[] moves;

	/**
	 *
	 */
	public Ability[] abilities;
	
	
	/**
	 * Intializes this Entity as if it's just being drawn.
	 * @param id_
	 * @param name_
	 * @param type_
	 * @param cost_
	 * @param RARITY_
	 * @param health_
	 * @param hpr_
	 * @param shield_
	 * @param aggressive_
	 * @param moves_
	 * @param abilities_
	 */
	public Entity(String id_, String name_, String[] type_, int cost_, String RARITY_,
			int health_, int hpr_, int shield_, boolean aggressive_, Move[] moves_, Ability[] abilities_) {
		super("en", id_, name_, type_, cost_, RARITY_, true);
		this.health = health_;
		this.hpr = hpr_;
		this.shield = shield_;
		this.attackModifier = 1;
		this.aggressive = aggressive_;
		this.moves = moves_;
		this.abilities = abilities_;
	}

	/*public Entity(String id_, String name_, String[] type_, int cost_, String RARITY_, boolean inInventory_,
			int health_, int hpr_, int shield_, boolean aggressive_, Move[] moves_, Ability[] abilities_) {
		super(id_, name_, type_, cost_, RARITY_, inInventory_);
		this.health = health_;
		this.hpr = hpr_;
		this.shield = shield_;
		this.attackModifier = 1;
		this.aggressive = aggressive_;
		this.moves = moves_;
		this.abilities = abilities_;
	}*/

	private Entity(String id_, String name_, String[] type_, int cost_, String RARITY_, boolean inInventory_,
			int health_, int hpr_, int shield_, int attackModifier_, boolean aggressive_, String[] statusEffects_, Move[] moves_, Ability[] abilities_) {
		this(id_, name_, type_, cost_, RARITY_, health_, hpr_, shield_, aggressive_, moves_, abilities_);
		this.inInventory = inInventory_; // override the inInventory value
		this.attackModifier = attackModifier_;
		this.statusEffects = statusEffects_;
	}

	/**
	 *
	 * @param command
	 */
	public void triggerAbilities(String command) {
		for (Ability i : abilities) {
			/*
			 * 1) the TYPE must be equivalent 2) has to be executeable in inventory and be
			 * in the inventory OR be outside of the inventory
			 */
			if (i.TYPE.equals("AbilityPassive") || (i.TYPE.equals(command)
					&& ((this.inInventory && i.triggeredInInventory) || !this.inInventory))) {
				i.trigger(this);
			}
		}
	}

	/**
	 *
	 * @param command
	 * @param e
	 */
	@Override
	public void triggerAbilities(String command, Entity e) {
		for (Ability i : abilities) {
			/*
			 * 1) the TYPE must be equivalent 2) has to be executeable in inventory and be
			 * in the inventory OR be outside of the inventory
			 */
			if (i.TYPE.equals("AbilityPassive") || (i.TYPE.equals(command)
					&& ((this.inInventory && i.triggeredInInventory) || !this.inInventory))) {
				i.trigger(this, e);
			}
		}
	}

	/**
	 *
	 * @param command
	 * @param e
	 * @param potency
	 */
	@Override
	public void triggerAbilities(String command, Entity e, int potency) {
		for (Ability i : abilities) {
			/*
			 * 1) the TYPE must be equivalent 2) has to be executeable in inventory and be
			 * in the inventory OR be outside of the inventory
			 */
			if (i.TYPE.equals("AbilityPassive") || (i.TYPE.equals(command)
					&& ((this.inInventory && i.triggeredInInventory) || !this.inInventory))) {
				i.trigger(this, e, potency);
			}
		}
	}

	/**
	 *
	 * @return
	 */
	@Override
	public Object duplicate() {
		// duplicate type
		String[] outputType = new String[this.type.length];
		int currentIndex = 0;
		for (String i : this.type) {
			outputType[currentIndex++] = i;
		}

		// duplicate moves
		Move[] outputMoves = new Move[this.moves.length];
		currentIndex = 0;
		for (Move i : this.moves) {
			outputMoves[currentIndex++] = (Move) i.duplicate();
		}

		// duplicable abilities
		Ability[] outputAbilities = new Ability[this.abilities.length];
		currentIndex = 0;
		for (Ability i : this.abilities) {
			outputAbilities[currentIndex++] = (Ability) i.duplicate();
		}

		// duplicable
		String[] outputStatusEffects = new String[this.statusEffects.length];
		currentIndex = 0;
		for (String i : this.statusEffects) {
			outputStatusEffects[currentIndex++] = i;
		}

		return new Entity(this.id, this.name, outputType, this.cost, this.RARITY, this.inInventory,
				this.health, this.hpr, this.shield, this.attackModifier, this.aggressive, outputStatusEffects, outputMoves, outputAbilities);
	}

	/**
	 *
	 * @return
	 */
	public boolean isAlive() {
		if (health > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * @param attacker
	 * @param damage
	 * @return This method returns true if this Entity has been killed by any means.
	 */
	public boolean receiveDamage(Entity attacker, int damage) { // returns true if the Entity has 0- HP
		this.health -= damage;
		this.triggerAbilities("AbilityReceiveDamage", attacker, damage);

		if (this.health < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * @return
	 */
	public String toString() {
		String output = "[inInventory:";

		// inInventory
		output += this.inInventory + ",";

		// health
		output += "health:" + this.health + ",";

		// hpr
		output += "hpr:" + this.hpr + ",";

		// attackModifier
		output += "attackModifier:" + this.attackModifier + ",";

		// shield
		output += "shield:" + this.shield + ",";

		// aggressive
		output += "aggressive:" + this.aggressive + ",";

		// types
		output += "type:[";
		for (String i : this.type) {
			output += i + ",";
		}
		output = output.substring(0, output.length() - 1) + "],";

		// statusEffects
		output += "statusEffects:[";
		for (String i : this.statusEffects) {
			output += i + ",";
		}
		output = output.substring(0, output.length() - 1) + "],";

		// moves
		output += "moves:[";
		for (Move i : this.moves) {
			output += i.toString() + ",";
		}
		output = output.substring(0, output.length() - 1) + "],";

		// abilities
		output += "abilities:[";
		for (Ability i : this.abilities) {
			output += i.toString() + ",";
		}
		output = output.substring(0, output.length() - 1) + "]";

		return output + "]";
	}

}
