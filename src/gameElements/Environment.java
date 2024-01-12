package gameElements;

public class Environment implements HasAbility {

	public boolean inInventory;
	public String[] type;
	public Move[] moves;
	public Ability[] abilities;
	public final boolean PERMANENT;

	public Environment(String[] type_, Move[] moves_, Ability[] abilities_, boolean PERMANENT_) {
		this.type = type_;
		this.moves = moves_;
		this.abilities = abilities_;
		this.PERMANENT = PERMANENT_;
	}

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

	public String toString() {
		String output = "[inInventory:";
		
		// inInventory
		output += this.inInventory + ",";
		
		// type
		output += "type:[";
		for (String i : this.type) {
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
		
		// PERMANENT
		output += "PERMANENT:" + this.PERMANENT;
		return output + "]";
	}

}
