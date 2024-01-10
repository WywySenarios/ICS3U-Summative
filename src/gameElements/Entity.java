package gameElements;

public class Entity implements HasAbility {

	public boolean inInventory;
	public int health;
	public int hpr;
	public int attackModifier;
	public int shield;
	public boolean aggressive;
	public String[] types;
	public String[] statusEffects = new String[0];
	//public int[] statusPotency = new int[0];
	public Move[] moves;
	public Ability[] abilities;
	
	public Entity(int health_, int hpr_, int shield_, boolean aggressive_, Move[] moves_, Ability[] abilities_) {
		this.health = health_;
		this.hpr = hpr_;
		this.attackModifier = 1;
		this.aggressive = aggressive_;
		this.moves = moves_;
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
	
	public void triggerAbilities(String command, Entity e, int potency) {
		for (Ability i : abilities) {
			/*
			 * 1) the TYPE must be equivalent
			 * 2) has to be executeable in inventory and be in the inventory OR be outside of the inventory
			 */
			if (i.TYPE.equals("AbilityPassive") || (i.TYPE.equals(command) && ((this.inInventory && i.triggeredInInventory) || ! this.inInventory))) {
				i.trigger(this, e, potency);
			}
		}
	}
	
	public boolean isAlive() {
		if (health > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean receiveDamage(Entity attacker, int damage) { // returns true if the Enitty has 0- HP
		this.health -= damage;
		this.triggerAbilities("AbilityReceiveDamage", attacker, damage);
		
		if (this.health < 0) {
			return true;
		} else {
			return false;
		}
	}

}
