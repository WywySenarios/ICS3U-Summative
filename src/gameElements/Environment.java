package gameElements;

public class Environment implements HasAbility, Duplicable {

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

	@Override
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

		return new Environment(outputType, outputMoves, outputAbilities, this.PERMANENT);
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
