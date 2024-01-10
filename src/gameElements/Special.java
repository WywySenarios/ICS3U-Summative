package gameElements;

public class Special implements HasAbility {
	
	public boolean inInventory;
	public int charges;
	public int chargeRegen;
	public String[] types;
	public String sacrificial;
	public Move move;
	public Ability[] abilities;

	public Special(int charges_, int chargeRegen_, String sacrificial_, Move move_, Ability[] abilities_) {
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

}
