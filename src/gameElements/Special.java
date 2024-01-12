package gameElements;

public class Special implements HasAbility {
	
	public boolean inInventory;
	public String[] type;
	public int charges;
	public int chargeRegen;
	public String sacrificial;
	public Move move;
	public Ability[] abilities;

	public Special(String[] type_, int charges_, int chargeRegen_, String sacrificial_, Move move_, Ability[] abilities_) {
		this.type = type_;
		this.charges = charges_;
		this.chargeRegen = chargeRegen_;
		this.sacrificial = sacrificial_;
		this.move = move_;
		this.abilities = abilities_;
	}
	
	public void triggerAbilities(String command) {
		for (Ability i : abilities) {
			/*
			 * 1) the TYPE must be equivalent
			 * 2) has to be executeable in inventory and be in the inventory OR be outside of the inventory
			 */
			if (i.TYPE.equals("AbilityPassive") || (i.TYPE.equals(command) && ((this.inInventory && i.triggeredInInventory) || ! this.inInventory))) {
				i.trigger(this);
			}
		}
	}
	
	public void triggerAbilities(String command, Entity e) {
		for (Ability i : abilities) {
			/*
			 * 1) the TYPE must be equivalent
			 * 2) has to be executeable in inventory and be in the inventory OR be outside of the inventory
			 */
			if (i.TYPE.equals("AbilityPassive") || (i.TYPE.equals(command) && ((this.inInventory && i.triggeredInInventory) || ! this.inInventory))) {
				i.trigger(this, e);
			}
		}
	}
	
	public void triggerAbilities(String command, Entity e, int potentcy) {
		for (Ability i : abilities) {
			/*
			 * 1) the TYPE must be equivalent
			 * 2) has to be executeable in inventory and be in the inventory OR be outside of the inventory
			 */
			if (i.TYPE.equals("AbilityPassive") || (i.TYPE.equals(command) && ((this.inInventory && i.triggeredInInventory) || ! this.inInventory))) {
				i.trigger(this, e, potentcy);
			}
		}
	}
	
	public String toString() {
		String output = "[inInventory:";
		
		// inInventory
		output += this.inInventory + ",";
		
		// charges
		output += "charges:" + this.charges + ",";
		
		// chargeRegen
		output += "chargeRegen:" + this.chargeRegen + ",";
		
		// sacrificial
		output += "sacrificial:" + this.sacrificial + ",";
		
		// types
		output += "type:[";
		for (String i : this.type) {
			output += i + ",";
		}
		output = output.substring(0, output.length() - 1) + "],";
		
		// moves
		output += "moves:[" + this.move.toString() + ",";
		
		// abilities
		output += "abilities:[";
		for (Ability i : this.abilities) {
			output += i.toString() + ",";
		}
		output = output.substring(0, output.length() - 1) + "]";
		
		return output + "]";
	}
}
