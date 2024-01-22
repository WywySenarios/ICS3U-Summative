package Ability;

import gameElements.Ability;
import gameElements.Entity;

/**
 * Resistance is a damage reduction Ability that triggers when the Ability holder is being attacked by a Card with a type that the Ability holder is resistant to.
 * @author pc
 */
public class Resistance extends Ability {
	
	private String[] resistantTypes;

    /**
     *
     */
    public int potency;
	
    /**
     *
     * @param resistantTypes_ the types in which this Card is resistant to. (e.g. if card A is resistant to Stickmen and is attacked by one, this ability would trigger).
     * @param potency_ the amount of damage reduction this ability offers when triggering.
     */
    public Resistance(String[] resistantTypes_, int potency_) {
		super("AbilityReceiveDamage", true);
		this.resistantTypes = resistantTypes_;
		this.potency = potency_;
	}
	
    /**
     *
     * @param abilityHolder
     * @param attacker
     * @param damage
     */
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

    /**
     *
     * @return
     */
    public Object duplicate() {
		return new Resistance(resistantTypes, potency);
	}

}
