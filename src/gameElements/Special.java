package gameElements;

/**
 *
 * @author pc
 */
public class Special implements HasAbility, Duplicable {

    /**
     *
     */
    public String id;

    /**
     *
     */
    public boolean inInventory;

    /**
     *
     */
    public String[] type;

    /**
     *
     */
    public int charges;

    /**
     *
     */
    public int chargeRegen;

    /**
     *
     */
    public String sacrificial;

    /**
     *
     */
    public Move move;

    /**
     *
     */
    public Ability[] abilities;

    /**
     *
     * @param id_
     * @param type_
     * @param charges_
     * @param chargeRegen_
     * @param sacrificial_
     * @param move_
     * @param abilities_
     */
    public Special(String id_, String[] type_, int charges_, int chargeRegen_, String sacrificial_, Move move_,
			Ability[] abilities_) {
		this.id = id_;
		this.type = type_;
		this.charges = charges_;
		this.chargeRegen = chargeRegen_;
		this.sacrificial = sacrificial_;
		this.move = move_;
		this.abilities = abilities_;
	}

    /**
     *
     * @param command
     */
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
     * @param potentcy
     */
    @Override
	public void triggerAbilities(String command, Entity e, int potentcy) {
		for (Ability i : abilities) {
			/*
			 * 1) the TYPE must be equivalent 2) has to be executeable in inventory and be
			 * in the inventory OR be outside of the inventory
			 */
			if (i.TYPE.equals("AbilityPassive") || (i.TYPE.equals(command)
					&& ((this.inInventory && i.triggeredInInventory) || !this.inInventory))) {
				i.trigger(this, e, potentcy);
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

		// duplicable abilities
		Ability[] outputAbilities = new Ability[this.abilities.length];
		currentIndex = 0;
		for (Ability i : this.abilities) {
			outputAbilities[currentIndex++] = (Ability) i.duplicate();
		}

		return new Special(this.id, outputType, this.charges, this.chargeRegen, this.sacrificial, (Move) this.move.duplicate(),
				outputAbilities);
	}

    /**
     *
     * @return
     */
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
