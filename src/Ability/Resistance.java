package Ability;

import gameElements.Ability;
import gameElements.Entity;

public class Resistance extends Ability {
	
	private String[] resistantTypes;
	public int potency;
	
	public Resistance(String[] resistantTypes_, int potency_) {
		super("AbilityReceiveDamage", true);
		this.resistantTypes = resistantTypes_;
		this.potency = potency_;
	}
	
	public void trigger(Object abilityHolder, Entity attacker, int damage) {
		if (attacker == null) { // this is for when a Special attacks
			return;
		}
		
		boolean triggers = false;
		for (String i : attacker.type) {
			if (searchForType(i)) {
				triggers = true;
				break;
			}
		}
		
		// idk why but I added a failsafe in case the "abilityHolder" was somehow not an Entity (no non Entity Object should have this Ability tbh)
		if (triggers && abilityHolder instanceof Entity) {
			// this ability works by healing back damage taken.
			if (this.potency > damage) { // if the damage can be fully nullified,
				((Entity) abilityHolder).health += damage;
			} else {
				((Entity) abilityHolder).health += potency;
			}
		}
	}
	
	private boolean searchForType(String input) {
		for (String i : this.resistantTypes) {
			if (input.equals(i)) {
				return true;
			}
		}
		
		return false;
	}

	public Object duplicate() {
		return new Resistance(resistantTypes, potency);
	}

}
