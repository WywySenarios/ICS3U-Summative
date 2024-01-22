package gameElements;

/**
 *
 * @author pc
 */
public abstract class Ability implements Duplicable {
	
    /**
     * This stores the type of ability the given Ability is (e.g. Resistance)
     */
    public final String TYPE;

    /**
     * This denotes whether this ability is capable of being triggered when inside of a Player's inventory.
     */
    public boolean triggeredInInventory;
	
    /**
     *
     * @param TYPE_
     * @param triggeredInInventory_
     */
    public Ability(String TYPE_, boolean triggeredInInventory_) {
		this.TYPE = TYPE_;
		this.triggeredInInventory = triggeredInInventory_;
	}
	
    /**
     * This method returns the type of the ability the given Ability is (e.g. Resistance)
     * @return
     */
    public String toString() {
		return TYPE;
	}
	
    /**
     * This method attempts to trigger this Ability, and only proceeds with Ability activation if the required conditions are met, including using the correct parameters and meeting trigger conditions.
     * @param abilityHolder denotes the Object (e.g. Special) that holds this Ability.
     */
    public void trigger(Object abilityHolder) {}

    /**
     * This method attempts to trigger this Ability, and only proceeds with Ability activation if the required conditions are met, including using the correct parameters and meeting trigger conditions.
     * @param abilityHolder denotes the Object (e.g. Special) that holds this Ability.
     * @param e denotes an Entity that might cause this Ability to trigger.
     */
    public void trigger(Object abilityHolder, Entity e) {}

    /**
     * This method attempts to trigger this Ability, and only proceeds with Ability activation if the required conditions are met, including using the correct parameters and meeting trigger conditions.
     * @param abilityHolder denotes the Object (e.g. Special) that holds this Ability.
     * @param e denotes an Entity that might cause this Ability to trigger.
     * @param potency
     */
    public void trigger(Object abilityHolder, Entity e, int potency) {}
}
